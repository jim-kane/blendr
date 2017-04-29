package org.kane.blendr.url;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BlendrURLTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BlendrURLTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BlendrURLTest.class );
    }
    
    public void testURL()
    {
    	testOne(null,"");
    	testOne("","");
    	
    	testOne("/foo/bar.html","/foo/bar.html");
    	
    	testOne("/foo/bar.html?foo=bar&baz=quz","/foo/bar.html?baz=quz&foo=bar");
    	testOne("/foo/bar.html?FOO=bar&BAZ=quz","/foo/bar.html?baz=quz&foo=bar");
    	
    	
    	testOne("HTTP://WWW.GOOGLE.COM/foo/bar.html?FOO=bar&BAZ=quz","http://www.google.com/foo/bar.html?baz=quz&foo=bar");
    }
    
    public void testOne(String original, String expected)
    {
    	BlendrURL url = new BlendrURL(original);
    	
    	assertEquals(expected, url.toString());
    }
}
