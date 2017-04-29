package org.kane.blendr.url;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class OrCodeSetTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OrCodeSetTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( OrCodeSetTest.class );
    }
    
    public void testOrCodeSet()
    {
    	test("","");
    	test(null,"");
    	test("\n\r\t","");
    	test("|||","");
    	
    	test("foo|bar","bar|foo");
    	test("foo|FOO|bar","bar|foo");
    	test("foo | FoO | bar |\n\r\t\t foo  ","bar|foo");
    }
    
    public void test(String raw, String expected_normalized)
    {
    	OrCodeSet or_set = new OrCodeSet(raw);
    	
    	assertEquals(expected_normalized, or_set.toString());
    }
}