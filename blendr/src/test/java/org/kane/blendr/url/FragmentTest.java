package org.kane.blendr.url;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FragmentTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FragmentTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FragmentTest.class );
    }
    
    public void testValid()
    {
    	testValid("foo", "foo");
    	testValid("bar", "bar");
    	
    	testValid("FOO", "foo");
 
    	testValid("digital-panda", "digital-panda");
    	
    	
    	testValid(" \n\r\tfoo\n\t\n ", "foo");
    }
    
    public void testInvalid()
    {
    	testInvalid("foo.txt");
    	testInvalid(".");
    	testInvalid("");
    	testInvalid(null);
    	testInvalid(".bat");
    	testInvalid("foo.");
    }
    

    public void testValid(String str, String expected_normalized)
    {
    	try
    	{
	    	Fragment obj = new Fragment(str);
	    	
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
    		Fragment obj = new Fragment(host_str);
    		assert(false);
    	}
    	catch(Exception e)
    	{ 
    		// should land here...
    		assert(true);
    	}
    }
    
    
}