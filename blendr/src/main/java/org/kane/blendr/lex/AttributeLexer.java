package org.kane.blendr.lex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A lexographical analyzer that is solely used to lex/parse attribute strings.  E.G. foo="bar" baz='quz'
 * 
 * @author jim.kane
 */
public class AttributeLexer 
{
	static private char[] equals_chars = "=".toCharArray();
	static private char[] double_quote_chars = "\"".toCharArray();
	static private char[] single_quote_chars = "\'".toCharArray();
	
	/**
	 * Lex a given string.
	 * 
	 * @param str
	 *            The string to lex/parse
	 * @param default_value
	 *            The value (Map) to return if no valid key/value pairs are
	 *            extracted from the attribute string
	 * @return A HashMap containing all key/value pairs in the attribute string,
	 *         or default_value if no key/value pairs could be extracted
	 */
	static public Attributes lex(String str)
	{
		if ( str == null ) return Attributes.NO_ATTRIBUTES;
		
		str = str.trim();
		if ( str.length() == 0 ) return Attributes.NO_ATTRIBUTES;
		
		Attributes ret = new Attributes();
	
		LexStream stream = new LexStream(str);
		
		while(!stream.isEmpty())
		{
			String key = eatKey(stream);
			if ( key == null ) continue;
			
			stream.eatWhitespace();
			
			if ( !stream.at(equals_chars) ) 
			{
				ret.addAttribute(key, "");
				continue;
			}
			else
			{
				stream.eat(); // eat the =

				String value = eatValue(stream);
				if ( value == null ) value = "";
				
				ret.addAttribute(key, value);
			}
		}
		
		if ( ret.isEmpty() ) return Attributes.NO_ATTRIBUTES;
		
		return ret;
	}
	
	static private String eatKey(LexStream stream)
	{
		stream.eatWhitespace();
	
		StringBuilder key = new StringBuilder();
		
		if ( stream.at(equals_chars) ) 
		{
			stream.eat();
			return null;
		}
		
		while(!stream.isEmpty())
		{
			if ( stream.atWhitespace() ) break;
			if ( stream.at(equals_chars) ) break;
			
			key.append(stream.charAt(0));
			stream.eat();
		}
		
		String ret = key.toString().trim().toLowerCase();
		if ( ret.length() == 0 ) return null;
		
		return ret;
	}
	
	static private String eatValue(LexStream stream)
	{
		stream.eatWhitespace();
		
		char terminator[] = null;
		
		if ( stream.at(double_quote_chars) ) terminator = double_quote_chars;
		if ( stream.at(single_quote_chars) ) terminator = single_quote_chars;
		
		if ( terminator == null ) return null;
		
		stream.eat(); // eat the terminator
		
		StringBuilder ret = new StringBuilder();
		
		while(!stream.isEmpty())
		{
			if ( stream.at(terminator) )
			{
				stream.eat(); // eat the terminator
				break;
			}
			
			ret.append(stream.charAt(0));
			stream.eat();
		}
		
		String ret_str = ret.toString();
		return ret_str;
	}
	
	static private void addTerm(StringBuilder current_term, Map<String,String> ret)
	{
		if ( current_term == null ) return;
		if ( current_term.length() == 0 ) return;
		
		String str = current_term.toString();
		str = str.trim();
		
		int equals_idx = str.indexOf('=');
		if ( equals_idx == -1 ) return;
		
		String key = str.substring(0, equals_idx);
		String value = str.substring(equals_idx+1);
		
		
			
		key = key.trim();
		value = value.trim();
			
		if ( value.startsWith("\"") ) value = value.substring(1);
		if ( value.startsWith("\'") ) value = value.substring(1);
			
		if ( value.endsWith("\"") ) value = value.substring(0,value.length()-1);
		if ( value.endsWith("\'") ) value = value.substring(0,value.length()-1);
			
		if ( key.length() == 0 || value.length() == 0 ) return;
			
		ret.put(key.toLowerCase(),value);
	}
}
