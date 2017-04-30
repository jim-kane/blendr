package org.kane.blendr.request;

import org.kane.blendr.request.PathLabel;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PathLabelTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PathLabelTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PathLabelTest.class );
    }
    
    public void testValid()
    {
    	testValid("foo.txt", "foo.txt");
    	testValid("bar", "bar");
    	
    	testValid("FoO.txt", "foo.txt");
    	testValid("BAR", "bar");
    	
    	
    	testValid("digital-panda", "digital-panda");
    	testValid("www.digital-panda.COM","www.digital-panda.com");
    	
    	testValid(" \n\r\tfoo\n\t\n ", "foo");
    	
    	testValid("foo.bar.baz.google.COM", "foo.bar.baz.google.com");
    	testValid("127.0.0.1", "127.0.0.1");
    }
    
    public void testInvalid()
    {
    	testInvalid("");
    	testInvalid("..");
    	testInvalid("jim kane");
    	testInvalid("jim@kane");
    	testInvalid(".bat");
    	testInvalid("foo.");
    }
    
    public void testExtension()
    {
    	assertEquals(new PathLabel("foo.txt").getOptionalExtension(null), "txt");
    	assertEquals(new PathLabel("FOO.TXT").getOptionalExtension(null), "txt");
    	
    	assertEquals(new PathLabel("foo").getOptionalExtension(null), null);
    	assertEquals(new PathLabel("www.google.COM").getOptionalExtension(null), "com");
    }
    
    public void testHasExtension()
    {
    	assertEquals(new PathLabel("foo.txt").hasExtension(), true);
    	assertEquals(new PathLabel("FOO.TXT").hasExtension(), true);
    	
    	assertEquals(new PathLabel("foo").hasExtension(), false);
    	assertEquals(new PathLabel("www.google.COM").hasExtension(), true);
    }
    
    public void testValid(String str, String expected_normalized)
    {
    	try
    	{
	    	PathLabel obj = new PathLabel(str);
	    	
	    	assertEquals(obj.toString(), expected_normalized);
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
    		PathLabel obj = new PathLabel(host_str);
    		assert(false);
    	}
    	catch(Exception e)
    	{ 
    		// should land here...
    		assert(true);
    	}
    }
    
    
}


