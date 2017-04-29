package org.kane.blendr.files;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Optional;
import org.jimmutable.core.utils.Validator;

public class FileKey extends Stringable
{
	transient private String extension;
	
	public FileKey(String path)
	{
		super(path);
	}

	
	public void normalize()  
	{
		normalizeTrim();
		normalizeLowerCase(); // always lower case...
		
		String value = getSimpleValue();
		
		if ( value != null )
		{
			while( value.startsWith("/") )
				value = value.substring(1);
			
			while( value.endsWith("/") )
				value = value.substring(0, value.length()-1);
			
			super.setValue(value);
			
			int dot_idx = value.lastIndexOf('.');
			if ( dot_idx != -1 )
			{
				extension = value.substring(dot_idx+1);
				
				if ( extension.contains("/") || extension.length() == 0 )
					extension = null;
			}
		}
	}

	@Override
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
			
			throw new ValidationException(String.format("Illegal character in file key %s (invalid character is '%c')", getSimpleValue(), ch));
		}
	}
	
	public boolean hasExtension() { return Optional.has(extension, null); }
	public String getOptionalExtension(String default_value) { return Optional.getOptional(extension, null, default_value); }
	
	
	static public FileKey createFileKey(String path, FileKey default_value)
	{
		try
		{
			return new FileKey(path);
		}
		catch(Exception e)
		{
			return default_value;
		}
	}
}
