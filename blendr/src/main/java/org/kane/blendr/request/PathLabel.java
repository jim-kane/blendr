package org.kane.blendr.request;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;
import org.kane.blendr.request.Host.MyConverter;

/**
 * A blendr path (e.g. /foo/bar/baz/index.html) is made up of labels (PathLabel)
 * separated by forward slashes (in this example, the labels are foo, bar, baz
 * and index.html)
 * 
 * Labels may contain letters, numbers, -, . and _
 * 
 * Labels may *not* contain spaces or directory directives (e.g. ..)
 * 
 * Labels are normalized to lower case (and trimmed)
 * 
 * Empty labels are not allowed.  Labels may not start with or end with a .
 * 
 * @author jim.kane
 *
 */

public class PathLabel extends Stringable
{
	static public final MyConverter CONVERTER = new MyConverter();
	
	public PathLabel(String label)
	{
		super(label);
	}

	public void normalize() 
	{
		normalizeLowerCase();
		normalizeTrim();
	}

	public void validate() 
	{
		Validator.notNull(getSimpleValue());

		String value = getSimpleValue();
		
		if ( value.length() == 0 ) throw new ValidationException("Empty path labels are not allowed");
		
		for ( char ch : getSimpleValue().toCharArray() )
		{
			if ( ch >= '0' && ch <= '9' ) continue;
			if ( ch >= 'a' && ch <= 'z' ) continue;
			if ( ch == '-' ) continue;
			if ( ch == '.' ) continue;
			if ( ch == '_' ) continue;
			
			throw new ValidationException(String.format("Illegal character in path label %s, '%c'", getSimpleValue(), ch));
		}	
		
		if ( value.contains("..") ) throw new ValidationException("Path label contains a directory directive (e.g. ..), which is not allowed"); 
		
		if ( value.startsWith(".") ) throw new ValidationException("Path labels may not start with a .");
		if ( value.endsWith(".") ) throw new ValidationException("Path labels may not end with a .");
	}
	
	/**
	 * Get the extension of the path label. Extensions are the string that come
	 * *after* the last . in the path label.
	 * 
	 * For example, the extension of foo.txt is "txt" (not *not* ".txt"). The
	 * path label "foo" does not have an extension
	 * 
	 * @param default_value
	 *            The value to return if this path label does not have an
	 *            extension
	 * 
	 * @return The extension of the path label, or default_value if this path
	 *         label does not have a valid extension
	 */
	public String getOptionalExtension(String default_value)
	{
		String value = getSimpleValue();
		
		int dot_idx = value.lastIndexOf('.');
		if ( dot_idx == -1 ) return default_value;
		
		String extension = value.substring(dot_idx+1);
			
		if ( extension.length() == 0 ) return default_value;
		
		return extension;
	}
	
	/**
	 * Test to see if this path label has a valid extension
	 * 
	 * @return true if the path label has an extension, false otherwise
	 */
	public boolean hasExtension()
	{
		return getOptionalExtension(null) != null;
	}
	
	
	static public class MyConverter extends Stringable.Converter<PathLabel>
	{
		public PathLabel fromString(String str, PathLabel default_value)
		{
			try
			{
				return new PathLabel(str);
			}
			catch(Exception e)
			{
				return default_value;
			}
		}
	}
}
