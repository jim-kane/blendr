package org.kane.blendr.request;

import java.util.Map;
import java.util.TreeMap;

public class QueryStringBuilder 
{
	private Map<String,String> data = new TreeMap();
	
	public QueryStringBuilder()
	{
	}
	
	public QueryStringBuilder(String query_string)
	{
		QueryString qs = new QueryString(query_string);
		data.putAll(qs.getSimpleData());
	}
	
	public QueryStringBuilder(QueryString qs)
	{
		data.putAll(qs.getSimpleData());
	}
	
	public void set(String key, String value)
	{
		if ( key == null ) return;
		
		key = key.toLowerCase().trim();
		if ( key.length() == 0 ) return;
		
		if ( value == null ) value = "";
		
		data.put(key, value);
	}
	
	public void remove(String key)
	{
		if ( key == null ) return;
		
		key = key.toLowerCase().trim();
		data.remove(key);
	}
	
	public void addCode(String key, String code)
	{
		if ( key == null ) return;
		
		key = key.toLowerCase().trim();
		if ( key.length() == 0 ) return;
		
		String cur_value = data.get(key);
		
		OrCodeSetBuilder builder = new OrCodeSetBuilder(cur_value);
		builder.add(code);
		
		data.put(key, builder.toString());
	}
	
	public void removeCode(String key, String code)
	{
		if ( key == null ) return;
		
		key = key.toLowerCase().trim();
		if ( key.length() == 0 ) return;
		
		String cur_value = data.get(key);
		
		OrCodeSetBuilder builder = new OrCodeSetBuilder(cur_value);
		builder.remove(code);
		
		data.put(key, builder.toString());
	}
	
	public String toString()
	{
		return QueryString.convertNormalizedDecodedDataToString(data);
	}
	
	public String asString() { return toString(); }
	public QueryString asQueryString() { return new QueryString(toString()); }
	
	public void clear() { data.clear(); }
}
