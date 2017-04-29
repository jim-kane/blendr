package org.kane.blendr.url;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCharacterEncoding 
{
	static private final String ENCODING_UTF_8 = "UTF-8";
	
	static public String encode(String str, String default_value)
	{
		if ( str == null ) return default_value;
		
		try
		{
			return URLEncoder.encode(str,ENCODING_UTF_8);
		}
		catch(Exception e)
		{
			return default_value;
		}
	}
	
	static public String decode(String str, String default_value)
	{
		if ( str == null ) return default_value;
		
		try
		{
			return URLDecoder.decode(str,ENCODING_UTF_8);
		}
		catch(Exception e)
		{
			return default_value;
		}
	}
}
