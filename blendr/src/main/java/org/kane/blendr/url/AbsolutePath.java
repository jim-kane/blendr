package org.kane.blendr.url;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

/**
 * Class that wraps the "aboslute" path portion of a URL
 * 
 * Absolute paths always start with a leading /
 * 
 * They may contain letters, numbers, /, -, _ and .
 * 
 * Spaces are *not allowed*
 * 
 * All backslashes are normalized to forward slashes. Paths that end with a /
 * (other than the empty path) have the / removed.
 * 
 * Paths are normalized to lower case. The empty path is normalized to /
 * 
 * Any duplicate slashes are removed.
 * 
 * Relative directory directives (e.g. /../../foo.html) are *not* allowed)
 * 
 * @author jim.kane
 *
 */
public class AbsolutePath extends Stringable
{
	transient private String extension;
	
	public AbsolutePath(String path_str)
	{
		super(path_str);
	}

	
	public void normalize() 
	{
		normalizeTrim();
		normalizeLowerCase(); // always lower case...
		
		String value = getSimpleValue();
		if ( value == null ) return; // invalid, don't try anything further
		
		value = value.replace('\\', '/');
		
		while( value.contains("//") ) // Remove any double slashses
			value = value.replace("//", "/");
		
		while( value.endsWith("/") )
			value = value.substring(0, value.length()-1);
		
		if ( value.length() == 0 ) value = "/";
		super.setValue(value);
		
		// Set the extension...
		int dot_idx = value.lastIndexOf('.');
		if ( dot_idx != -1 )
		{
			extension = value.substring(dot_idx+1);
			
			if ( extension.contains("/") || extension.length() == 0 )
				extension = null;
		}
		
		
	}

	
	public void validate() 
	{
		Validator.notNull(getSimpleValue());
		Validator.min(getSimpleValue().length(), 1);
		
		for ( char ch : getSimpleValue().toCharArray() )
		{
			if ( ch == '/' ) continue;
			if ( ch >= '0' && ch <= '9' ) continue;
			if ( ch >= 'a' && ch <= 'z' ) continue;
			if ( ch == '-' ) continue;
			if ( ch == '.' ) continue;
			if ( ch == '_' ) continue;
			
			throw new ValidationException(String.format("Illegal character in path %s, '%c'", getSimpleValue(), ch));
		}	
		
	}
}
