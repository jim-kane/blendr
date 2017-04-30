package org.kane.blendr.request;

import org.kane.blendr.request.OrCodeSetBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class OrCodeSetBuilderTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OrCodeSetBuilderTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( OrCodeSetBuilderTest.class );
    }
    
    public void testOrCodeSet()
    {
    	OrCodeSetBuilder builder = new OrCodeSetBuilder();
    	
    	assertEquals(builder.asString(), "");
    	
    	builder.add("foo");  assertEquals(builder.asString(), "foo");
    	
    	builder.add("FOO");  assertEquals(builder.asString(), "foo");
    	
    	builder.add("bar");  assertEquals(builder.asString(), "bar|foo");
    	builder.add(null);  assertEquals(builder.asString(), "bar|foo");
    	builder.add("");  assertEquals(builder.asString(), "bar|foo");
    	builder.add(" ");  assertEquals(builder.asString(), "bar|foo");
    	builder.add("\n\r\t");  assertEquals(builder.asString(), "bar|foo");
    	
    	builder.remove("baz"); assertEquals(builder.asString(), "bar|foo");
    	builder.remove("bar"); assertEquals(builder.asString(), "foo");
    	
    	builder.clear(); assertEquals(builder.asString(), "");
    }
    
    
}