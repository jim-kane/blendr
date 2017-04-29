package org.kane.blendr.url;

import java.net.URI;

import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

public class BlenderURL extends Stringable
{
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
			uri = null;
		}
	}


	@Override
	public void validate() 
	{
		Validator.notNull(uri);
		Validator.notNull(getSimpleValue());
	}
}
