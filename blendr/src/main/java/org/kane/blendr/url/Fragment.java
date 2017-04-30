package org.kane.blendr.url;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

/**
 * A wrapper around acceptable blendr fragments
 * 
 * Fragments are normalized to lower case. They may only contain letters,
 * numbers, underscores and dashes. Fragments may not be blank
 * 
 * @author jim.kane
 *
 */
public class Fragment extends Stringable
{
	public Fragment(String value)
	{		
		super(value);
	}

	
	public void normalize() 
	{
		normalizeLowerCase();
		normalizeTrim();
	}

	
	public void validate() 
	{
		String value = getSimpleValue();
		
		Validator.notNull(value);
		
		if ( value.length() == 0 ) throw new ValidationException("Fragments may not be blank");
		
		for ( char ch : value.toCharArray() )
		{
			if ( ch >= 'a' && ch <= 'z' ) continue;
			if ( ch >= '0' && ch <= '9' ) continue;
			if ( ch == '-' ) continue;
			
			throw new ValidationException(String.format("Illegal character in fragment %s, '%c'", value, ch));
		}
	}
	
	
}
