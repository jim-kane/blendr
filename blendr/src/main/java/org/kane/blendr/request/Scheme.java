package org.kane.blendr.request;

import org.jimmutable.core.objects.StandardEnum;
import org.jimmutable.core.utils.Normalizer;
import org.jimmutable.core.utils.Validator;

public enum Scheme implements StandardEnum
{
	HTTP("http", 80), 
	HTTPS("https", 443),
	UNKNOWN("unknown", 80);
	
	static public final MyConverter CONVERTER = new MyConverter();
	
	private String code;
	private int default_port;
	
	private Scheme(String code, int default_port)
	{
		Validator.notNull(code);
		this.code = Normalizer.lowerCase(code);
		this.default_port = default_port;
	}
	
	public String getSimpleCode() { return code; }
	public String toString() { return code; }
	
	public int getSimpleDefaultPort() { return default_port; }
	
	static public class MyConverter extends StandardEnum.Converter<Scheme>
	{
		public Scheme fromCode(String code, Scheme default_value) 
		{
			if ( code == null ) return default_value;
			
			for ( Scheme t : Scheme.values() )
			{
				if ( t.getSimpleCode().equalsIgnoreCase(code) ) 
					return t;
			}
			
			return default_value;
		}
	}
}
