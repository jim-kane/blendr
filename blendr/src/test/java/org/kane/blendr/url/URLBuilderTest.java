package org.kane.blendr.url;

import java.net.URI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class URLBuilderTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public URLBuilderTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( URLBuilderTest.class );
    }
    
    public void testURL()
    {
    	URLBuilder builder = new URLBuilder();
    	
    	assertEquals(builder.asString(),"");
    	
    	builder.setPath("/foo/bar.html"); assertEquals(builder.asString(),"/foo/bar.html");
    	
    	builder.setParam("FOO", "BAR"); assertEquals(builder.asString(),"/foo/bar.html?foo=BAR");
    	
    	builder.addToParam("FOO", "BAZ"); assertEquals(builder.asString(),"/foo/bar.html?foo=bar%7Cbaz");
    	
    	builder.setFragment("middle"); assertEquals(builder.asString(),"/foo/bar.html?foo=bar%7Cbaz#middle");
    }
    
}
