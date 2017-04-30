package org.kane.blendr.url;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.fields.FieldArrayList;
import org.jimmutable.core.fields.FieldList;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Optional;
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
	transient private FieldList<PathLabel> labels;
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
		
		labels = new FieldArrayList();
		
		String label_strs[] = value.split("/");
		
		for ( String label_str : label_strs )
		{
			if ( label_str.length() == 0 ) continue;
			
			try
			{
				PathLabel label = new PathLabel(label_str);
				labels.add(label);
			}
			catch(ValidationException e)
			{
				super.setValue(null); // Not a valid AbsolutePath because the path contains at least one invalid path label
				return;
			}
		}
		
		labels.freeze();
		
		// Build the normalized string from the labels..
		
		StringBuilder normalized_value = new StringBuilder("/");
		
		for ( PathLabel label : labels )
		{
			if ( normalized_value.charAt(normalized_value.length()-1) != '/' )
				normalized_value.append('/');
			
			normalized_value.append(label.toString());
		}
		
		super.setValue(normalized_value.toString());
		
		// Set the extension
		extension = null;
		
		if ( !labels.isEmpty() ) 
		{
			extension = labels.get(labels.size()-1).getOptionalExtension(null);
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

	/**
	 * Does this path have a valid extension? (e.g. "html", "txt", etc.)
	 * 
	 * @return true if the path has a valid extension, false otherwise
	 */
	public boolean hasExtension() 
	{ 
		return Optional.has(extension, null); 
	}
	
	
	/**
	 * Get the extension of the path. Extensions are the string that come
	 * *after* the last . in the path.
	 * 
	 * For example, the extension of /some-directory/foo.txt is "txt" (not *not*
	 * ".txt"). The path "foo" does not have an extension
	 * 
	 * @param default_value
	 *            The value to return if this path does not have an extension
	 * 
	 * @return The extension of the path, or default_value if this path does not
	 *         have a valid extension
	 */
	public String getOptionalExtension(String default_value) 
	{ 
		return Optional.getOptional(extension, null, default_value); 
	}
	
	/**
	 * Get an (frozen, therefore immutable) list of the PathLabel(s) that make up this path
	 * 
	 * May be empty
	 * 
	 * @return An immutable List of PathLabel(s) that make up this path
	 */
	public FieldList<PathLabel> getSimpleLabels() 
	{ 
		return labels; 
	}
}
