package org.kane.blendr.request;

import org.kane.blendr.request.Scheme;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SchemeTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SchemeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SchemeTest.class );
    }
    
    public void testScheme()
    {
    	testOne("http", Scheme.HTTP);
    	testOne("https", Scheme.HTTPS);
    	
    	testOne("HtTp", Scheme.HTTP);
    	testOne("HTTpS", Scheme.HTTPS);
    	
    	testOne("ftp", Scheme.UNKNOWN);
    }
   
    
    public void testOne(String str, Scheme expected_value)
    {
    	try
    	{
	    	Scheme scheme = Scheme.CONVERTER.fromCode(str, Scheme.UNKNOWN);
	    	
	    	assertEquals(expected_value, scheme);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		assert(false);
    	}
    }
}



