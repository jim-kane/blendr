package org.kane.blendr.lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class used to lex blendr source code
 * 
 * @author jim.kane
 *
 */
public class Lexer 
{
	static private final char escaped_open_brace[] = "&{;".toCharArray();
	static private final char escaped_close_brace[] = "&};".toCharArray();
	static private final char close_brace[] = "}".toCharArray();
	
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
	
	/**
	 * This function eats any escaped content at start of input
	 * 
	 * @return True of something was eaten, false otherwise
	 */
	private boolean eatEscapedContent()
	{
		if ( !Tag.ESCAPE.atOpen(input) ) return false;
		
		addTextTokenUnderConstructionToOutput();
		Tag.ESCAPE.eatOpen(input);
			
		while(!input.isEmpty())
		{
			if ( Tag.ESCAPE.atClose(input) )
			{
				Tag.ESCAPE.eatClose(input);
				break;
			}
			else
			{
				text_token_under_construction.append(input.charAt(0));
				input.eat();
			}
		}
			
		addTextTokenUnderConstructionToOutput();
		
		return true;
	}
	
	private boolean eatEscapedOpenBrace()
	{
		if ( !input.at(escaped_open_brace) ) return false;
		
		input.eat(escaped_open_brace.length);
		text_token_under_construction.append('{');
		
		return true;
	}
	
	private boolean eatEscapedCloseBrace()
	{
		if ( !input.at(escaped_close_brace) ) return false;
		
		input.eat(escaped_close_brace.length);
		text_token_under_construction.append('}');
		
		return true;
	}
	
	
	
	private void processInput()
	{ 
		if ( input.isEmpty() ) return;
		if ( eatEscapedContent() ) return;
		if ( eatEscapedOpenBrace() ) return;
		if ( eatEscapedCloseBrace() ) return;
		
		
		for ( Tag operator : Tag.values() )
		{
			if ( operator == Tag.ESCAPE ) continue; // should never get here because this tag is handled before...
			
			if ( operator.atOpen(input) )
			{
				addTextTokenUnderConstructionToOutput();
				
				tokens.add(operator.createOpenToken(input.getPosition(), Attributes.NO_ATTRIBUTES));
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
			
			if ( operator.atUnary(input) )
			{
				addTextTokenUnderConstructionToOutput();
				
				tokens.add(operator.createOpenToken(input.getPosition(), Attributes.NO_ATTRIBUTES));
				tokens.add(operator.createCloseToken(input.getPosition()));
				operator.eatUnary(input);
				
				return;
			}
			
			if ( operator.atOpenWithAttribs(input) )
			{
				addTextTokenUnderConstructionToOutput();
			
				int start_pos = input.getPosition();
				
				operator.eatOpenWithAttribs(input);
				
				while(!input.isEmpty())
				{
					if ( input.at(close_brace) )
					{
						input.eat();
						break;
					}
					
					text_token_under_construction.append(input.charAt(0));
					input.eat();
				}
				
				Attributes attributes = AttributeLexer.lex(text_token_under_construction.toString());
				
				
				tokens.add(operator.createOpenToken(start_pos, attributes));
				text_token_under_construction = new StringBuilder();
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
