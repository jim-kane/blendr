package org.kane.blendr.lex;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to lex blendr source code
 * 
 * @author jim.kane
 *
 */
public class Lexer 
{
	transient private LexStream input;
	transient private StringBuilder text_token_under_construction = new StringBuilder();

	transient private String original_source_code;
	transient private List<Token> tokens = new ArrayList();
	
	
	private Lexer(String input_raw)
	{
		this.input = new LexStream(input_raw);
		this.original_source_code = input_raw;
		
		// remove any leading whitespace...
		while( !input.isEmpty() && Character.isWhitespace(input.charAt(0)) )
		{
			input.eat();
		}
		
		while( !input.isEmpty() )
		{
			processInput();
		}
		
		trimWhitespaceFromEndOfTextTokenUnderConstruction();
		addTextTokenUnderConstructionToOutput();
	}
	
	private void addTextTokenUnderConstructionToOutput()
	{
		String str = text_token_under_construction.toString();
		
		if ( str.length() != 0 )
		{
			Token token = new TokenContent(str,input.getPosition()-str.length(),str.length());
			tokens.add(token);
		}
		
		text_token_under_construction = new StringBuilder();
	}
	
	private void trimWhitespaceFromEndOfTextTokenUnderConstruction()
	{
		while(true)
		{
			if ( text_token_under_construction.length() == 0 ) break;
			
			char last_ch = text_token_under_construction.charAt(text_token_under_construction.length()-1);
			
			if ( !Character.isWhitespace(last_ch) ) break;
			
			text_token_under_construction.deleteCharAt(text_token_under_construction.length()-1);
		}
	}
	
	private void processInput()
	{ 
		if ( input.isEmpty() ) return;
		
		for ( Tag operator : Tag.values() )
		{
			if ( operator.atOpen(input) )
			{
				addTextTokenUnderConstructionToOutput();
				
				tokens.add(operator.createOpenToken(input.getPosition()));
				operator.eatOpen(input);
				
				return;
			}
			
			if ( operator.atClose(input) )
			{
				addTextTokenUnderConstructionToOutput();
				
				tokens.add(operator.createCloseToken(input.getPosition()));
				operator.eatClose(input);
				
				return;
			}
			
		}
		
		text_token_under_construction.append(input.charAt(0));
		input.eat();
	}
	
	private LexOutput createSimpleOutput() 
	{ 
		return new LexOutput(original_source_code, tokens);
	}
	
	/**
	 * Lex a supplied piece of blendr source_code
	 * 
	 * @param source_code
	 *            The code to lex
	 * @return A LexOutput object containing the lexographically analized
	 *         source_code
	 */
	static public LexOutput lex(String source_code)
	{
		if ( source_code == null ) source_code = "";
		Lexer lexer = new Lexer(source_code);
		
		return lexer.createSimpleOutput();
	}
}
