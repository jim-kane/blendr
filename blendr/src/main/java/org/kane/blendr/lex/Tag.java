package org.kane.blendr.lex;

import java.util.Map;

/**
 * An enum of all of the valid tags in the blendr language
 * 
 * @author jim.kane
 *
 */
public enum Tag 
{
	ESCAPE("escape"),
	SCRIPT("script"),
	EXECUTE_SCRIPT("!"),
	HTML("html"),
	CSS("css"),
	
	PRIVATE("private"),
	PUBLIC("public"),
	STAFF_PRIVATE("staff-private"),
	CONSUMER_PRIVATE("consumer-private"),
	
	
	AJAX("ajax"),
	INCLUDE("include"),
	INHERIT("inherit"),
	
	ASPECT("aspect"),
	OVERRIDE("override"),
	;
	
	
	private char[] open_characters;
	private char[] close_characters;
	private char[] unary_characters;
	private char[] open_w_attribs_characters;
	
	private String open_string;
	private String close_string;
	private String unary_string;
	private String open_w_attribs_string;
	
	private Tag(String str)
	{
		open_string = String.format("{%s}", str);
		close_string = String.format("{/%s}", str);
		unary_string = String.format("{%s/}", str);
		open_w_attribs_string = String.format("{%s ", str);
		
		this.open_characters = open_string.toCharArray();
		this.close_characters = close_string.toCharArray();
		this.unary_characters = unary_string.toCharArray();
		this.open_w_attribs_characters = open_w_attribs_string.toCharArray();
	}
	
	public String toString() { return open_string; }
	
	/**
	 * Is input at the open version of this tag?
	 * 
	 * @param input The input stream to test
	 * @return True if input is at the open tag, false otherwise
	 */
	public boolean atOpen(LexStream input)
	{
		if ( input == null ) return false;
		
		return input.at(open_characters);
	}
	
	/**
	 * Is input at the close version of this tag?
	 * 
	 * @param input The input stream to test
	 * @return True if input is at the close tag, false otherwise
	 */
	public boolean atClose(LexStream input)
	{
		if ( input == null ) return false;
		
		return input.at(close_characters);
	}
	
	/**
	 * Is input at the unary version of this tag?
	 * 
	 * @param input The input stream to test
	 * @return True if input is at the unary tag, false otherwise
	 */
	public boolean atUnary(LexStream input)
	{
		if ( input == null ) return false;
		
		return input.at(unary_characters);
	}
	
	/**
	 * Is input at the open with attributes version of this tag?
	 * 
	 * @param input The input stream to test
	 * @return True if input is at the open with attributes tag, false otherwise
	 */
	public boolean atOpenWithAttribs(LexStream input)
	{
		if ( input == null ) return false;
		
		return input.at(open_w_attribs_characters);
	}
	
	/**
	 * Eat the open tag
	 * @param input The input stream
	 */
	public void eatOpen(LexStream input)
	{
		if ( input == null ) return;
		input.eat(open_characters.length);
	}
	
	/**
	 * Eat the close tag
	 * @param input The input stream
	 */
	public void eatClose(LexStream input)
	{
		if ( input == null ) return;
		input.eat(close_characters.length);
	}
	
	/**
	 * Eat the unary tag
	 * @param input The input stream
	 */
	public void eatUnary(LexStream input)
	{
		if ( input == null ) return;
		input.eat(unary_characters.length);
	}
	
	/**
	 * Eat the unary tag
	 * @param input The input stream
	 */
	public void eatOpenWithAttribs(LexStream input)
	{
		if ( input == null ) return;
		input.eat(open_w_attribs_characters.length);
	}
	
	public String getSimpleOpenString() { return open_string; }
	public String getSimpleCloseString() { return close_string; }
	public String getSimpleUnaryString() { return unary_string; }
	public String getSimpleOpenWithAttribs() { return open_w_attribs_string; }
	
	
	/**
	 * Create an open tag token
	 * @param position The position (in the source code) of the start of the token
	 * @param attributes The attributes of the open tag (generally, use AttributeLexer.lex to create)
	 * @return A open tag token
	 */
	public TokenOpenTag createOpenToken(int position, Attributes attributes)
	{
		return new TokenOpenTag(this,position, attributes);
	}
	
	/**
	 * Create an close tag token
	 * @param position The position (in the source code) of the start of the token
	 * @return A close tag token
	 */
	public TokenCloseTag createCloseToken(int position)
	{
		return new TokenCloseTag(this,position);
	}
}
