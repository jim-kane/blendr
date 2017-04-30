package org.kane.blendr.url;

import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HostTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HostTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HostTest.class );
    }
    
    public void testValidHosts()
    {
    	testValid("www.google.com", "www.google.com");
    	testValid("WWW.GOOGLE.COM", "www.google.com");
    	testValid("www.digital-panda.com", "www.digital-panda.com");
    	testValid("fark.com","fark.com");
    	testValid("foo.bar.baz.google.COM", "foo.bar.baz.google.com");
    	testValid("127.0.0.1", "127.0.0.1");
    }
    
    public void testInvalidHosts()
    {
    	testInvalid("");
    	testInvalid("foo");
    	testInvalid("127.0.0.1:80");
    	testInvalid("google..com");
    	testInvalid("www.-foo.com");
    	testInvalid("www.foo-.com");
    	testInvalid("www.foo bar.com");
    	testInvalid("www.foo_bar.com");
    }
    
    public void testLabels()
    {
    	Host h = new Host("www.GOOGLE.com");
    	
    	assertEquals(h.getSimpleLabels().length, 3);
    	assertEquals(h.getSimpleLabels()[0], "www");
    	assertEquals(h.getSimpleLabels()[1], "google");
    	assertEquals(h.getSimpleLabels()[2], "com");
    	
    	assertEquals(h.getSimpleTopLevelDomainName(), "com");
    }
    
    public void testValid(String host_str, String expected_normalized)
    {
    	try
    	{
	    	Host host = new Host(host_str);
	    	
	    	assertEquals(host.toString(), expected_normalized);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		assert(false);
    	}
    }
    
    public void testInvalid(String host_str)
    {
    	try
    	{
    		Host host = new Host(host_str);
    		assert(false);
    	}
    	catch(Exception e)
    	{ 
    		// should land here...
    		assert(true);
    	}
    }
    
    
}


