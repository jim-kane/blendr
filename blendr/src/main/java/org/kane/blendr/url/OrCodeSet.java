package org.kane.blendr.url;

import org.jimmutable.core.fields.FieldSet;
import org.jimmutable.core.fields.FieldTreeSet;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

/**
 * An or set is a case-insensitive set of codes separated by |
 * 
 * Codes may not contain an | (obviously)
 * 
 * This class normalizes all codes to lower case and places the codes in
 * alphabetical order
 * 
 * @author jim.kane
 *
 */
public class OrCodeSet extends Stringable 
{
	transient private FieldSet<String> code_set;
	
	public OrCodeSet(String value)
	{
		super(value == null ? "" : value);
	}

	public void normalize() 
	{
		code_set = new FieldTreeSet();
		
		for ( String code : getSimpleValue().split("\\|") )
		{
			code = code.toLowerCase().trim();
			if (code.length() == 0) continue;
			
			code_set.add(code);
		}
		
		code_set.freeze();
		
		StringBuilder normalized_value = new StringBuilder(); 
		
		for(String code : code_set)
		{
			if ( normalized_value.length() != 0 )
				normalized_value.append("|");
			
			normalized_value.append(code);
		}
		
		super.setValue(normalized_value.toString());
	}

	public void validate()  
	{
		Validator.notNull(getSimpleValue());
	}
	
	/**
	 * Get an (immutable) set of the codes that make up this or set
	 * @return An immutable set of string
	 */
	public FieldSet<String> getSimpleCodeSet()
	{
		return code_set;
	}
}
