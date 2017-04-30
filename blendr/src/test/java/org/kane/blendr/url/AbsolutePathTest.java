package org.kane.blendr.url;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AbsolutePathTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AbsolutePathTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AbsolutePathTest.class );
    }
    
    public void testValid()
    {
    	testValid("","/");
    	testValid(null,"/");
    	
    	testValid("foo.txt", "/foo.txt");
    	testValid("bar", "/bar");
    	testValid("/bar/","/bar");
    	
    	
    	testValid("FoO.txt", "/foo.txt");
    	testValid("BAR", "/bar");
    	
    	
    	testValid("digital-panda", "/digital-panda");
    	testValid("www.digital-panda.COM","/www.digital-panda.com");
    	
    	testValid(" \n\r\tfoo\n\t\n ", "/foo");
    	
    	testValid("foo.bar.baz.google.COM", "/foo.bar.baz.google.com");
    	testValid("127.0.0.1", "/127.0.0.1");
    	
    	testValid("foo\\bar\\baz", "/foo/bar/baz");
    	testValid("\\\\foo / bar \n\r\t \\ baz", "/foo/bar/baz");
    	testValid("foo/bar//", "/foo/bar");
    }
    
    public void testInvalid()
    {
    	testInvalid("/../index.html");
    	testInvalid("..");
    	testInvalid("/foo/.bat");
    	testInvalid("jim@kane");
    	testInvalid(".bat");
    	testInvalid("foo.");
    }
    
    public void testExtension()
    {
    	assertEquals(new AbsolutePath("foo.txt").getOptionalExtension(null), "txt");
    	assertEquals(new AbsolutePath("FOO.TXT").getOptionalExtension(null), "txt");
    	
    	assertEquals(new AbsolutePath("/a/b/c/foo.txt").getOptionalExtension(null), "txt");
    	assertEquals(new AbsolutePath("/BAR/FOO.TXT").getOptionalExtension(null), "txt");
    	
    	assertEquals(new AbsolutePath("foo").getOptionalExtension(null), null);
    	assertEquals(new AbsolutePath("www.google.COM").getOptionalExtension(null), "com");
    	
    	assertEquals(new AbsolutePath("/foo").getOptionalExtension(null), null);
    	assertEquals(new AbsolutePath("/bar/baz/plaz").getOptionalExtension(null), null);
    }
    
    public void testHasExtension()
    {
    	assertEquals(new AbsolutePath("foo.txt").hasExtension(), true);
    	assertEquals(new AbsolutePath("FOO.TXT").hasExtension(), true);
    	
    	assertEquals(new AbsolutePath("/a/b/c/foo.txt").hasExtension(), true);
    	assertEquals(new AbsolutePath("/BAR/FOO.TXT").hasExtension(), true);
    	
    	assertEquals(new AbsolutePath("foo").hasExtension(), false);
    	assertEquals(new AbsolutePath("www.google.COM").hasExtension(), true);
    	
    	assertEquals(new AbsolutePath("/foo").hasExtension(), false);
    	assertEquals(new AbsolutePath("/bar/baz/plaz").hasExtension(), false);
    }
    
    public void testLabels()
    {
    	{
    		AbsolutePath path = new AbsolutePath("/");
    		
    		assertEquals(path.getSimpleLabels().size(),0);
    	}
    	
    	{
    		AbsolutePath path = new AbsolutePath("/foo/bar/baz.html");
    		
    		assertEquals(path.getSimpleLabels().size(),3);
    		
    		assertEquals(path.getSimpleLabels().get(0).toString(),"foo");
    		assertEquals(path.getSimpleLabels().get(1).toString(),"bar");
    		assertEquals(path.getSimpleLabels().get(2).toString(),"baz.html");
    	}
    	
    	{
    		AbsolutePath path = new AbsolutePath("\\\\FOO//baR/bAz.html");
    		
    		assertEquals(path.getSimpleLabels().size(),3);
    		
    		assertEquals(path.getSimpleLabels().get(0).toString(),"foo");
    		assertEquals(path.getSimpleLabels().get(1).toString(),"bar");
    		assertEquals(path.getSimpleLabels().get(2).toString(),"baz.html");
    	}
    	
    }
    
    public void testValid(String str, String expected_normalized)
    {
    	try
    	{
	    	AbsolutePath obj = new AbsolutePath(str);
	    	
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
    		AbsolutePath obj = new AbsolutePath(host_str);
    		assert(false);
    	}
    	catch(Exception e)
    	{ 
    		// should land here...
    		assert(true);
    	}
    }
    
    
}
