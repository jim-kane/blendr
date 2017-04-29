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
    
    public void testQueryBuilder()
    {
    	QueryStringBuilder builder = new QueryStringBuilder();
    	
    	assertEquals(builder.asString(), "");
    	
    	builder.set("foo", "bar"); assertEquals(builder.asString(), "foo=bar");
    	builder.set("FOO", "bar"); assertEquals(builder.asString(), "foo=bar");
    	builder.set("FOO", "BaR"); assertEquals(builder.asString(), "foo=BaR");
    	
    	builder.set("bar", "baz"); assertEquals(builder.asString(), "bar=baz&foo=BaR");
    	
    	builder.remove("foo"); assertEquals(builder.asString(), "bar=baz");
    	
    	builder.clear(); assertEquals(builder.asString(), "");
    	
    	builder.addCode("foo", "a"); assertEquals(builder.asString(), "foo=a");
    	builder.addCode("Foo", "b"); assertEquals(builder.asString(), "foo=a%7Cb");
    	builder.addCode("FoO", "c"); assertEquals(builder.asString(), "foo=a%7Cb%7Cc");
    	
    	builder.removeCode("FOO", "a"); assertEquals(builder.asString(), "foo=b%7Cc");
    }
    
    
}