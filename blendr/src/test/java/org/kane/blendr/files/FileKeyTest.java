package org.kane.blendr.files;

import org.eclipse.jetty.http.MimeTypes;
import org.kane.blendr.url.BlendrURLTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FileKeyTest extends TestCase
{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileKeyTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FileKeyTest.class );
    }
    
    public void testFileKey()
    {
    	testOne(null,null);
    	testOne("",null);
    	
    	testOne("foo.txt","foo.txt");
    	testOne("/foo.txt","foo.txt");
    	
    	testOne("/FOO.TXT","foo.txt");
    	
    	testOne("/foo/", "foo");
    	testOne("//foo//", "foo");
    	
    	testOne(" /foo/bar/baz.html ", "foo/bar/baz.html");
    	
    	testOne("jim@kane",null);
    	
    	testOne("/nice-name/index.html", "nice-name/index.html");
    	
    	testOne("/nice_name/index99.html", "nice_name/index99.html");
    	
    	testOne("/jim kane/foo.html", null);
    	testOne("/foo/bar/", "foo/bar");
    }
    
    public void testExtension()
    {
    	testExtension("/foo/bar.html","html");
    	testExtension("/foo/bar.HTML","html");
    	
    	testExtension("/foo/bar.",null);
    	testExtension("/foo.bar/baz",null);
    	
    	testExtension("foo",null);
    }
    

    
    public void testOne(String path, String expected_to_string)
    {
    	FileKey key = FileKey.createFileKey(path, null);
    	
    	if ( key == null && expected_to_string == null ) return;
    	if ( key == null ) assert(false);
    	
    	assertEquals(expected_to_string, key.toString());
    }
    
    public void testExtension(String path, String expected_extension)
    {
    	FileKey key = FileKey.createFileKey(path, null);
    	if ( key == null ) assert(false);
    	
    	assertEquals(expected_extension, key.getOptionalExtension(null));
    }
    
}