package org.kane.blendr;

import java.util.HashMap;
import java.util.Map;

import org.kane.blendr.execute.Executor;
import org.kane.blendr.lex.AttributeLexer;
import org.kane.blendr.lex.Attributes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AttributeLexTest extends TestCase
{
	private Executor executor = new Executor();
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AttributeLexTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AttributeLexTest.class );
    }
    
    public void testLexer()
    {
    	// Empty tests
    	{
	    	testLex(null,null);
	    	testLex("",null);
	    	testLex(" ",null);
	    	testLex("  ",null);
	    	testLex("\n\r\t",null);
	    	testLex("\t\t\t",null);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "bar");
    		
    		testLex("foo=\'bar\'",result);
    		testLex(" foo = \'bar\'  ",result);
    		
    		testLex("foo=\"bar\"",result);
    		testLex("foo=\"bar",result);
    		testLex("foo=\'bar",result);
    		testLex(" foo = \"bar\"  ",result);
    		testLex("\r\r\r\tfoo\n\r=\t\"bar\"\n\n",result);
    	}
    	
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "bar");
    		result.put("baz", "quz");
    		
    		testLex("foo='bar' baz='quz'",result);
    		testLex("foo=\"bar\" baz='quz'",result);
    		testLex("foo=\"bar\" baz=\"quz\"",result);
    		testLex("foo=\"bar\"baz=\"quz\"",result);
    		testLex("foo='bar'baz='quz'",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "bar");
    		result.put("baz", "");
    		
    		testLex("foo='bar' baz",result);
    		testLex("baz foo='bar'",result);
    		testLex("foo=\"bar\" baz",result);
    		testLex("baz foo=\"bar\"",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("baz", "");
    		
    		testLex("baz",result);
    		testLex(" baz\n", result);
    		testLex("baz=",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "air \"quotes\" suck");
    		
    		testLex("foo='air \"quotes\" suck'",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "air 'quotes' suck");
    		
    		testLex("foo=\"air 'quotes' suck\"",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", " space ");
    		
    		testLex("foo=\" space \"",result);
    		testLex("foo=' space '",result);
    	}
    	
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "");
    		testLex("=foo",result);
    		testLex("foo=",result);
    		testLex("foo====",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("'foo'", "");
    		testLex("'foo'",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("'foo", "");
    		testLex("'foo",result);
    	}
    	
    	{
    		Map<String,String> result = new HashMap();
    		result.put("foo", "");
    		result.put("bar", "");
    		
    		testLex("foo=bar",result);
    	}
    	
    }
    
    public void testLex(String code, Map<String,String> expected_result)
    {
    	Attributes from_map = new Attributes(expected_result);
    	
    	assertEquals(AttributeLexer.lex(code), from_map);
    }
}
