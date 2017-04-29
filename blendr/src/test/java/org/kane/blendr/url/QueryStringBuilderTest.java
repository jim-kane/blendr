package org.kane.blendr.url;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class QueryStringBuilderTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public QueryStringBuilderTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( QueryStringBuilderTest.class );
    }
    
    public void testURL()
    {
    	test("","");
    	test(null,"");
    	test("  ","");
    	
    	test("HTTP://WWW.GOOGLE.COM","http://www.google.com");
    	test("http://www.google.com?FOO=bar&BAZ=quz","http://www.google.com?baz=quz&foo=bar");
    }
    
    public void test(String src_url, String expected_normalized_url)
    {
    	BlendrURL burl = new BlendrURL(src_url);
    	assertEquals(expected_normalized_url, burl.toString());
    }
    
}