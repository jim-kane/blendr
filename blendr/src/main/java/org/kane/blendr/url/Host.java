package org.kane.blendr.url;

import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Validator;

/**
 * This class is a Stringable wrapper around the Host field of a URL
 * 
 * NOTE: This class does not support IPv6 addresses as host names (which are
 * written in brackets []).  As a result, belndr does not support IPv6 (yet)
 * 
 * @author jim.kane
 *
 */
public class Host extends Stringable
{
	/**
	 * Construct a host from a String
	 * 
	 * @param host
	 */
	public Host(String host_str)
	{
		super(host_str);
	}

	public void normalize() 
	{
		normalizeLowerCase();
		normalizeTrim();
	}
	
	public void validate()
	{
		String value = super.getSimpleValue();
		
		Validator.notNull(value);
		
		for ( char ch : value.toCharArray() )
		{
			if ( ch >= 'a' && ch <= 'z' ) continue;
			if ( ch >= '0' && ch <= '9' ) continue;
			if ( ch == '.' ) continue;
			if ( ch == '-' ) continue;
			
			throw new ValidationException(String.format("Illegal character in host name %s, '%c'", value, ch));
		}
		
		if ( value.startsWith("-") )  throw new ValidationException("Host names may not start with a -");
		if ( value.startsWith(".") ) throw new ValidationException("Host names may not start with a .");
		
		if ( value.endsWith("-") )  throw new ValidationException("Host names may not end with a -");
		if ( value.endsWith(".") ) throw new ValidationException("Host names may not end with a .");
		
		if ( !value.contains(".") ) throw new ValidationException("Host name must contain a minimum of two labels, seperated by a . (e.g. google.com)");
		
		if ( value.contains("..") ) throw new ValidationException("Host names may not contain blank labels");
		if ( value.contains(".-") || value.contains("-.") ) throw new ValidationException("Labels in a host name may not start of end with a -");
	}
	
	/**
	 * A host name is made up of labels. For example, the host www.google.com
	 * has the labels [www, google, com]
	 * 
	 * This function gets the labels of a host. It is "somewhat" expensive to
	 * call, as it creates a String array to return, so it is best to not call
	 * in a loop etc.
	 * 
	 * @return A string array containing the labels of a Host. This array is
	 *         created fresh with each function call, so changing its contents
	 *         does not change the Host object.
	 */
	public String[] getSimpleLabels() { return getSimpleValue().split("\\."); }
	
	/**
	 * Get the top level domain of a host. For example, the TLD of
	 * www.google.com is com
	 * 
	 * If called on an IPv4 address, the TLD will get the last octet.
	 * 
	 * @return This hosts TLD
	 */
	public String getSimpleTopLevelDomainName() 
	{ 
		String labels[] = getSimpleLabels();
		
		return labels[labels.length-1];
	}
}
