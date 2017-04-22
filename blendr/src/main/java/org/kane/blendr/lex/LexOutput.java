package org.kane.blendr.lex;

import java.util.Collection;
import java.util.List;

import org.jimmutable.core.fields.FieldArrayList;
import org.jimmutable.core.fields.FieldList;
import org.jimmutable.core.objects.TransientImmutableObject;
import org.jimmutable.core.serialization.FieldDefinition;
import org.jimmutable.core.utils.Validator;

/**
 * An encapsulation of the output of the blendr lexer (lexographical analyzer)
 * 
 * @author jim.kane
 *
 */
public class LexOutput extends TransientImmutableObject
{
	private String original_source_code;
	private FieldList<Token> tokens;

	/**
	 * Create a LexOutput object by supplying both the original source code and
	 * the list of tokens
	 * 
	 * @param original_code
	 *            The orignial source code
	 * @param tokens
	 *            The list of tokens "lexed" from the source code
	 */
	public LexOutput(String original_code, List<Token> tokens)
	{
		this.original_source_code = original_code;
		this.tokens = new FieldArrayList(tokens);
		
		complete();
	}
	
	public void normalize() 
	{
		
	}

	public void validate() 
	{
		Validator.notNull(original_source_code);
		Validator.notNull(tokens);
	}
	
	public void freeze()
	{
		tokens.freeze();
	}

	public String getSimpleOriginalSourceCode() { return original_source_code; }
	public List<Token> getSimpleTokens() { return tokens; }

	public int compareTo(Object o) 
	{
		if ( !(o instanceof LexOutput) ) return 0;
		LexOutput other = (LexOutput)o;
		
		return getSimpleOriginalSourceCode().compareTo(other.getSimpleOriginalSourceCode());
	}

	
	public int hashCode() 
	{
		return getSimpleOriginalSourceCode().hashCode();
	}

	
	public boolean equals(Object o) 
	{
		if ( !(o instanceof LexOutput) ) return false;
		LexOutput other = (LexOutput)o;
		
		if ( !getSimpleOriginalSourceCode().equals(other.getSimpleOriginalSourceCode()) ) return false;
		
		return tokens.equals(other.getSimpleTokens());
	}
	
	public String toString() 
	{
		return tokens.toString();
	}
}
