package org.kane.blendr.url;

import java.net.URI;
import org.jimmutable.core.utils.Optional;

import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

public class BlenderURL extends Stringable
{
	static public final URI INVALID = createInvalidURI();
	
	transient private URI uri;
	
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
			uri = new URI(getSimpleValue());
		
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
			
			setValue(uri.toString());
		}
		catch(Exception e)
		{
			uri = INVALID;
		}
	}


	@Override
	public void validate() 
	{
		Validator.notNull(uri);
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
	
	public boolean isValidURL() { return !uri.equals(INVALID); }
	
	
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
}
