package org.kane.blendr.request;

import java.util.Set;
import java.util.TreeSet;

public class OrCodeSetBuilder 
{
	private Set<String> code_set = new TreeSet();
	
	public OrCodeSetBuilder()
	{
		
	}
	
	public OrCodeSetBuilder(String as_str)
	{
		OrCodeSet tmp = new OrCodeSet(as_str);
		code_set.addAll(tmp.getSimpleCodeSet());
	}
	
	public void add(String code)
	{
		if ( code == null ) return;
		
		code = code.toLowerCase().trim();
		if ( code.length() == 0 ) return;
		
		code_set.add(code);
	}
	
	public void remove(String code)
	{
		if ( code == null ) return;
		
		code = code.toLowerCase().trim();
		if ( code.length() == 0 ) return;
		
		code_set.remove(code);
	}
	
	public String toString()
	{
		StringBuilder normalized_value = new StringBuilder(); 
		
		for(String code : code_set)
		{
			if ( normalized_value.length() != 0 )
				normalized_value.append("|");
			
			normalized_value.append(code);
		}
		
		return normalized_value.toString();
	}
	
	public String asString() { return toString(); }
	public OrCodeSet asOrCodeSet() { return new OrCodeSet(toString()); }
	
	public void clear() { code_set.clear(); }
}
