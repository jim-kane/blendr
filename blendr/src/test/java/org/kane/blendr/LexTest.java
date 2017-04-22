package org.kane.blendr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jimmutable.core.objects.StandardObject;
import org.kane.blendr.lex.LexOutput;
import org.kane.blendr.lex.Lexer;
import org.kane.blendr.lex.Tag;
import org.kane.blendr.lex.Token;
import org.kane.blendr.lex.TokenCloseTag;
import org.kane.blendr.lex.TokenContent;
import org.kane.blendr.lex.TokenOpenTag;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LexTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LexTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LexTest.class );
    }

    public void testTextOnlyLex()
    {
    	String original_source = "Hello World";
    	
    	LexOutput output = Lexer.lex(original_source);
    	
    	// Construct lex by hand
    	
    	LexOutput created_by_hand = new LexOutput(original_source, Arrays.asList(new TokenContent(original_source,0,original_source.length())));
    
    	
    	// Assert that the lexer and the by hand lex are equal
    	assertEquals(output,created_by_hand);
    }
    
    public void testTokenObjects()
    {
    	Token one;
    	Token two;
    	
    	{
    		one = new TokenContent("Hello World", 0, "Hello World".length());
    		two = new TokenContent("Hello World", 0, "Hello World".length());
    		
    		assertEquals(one,two);
    	}
    	
    	{
    		one = new TokenContent("Hello World", 0, "Hello World".length());
    		two = new TokenContent("Hello World!", 0, "Hello World!".length());
    		
    		assert(!one.equals(two));
    	}
    	
    	{
    		one = new TokenContent("Hello World", 0, "Hello World".length());
    		two = new TokenContent("Hello World", 10, "Hello World".length());
    		
    		assert(!one.equals(two));
    	}
    	
    	{
    		one = new TokenOpenTag(Tag.OPERATOR_HTML,0);
    		two = new TokenOpenTag(Tag.OPERATOR_HTML,0);
    		
    		assertEquals(one,two);
    		
    		assertEquals(((TokenOpenTag)one).getSimpleOperator(),Tag.OPERATOR_HTML);
    	}
    	
    	{
    		one = new TokenCloseTag(Tag.OPERATOR_HTML,0);
    		two = new TokenCloseTag(Tag.OPERATOR_HTML,0);
    		
    		assertEquals(one,two);
    		
    		assertEquals(((TokenCloseTag)one).getSimpleOperator(),Tag.OPERATOR_HTML);
    	}
    	
    	{
    		one = new TokenCloseTag(Tag.OPERATOR_HTML,0);
    		two = new TokenOpenTag(Tag.OPERATOR_HTML,0);
    		
    		assert(!one.equals(two));
    	}
    }
    
    
    public void testCompleteLex()
    {
    	String original_source = " {html}This is a test {script} var foo = {html}bar{/html} {/script}.<br/>Test execution: {!}foo{/!}.{/html} ";
    	
    	LexOutput from_lex = Lexer.lex(original_source);
    	
    	// Construct lex by hand
    	List<Token> tokens = new ArrayList();
    	tokens.add(new TokenOpenTag(Tag.OPERATOR_HTML,1));
    	tokens.add(new TokenContent("This is a test ",7, 15));
    	tokens.add(new TokenOpenTag(Tag.OPERATOR_SCRIPT,22));
    	tokens.add(new TokenContent(" var foo = ",30, 11));
    	tokens.add(new TokenOpenTag(Tag.OPERATOR_HTML,41));
    	tokens.add(new TokenContent("bar",47, 3));
    	tokens.add(new TokenCloseTag(Tag.OPERATOR_HTML,50));
    	tokens.add(new TokenContent(" ",57, 1));
    	tokens.add(new TokenCloseTag(Tag.OPERATOR_SCRIPT,58));
    	tokens.add(new TokenContent(".<br/>Test execution: ",67, 22));
    	tokens.add(new TokenOpenTag(Tag.OPERATOR_EXECUTE_SCRIPT,89));
    	tokens.add(new TokenContent("foo",92, 3));
    	tokens.add(new TokenCloseTag(Tag.OPERATOR_EXECUTE_SCRIPT,95));
    	tokens.add(new TokenContent(".",99, 1));
    	tokens.add(new TokenCloseTag(Tag.OPERATOR_HTML,100));
    	
 	
    	//System.out.println(lexOutputToStatements(from_lex));
    	
    	LexOutput by_hand = new LexOutput(original_source, tokens);
    	
    	// Assert that the lexer and the by hand lex are equals
    	assertEquals(from_lex,by_hand);
    }
 
}
