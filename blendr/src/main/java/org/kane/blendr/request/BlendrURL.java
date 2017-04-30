package org.kane.blendr.request;

import java.net.URI;
import java.util.Objects;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.StandardImmutableObject;
import org.jimmutable.core.serialization.FieldDefinition;
import org.jimmutable.core.serialization.TypeName;
import org.jimmutable.core.serialization.reader.ObjectParseTree;
import org.jimmutable.core.serialization.writer.ObjectWriter;
import org.jimmutable.core.utils.Comparison;
import org.jimmutable.core.utils.Optional;
import org.jimmutable.core.utils.Validator;

public class BlendrURL extends StandardImmutableObject<BlendrURL>
{
	static public final TypeName TYPE_NAME = new TypeName("blendr.request"); public TypeName getTypeName() { return TYPE_NAME; }
	
	private Scheme scheme; // required
	private Host host; // required
	private int port; // required
	private AbsolutePath path; // required
	private QueryString query_string; // required
	private Fragment fragment; // optional
	
	static public final FieldDefinition.Enum<Scheme> FIELD_SCHEME = new FieldDefinition.Enum("scheme",null, Scheme.CONVERTER);
	static public final FieldDefinition.Stringable<Host> FIELD_HOST = new FieldDefinition.Stringable("host",null, Host.CONVERTER);
	static public final FieldDefinition.Integer FIELD_PORT = new FieldDefinition.Integer("port",-1);
	
	static public final FieldDefinition.Stringable<AbsolutePath> FIELD_PATH = new FieldDefinition.Stringable("path",null, AbsolutePath.CONVERTER);
	static public final FieldDefinition.Stringable<QueryString> FIELD_QUERY_STRING = new FieldDefinition.Stringable("query_string",null, QueryString.CONVERTER);
	static public final FieldDefinition.Stringable<Fragment> FIELD_FRAGMENT = new FieldDefinition.Stringable("fragment",null, Fragment.CONVERTER);
	
	public BlendrURL(String uri_string)
	{
		try
		{
			URI uri = new URI(uri_string);
			
			scheme = Scheme.CONVERTER.fromCode(uri.getScheme(), null);
			host = new Host(uri.getHost());
			port = uri.getPort();
			
			if ( port <= 0 ) port = scheme.getSimpleDefaultPort();
			
			path = new AbsolutePath(uri.getPath());
			
			String qs = uri.getQuery();
			
			if ( qs == null ) 
				query_string = QueryString.EMPTY;
			else 
				query_string = new QueryString(qs);
			
			if ( uri.getFragment() == null || uri.getFragment().trim().length() == 0 )
			{
				fragment = null;
			}
			else
			{
				fragment = new Fragment(uri.getFragment());
			}
			
			complete();
		}
		catch(ValidationException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new ValidationException("Unable to parse uri: "+uri_string);
		}
	}
	
	public BlendrURL(ObjectParseTree t)
	{
		scheme = t.getEnum(FIELD_SCHEME);
		
		host = t.getStringable(FIELD_HOST);
		port = t.getInt(FIELD_PORT);
		
		path = t.getStringable(FIELD_PATH);
		query_string = t.getStringable(FIELD_QUERY_STRING);
		fragment = t.getStringable(FIELD_FRAGMENT);
	}
	
	public void write(ObjectWriter writer) 
	{
		writer.writeEnum(FIELD_SCHEME, getSimpleScheme());
		writer.writeStringable(FIELD_HOST, getSimpleHost());
		
		
		writer.writeInt(FIELD_PORT, getSimplePort());
		
		writer.writeStringable(FIELD_PATH, getSimplePath());
		writer.writeStringable(FIELD_QUERY_STRING, getSimpleQueryString());
		
		
		writer.writeStringable(FIELD_FRAGMENT, getOptionalFragment(null));
	}
	


	@Override
	public void normalize() 
	{
	}
	
	@Override
	public void validate() 
	{
		Validator.notNull(scheme, host, path, query_string);
		Validator.min(port, 1);
	} 
	

	public int compareTo(BlendrURL o) 
	{
		int ret = Comparison.startCompare();
		
		ret = Comparison.continueCompare(ret, host, o.host);
		ret = Comparison.continueCompare(ret, path, o.path);
		ret = Comparison.continueCompare(ret, query_string, o.query_string);
		ret = Comparison.continueCompare(ret, scheme, o.scheme);
		
		return ret;
	}

	public void freeze() 
	{	
	}

	public int hashCode() 
	{
		return Objects.hash(scheme, host, path, query_string);
	}

	public boolean equals(Object obj) 
	{
		if (!(obj instanceof BlendrURL)) return false;
		
		BlendrURL other = (BlendrURL)obj;
		
		if ( !getSimpleScheme().equals(other.getSimpleScheme()) ) return false;
		if ( !getSimpleHost().equals(other.getSimpleHost()) ) return false;
		if ( getSimplePort() != other.getSimplePort() ) return false;
		if ( !getSimpleQueryString().equals(other.getSimpleQueryString()) ) return false;
		
		if ( !Objects.equals(getOptionalFragment(null),other.getOptionalFragment(null)) ) return false;
		
		return true;
	}

	private String asURL()
	{
		StringBuilder normalized_value = new StringBuilder();
		
		normalized_value.append(scheme.getSimpleCode());
		normalized_value.append("://");
		normalized_value.append(host);
		
		if ( port != scheme.getSimpleDefaultPort() ) 
		{
			normalized_value.append(":");
			normalized_value.append(port);
		}
		
		normalized_value.append(path);
		
		if ( !query_string.isEmpty() )
		{
			normalized_value.append("?");
			normalized_value.append(query_string.toString());
		}
		
		if ( fragment != null )
		{
			normalized_value.append("#");
			normalized_value.append(fragment);
		}
		
		return normalized_value.toString();
	}
	
	public Scheme getSimpleScheme() { return scheme; }
	public Host getSimpleHost() { return host; }

	public int getSimplePort() { return port; }
	public boolean hasNonStandardPort() { return port != scheme.getSimpleDefaultPort(); }
	
	public AbsolutePath getSimplePath() { return path; }
	
	public QueryString getSimpleQueryString() { return query_string; }
	 
	public boolean hasOptionalFragment() { return Optional.has(fragment, null); }
	public Fragment getOptionalFragment(Fragment default_value) { return Optional.getOptional(fragment, null, default_value); }
}
