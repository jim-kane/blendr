package org.kane.blendr.url;

import java.net.URI;

import org.jimmutable.core.utils.Optional;

public class URLBuilder 
{
	transient private String protocol;
	transient private String user_info;
	transient private String host;
	transient private int port;
	transient private String path;
	transient private QueryStringBuilder query_string_builder;
	transient private String fragment;
	
	public URLBuilder()
	{
		query_string_builder = new QueryStringBuilder();
	}
	
	public URLBuilder(String url)
	{
		BlenderURL burl = new BlenderURL(url);
		
		this.protocol = burl.getOptionalProtocol(null);
		this.user_info = burl.getOptionalUserInfo(null);
		this.host = burl.getOptionalHost(null);
		this.port = burl.getOptionalPort(-1);
		this.path = burl.getOptionalPath(null);
		this.query_string_builder = new QueryStringBuilder(burl.getSimpleQueryString());
		this.fragment = burl.getOptionalFragment(null);
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
	
	public QueryString getSimpleQueryString() { return query_string_builder.asQueryString(); }
	
	public boolean hasFragment() { return Optional.has(fragment, null); }
	public String getOptionalFragment(String default_value) { return Optional.getOptional(fragment, null, default_value); } 
	
	
	public void setProtocol(String value)
	{
		if ( value == null ) return;
		value = value.toLowerCase().trim();
		
		if ( value.length() == 0 ) return;
		
		protocol = value;
	}
	
	public void removeProtocol() { protocol = null; }
	
	public void setUserInfo(String value) 
	{
		if ( value == null || value.length() == 0 ) return;
		
		user_info = value;
	}
	
	public void removeUserInfo() { user_info = null; }
	
	public void setHost(String value)
	{
		if ( value == null ) return;
		value = value.toLowerCase().trim();
		
		if ( value.length() == 0 ) return;
		
		host = value;
	}
	
	public void removeHost() { host = null; }
	
	public void setPort(int host)
	{
		this.port = port < 0 ? -1 : port;
	}
	
	public void removePort() { port = -1; }
	
	public void setPath(String value) 
	{
		if ( value == null || value.length() == 0 ) return;
		
		path = value;
	}
	
	public void removePath() { path = null; }
	
	public void setQueryString(String value)
	{
		query_string_builder = new QueryStringBuilder(value);
	}
	
	public void removeQueryString() 
	{
		query_string_builder = new QueryStringBuilder();
	}
	
	public void setFragment(String value) 
	{
		if ( value == null || value.length() == 0 ) return;
		
		fragment = value;
	}
	
	public void removeFragment() { fragment = null; }
	
	public void setParam(String key, String value)
	{
		query_string_builder.set(key, value);
	}
	
	public void removeParam(String key)
	{
		query_string_builder.remove(key);
	}
	
	
	public void addToParam(String key, String code)
	{
		query_string_builder.addCode(key, code);
	}
	
	
	public void removeFromParam(String key, String code)
	{
		query_string_builder.removeCode(key, code);
	}
	
	
	public String toString()
	{
		return BlenderURL.composeURIString(protocol, user_info, host, port, path, query_string_builder.asQueryString(), fragment);
	}
	
	public String asString()
	{
		return toString();
	}
	
	public BlenderURL asBlendrURL()
	{
		return new BlenderURL(toString());
	}
}
