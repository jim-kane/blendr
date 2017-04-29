package org.kane.blendr;

import java.net.URLEncoder;
import java.util.Map;

public class URLBuilder 
{
	static private final String ENCODING_UTF_8 = "UTF-8";
	
	/*private String protocol = "http";
	private String userInfo = null;
	
	private String = "localhost";
	private int port = -1; // unset
	
	private String path;
	private Map<String,String> query_string = new HashMap();
	
	String userInfo, String host, int port,
    String path, String query, String fragment*/
	
	
	
	static public void main(String args[]) throws Exception
	{
		/*URI  u = new URI("https://foo:bar@www.google.com");
		
		System.out.println(u.getUserInfo());
		System.out.println(u.getAuthority());*/
		
		System.out.println(URLEncoder.encode("foo bar", ENCODING_UTF_8));
	}
	
	
	
}
