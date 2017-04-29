package org.kane.blendr.url;

import java.util.Map;

import org.jimmutable.core.fields.FieldMap;
import org.jimmutable.core.fields.FieldTreeMap;
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
	static public final QueryString EMPTY = new QueryString("");
	
	// Store the *decoded*, normalized query string data
	transient private FieldMap<String,String> data;
	
	public QueryString(String query_string)
	{
		super(query_string == null ? "" : query_string);
	}
	
	public void normalize() 
	{	
		data = new FieldTreeMap();
		// Populated the decoded data structure (effectively, parse the query string)
		String query_string = getSimpleValue();

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
		
		super.setValue(convertNormalizedDecodedDataToString(data));
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
	
	/**
	 * Convert normalized (but not url encoded) data to a query string
	 * 
	 * @param decoded_data
	 *            The normalized, decoded data to convert. Although any map may
	 *            be used, it is best to use a map that naturally orders on keys
	 *            (e.g. TreeMap, FieldTreeMap)
	 * @return A valid query string
	 */
	static public String convertNormalizedDecodedDataToString(Map<String,String> decoded_data)
	{
		StringBuilder ret = new StringBuilder();

		for ( Map.Entry<String, String> entry : decoded_data.entrySet() )
		{
			if ( ret.length() != 0 ) ret.append("&");

			ret.append(URLCharacterEncoding.encode(entry.getKey(), ""));
			ret.append("=");
			ret.append(URLCharacterEncoding.encode(entry.getValue(), ""));
		}

		return ret.toString();
	}
	
	public boolean hasKey(String key)
	{
		if ( key == null ) return false;
		key = key.toLowerCase().trim();
		
		return data.containsKey(key);
	}
	
	public String getValue(String key, String default_value)
	{
		if ( key == null ) return default_value;
		
		key = key.toLowerCase().trim();
		
		String ret = data.get(key);
		if ( ret == null ) return default_value;
		return ret;
	}
	
	public boolean isEmpty() { return data.isEmpty(); }
}
