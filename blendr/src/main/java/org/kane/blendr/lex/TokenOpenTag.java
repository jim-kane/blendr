package org.kane.blendr.lex;

import java.util.Map;

import org.jimmutable.core.utils.Validator;

/**
 * A token that represents a open tag (e.g. {script} or {html}
 * 
 * @author jim.kane
 *
 */
final public class TokenOpenTag extends Token
{
	private Tag operator;
	private Attributes attributes;
	
	public TokenOpenTag(Tag operator, int start_position, Attributes attributes)
	{
		super(start_position,operator.getSimpleOpenString().length());
		this.operator = operator;
		this.attributes = attributes;
		complete();
	}
	
	public void validate() 
	{
		super.validate();
		Validator.notNull(operator);
		Validator.notNull(attributes);
	}
	
	public void freeze()
	{
		
	}

	public String toString() { return operator.getSimpleOpenString(); }
	public Tag getSimpleOperator() { return operator; }
	
	public Attributes getSimpleAttributes() { return attributes; }
	
	
	public boolean equals(Object o) 
	{
		if ( !super.equals(o) ) return false;
		
		if ( !(o instanceof TokenOpenTag) ) return false;
		
		TokenOpenTag t = (TokenOpenTag)o;
		
		return getSimpleOperator().equals(t.getSimpleOperator()) && getSimpleAttributes().equals(t.getSimpleAttributes());
	}
	
	public String diagnosticPrint()
	{
		return String.format(
				"Token: %s\nOperator: %s\nAttributes: %s\nStart Position: %d\nLength: %d", 
				"TokenOpenTag", 
				operator.toString(), 
				attributes.toString(),
				super.getSimpleStartPosition(), 
				super.getSimpleLength());
	}
}
