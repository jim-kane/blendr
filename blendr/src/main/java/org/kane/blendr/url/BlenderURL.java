package org.kane.blendr.url;

import java.net.URI;
import org.jimmutable.core.utils.Optional;

import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

public class BlenderURL extends Stringable
{
	static public final URI INVALID = createInvalidURI();
	
	transient private String protocol;
	transient private String user_info;
	transient private String host;
	transient private int port;
	transient private String path;
	transient private QueryString query_string;
	transient private String fragment;
	
	public BlenderURL(String url)
	{
		super(url == null ? "" : url.trim());
	}


	@Override
	public void normalize() 
	{
		try
		{
			URI uri = new URI(getSimpleValue());
		
			protocol = uri.getScheme();
			if ( protocol != null ) protocol = protocol.trim().toLowerCase();
			
			user_info = uri.getUserInfo();
			
			host = uri.getHost();
			if ( host != null ) host = host.toLowerCase().trim();
			
			port = uri.getPort();
			
			path = uri.getPath();
			
			String qs = uri.getQuery();
			
			if ( qs == null ) 
				query_string = QueryString.EMPTY;
			else 
				query_string = new QueryString(qs);
			
			fragment = uri.getFragment();
			
			
			qs = query_string.toString();
			if ( qs.length() == 0 ) qs = null;
			
			uri = new URI(protocol, user_info, host, port, path, qs, fragment); // noramlize the url
			
			setValue(composeURIString(protocol, user_info, host, port, path, query_string, fragment));
		}
		catch(Exception e)
		{
			// everything will be unset...
		}
	}


	@Override
	public void validate() 
	{
		Validator.notNull(getSimpleValue());
	} 
	
	
	public boolean hasProtocol() { return Optional.has(protocol, null); }
	public String getOptionalProtocol(String default_value) { return Optional.getOptional(protocol, null, default_value); }
	
	public boolean hasUserInfo() { return Optional.has(user_info, null); }
	public String getOptionalUserInfo(String default_value) { return Optional.getOptional(user_info, null, default_value); }
	
	public boolean hasHost() { return Optional.has(host, null); }
	public String getOptionalHost(String default_value) { return Optional.getOptional(host, null, default_value); }
	
	public boolean hasPort() { return Optional.has(port, -1); }
	public int getOptionalPort(int default_value) { return Optional.getOptional(port, -1, default_value); } 
	
	public boolean hasPath() { return Optional.has(path, null); }
	public String getOptionalPath(String default_value) { return Optional.getOptional(path, null, default_value); }
	
	public QueryString getSimpleQueryString() { return query_string; }
	
	public boolean hasFragment() { return Optional.has(fragment, null); }
	public String getOptionalFragment(String default_value) { return Optional.getOptional(fragment, null, default_value); } 
	
	static private URI createInvalidURI()
	{
		try
		{
			return new URI("blendr-error-invalid-uri");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	static public String composeURIString(String protocol, String user_info, String host, int port, String path, QueryString query_string, String fragment)
	{
		StringBuilder as_string = new StringBuilder();
		
		if ( protocol != null && protocol.length() != 0 )
		{
			as_string.append(protocol);
			as_string.append(":");
		}
		
		if ( user_info != null || host != null || port > 0 )
		{
			as_string.append("//");
		}
		
		if ( user_info != null && user_info.length() != 0 )
		{
			as_string.append(user_info);
			as_string.append("@");
		}
		
		if ( host != null && host.length() > 0 )
		{
			as_string.append(host);
		}
		
		if ( port > 0 )
		{
			as_string.append(":");
			as_string.append(port);
		}
		
		if ( path != null )
		{
			as_string.append(path);
		}
		
		if ( query_string != null && !query_string.isEmpty() )
		{
			as_string.append("?");
			as_string.append(query_string.toString());
		}
		
		
		if ( fragment != null && fragment.length() > 0 )
		{
			as_string.append("#");
			as_string.append(fragment);
		}
		
		return as_string.toString().trim();
	}
}
