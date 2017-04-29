package org.kane.blendr.lex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jimmutable.core.objects.TransientObject;
import org.jimmutable.core.utils.Validator;


public class Attributes extends TransientObject<Attributes>
{
	static public final Attributes NO_ATTRIBUTES = new Attributes();
	
	private Map<String,String> attribs = new HashMap();
	
	// Mutable constructor used by AttributeLexer only
	protected Attributes() {}
	
	public Attributes(Map<String,String> attribs)
	{
		if ( attribs == null ) attribs = Collections.EMPTY_MAP;
		
		for ( Map.Entry<String, String> entry : attribs.entrySet() )
		{
			addAttribute(entry.getKey(),entry.getValue());
		}
	}
	
	
	public Attributes(String key, String value)
	{
		addAttribute(key,value);
	}
	
	/**
	 * Add an attribute to this collection of attributes
	 * 
	 * (Only AttributeLexer and the Map constructor should call this function)
	 * 
	 * @param key
	 *            The attribute to add
	 * @param value
	 *            The value of the attribute
	 */
	protected void addAttribute(String key, String value)
	{
		if ( key == null ) return;
		if ( value == null ) return;
		
		key = key.trim().toLowerCase();
		
		attribs.put(key, value);
	}
	
	/**
	 * Does a given attribute exist?
	 * 
	 * @param attrib_name
	 *            The name of the attribute
	 * @return True if there is an attribute with the name attrib_name, false
	 *         otherwise
	 */
	public boolean hasAttribute(String attrib_name)
	{
		if ( attribs == null ) return false;
		if ( attrib_name == null ) return false;
		
		return attribs.containsKey(attrib_name.trim().toLowerCase());
	}
	
	/**
	 * Get the value of an attribute
	 * 
	 * @param attrib_name
	 *            The name of the attribute to get
	 * @param default_value
	 *            The value to return if the specified attrib_name does not
	 *            exist
	 * @return The value of the attribute, or default_value if the attribute
	 *         does not exist. Note: the parse of, say {aspect dynamic} will
	 *         have a attribute "dynamic" whose value is the empty string.
	 */
	public String getOptionalAttribute(String attrib_name, String default_value)
	{
		if ( attribs == null ) return default_value;
		if ( attrib_name == null ) return default_value; 
		
		String ret = attribs.get(attrib_name.trim().toLowerCase());
		if ( ret == null ) return default_value;
		return ret;
	}
	
	/**
	 * Are their any attributes?
	 * 
	 * @return True if there are no attributes, false otherwise
	 */
	public boolean isEmpty() { return attribs.isEmpty(); }

	
	public int compareTo(Attributes o) 
	{
		return Integer.compare(attribs.size(), o.attribs.size());
	}
	
	public void normalize() 
	{	
	}

	
	public void validate() 
	{
		Validator.notNull(attribs);
	}

	public int hashCode() 
	{
		return attribs.hashCode();
	}

	public boolean equals(Object obj) 
	{
		if ( !(obj instanceof Attributes) ) return false;
		
		Attributes other = (Attributes)obj;
		
		return attribs.equals(other.attribs);
	}

	
	public String toString() 
	{
		return attribs.toString();
	}
}
