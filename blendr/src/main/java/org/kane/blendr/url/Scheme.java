package org.kane.blendr.url;

import org.jimmutable.core.objects.StandardEnum;
import org.jimmutable.core.utils.Normalizer;
import org.jimmutable.core.utils.Validator;

public enum Scheme implements StandardEnum
{
	HTTP("http"), 
	HTTPS("https"),
	UNKNOWN("unknown");
	
	static public final MyConverter CONVERTER = new MyConverter();
	
	private String code;
	
	private Scheme(String code)
	{
		Validator.notNull(code);
		this.code = Normalizer.lowerCase(code);
	}
	
	public String getSimpleCode() { return code; }
	public String toString() { return code; }
	
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
