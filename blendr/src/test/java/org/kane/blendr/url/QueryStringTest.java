package org.kane.blendr.url;

import java.util.HashMap;
import java.util.Map;

import org.kane.blendr.AttributeLexTest;
import org.kane.blendr.execute.Executor;
import org.kane.blendr.lex.AttributeLexer;
import org.kane.blendr.lex.Attributes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class QueryStringTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public QueryStringTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( QueryStringTest.class );
    }
    
    public void testQueryStrings()
    {
    	{
    		Map<String,String> expected_result = new HashMap();
    		
    		test(null, expected_result);
    		test("", expected_result);
    		test(" ", expected_result);
    	}
    	
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("foo", "bar");
    		
    		test("foo=bar", expected_result);
    		test("FOO=bar", expected_result);
    		
    	}
    	
    	// Test that the value is case sensitive
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("foo", "BAR");
    		
    		test("foo=BAR", expected_result);
    		test("FOO=BAR", expected_result);
    	}
    	
    	// Test that encoded keys and values are properly decoded
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("$", "+");
    		
    		test("%24=%2B", expected_result);
    		test("$=%2B", expected_result);
    	}
    	
    	// Test the encoding of spaces...
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("foo", "hello world");
    		
    		test("foo=hello world", expected_result);
    		test("foo=hello+world", expected_result);
    	}
    	
    	// Test multiple values
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("$", "+");
    		expected_result.put("foo", "bar");
    		
    		
    		test("%24=%2B&foo=bar", expected_result);
    		test("$=%2B&foo=bar", expected_result);
    	}
    	
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("foo", "baz");
    		
    		test("foo=bar&foo=baz", expected_result);
    	}
    
    	
    	{
    		Map<String,String> expected_result = new HashMap();
    		expected_result.put("foo", "");
    		expected_result.put("bar", "");
    		
    		
    		test("foo&bar", expected_result);
    	}
    }
    
    public void testStringEncoding()
    {
    	testStringEncoding(null, "");
    	testStringEncoding("", "");
    	testStringEncoding(" ", "");
    	testStringEncoding("\t\r\n", "");
    	
    	testStringEncoding("foo=bar", "foo=bar");
    	testStringEncoding("FOO=bar", "foo=bar");
    	testStringEncoding("FOO=BaR", "foo=BaR");
    	
    	testStringEncoding("$=%2B", "%24=%2B");
    	
    	testStringEncoding("foo=bar&baz=quz", "baz=quz&foo=bar");
    }
    
    public void test(String query_string, Map<String,String> expected_result)
    {
    	QueryString qs = new QueryString(query_string);
    	
    	assertEquals(qs.getSimpleData(), expected_result);
    }
    
    public void testStringEncoding(String query_string, String expected_result)
    {
    	QueryString qs = new QueryString(query_string);
    	
    	assertEquals(qs.toString(), expected_result);
    }
}

