package org.kane.blendr.url;

import org.jimmutable.core.fields.FieldHashMap;
import org.jimmutable.core.fields.FieldMap;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

/**
 * This class is used to work with application/x-www-form-urlencoded data
 * (commonly referred to as query strings)
 * 
 * @author jim.kane
 *
 */
public class QueryString extends Stringable
{
	// Store the *decoded*, normalized query string data
	transient private FieldMap<String,String> data;
	
	public QueryString(String query_string)
	{
		super(query_string == null ? "" : query_string);
	}
	
	public void normalize() 
	{	
		// Populated the decoded data structure (effectively, parse the query string)
		String query_string = getSimpleValue();
		
		data = new FieldHashMap();

		if ( query_string == null ) query_string = "";

		for ( String term : query_string.split("&") )
		{
			int equals_idx = term.indexOf('=');

			if ( equals_idx == -1 )
			{
				// not a pair
				addDecodedKeyValuePair(URLCharacterEncoding.decode(term, null),"");
			}
			else
			{
				String key = term.substring(0, equals_idx);
				String value = term.substring(equals_idx+1);

				addDecodedKeyValuePair(URLCharacterEncoding.decode(key, null), URLCharacterEncoding.decode(value, null));
			}
		}

		data.freeze();
	}

	public void validate() 
	{	
		Validator.notNull(getSimpleValue());
	}

	private void addDecodedKeyValuePair(String decoded_key, String decoded_value)
	{
		if ( decoded_key == null ) return;
		decoded_key = decoded_key.trim().toLowerCase();
		if ( decoded_key.length() == 0 ) return;
		
		if ( decoded_value == null ) decoded_value = "";
		
		data.put(decoded_key, decoded_value);
	}
	
	/**
	 * Get a map that contains the *decoded* data in this query string. For
	 * example, the query string foo=bar&baz=quz has the data {foo=bar, baz=quz}
	 * 
	 * keys are always decoded, trimmed and converted to lower case.
	 * 
	 * values are always decoded (but may contain any character(s) in any order)
	 * 
	 * @return A frozen FieldMap containing the parsed data of the query string
	 */
	public FieldMap<String,String> getSimpleData() 
	{ 
		return data; 
	}
}
