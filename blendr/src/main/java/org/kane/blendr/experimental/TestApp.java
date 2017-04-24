package org.kane.blendr.experimental;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class TestApp 
{
	public static void main(String[] args) throws Exception
	{
		Server server = new Server(8080);
		
		server.setHandler(new HelloHandler());
		
		server.start();
		server.join();
	}
	
	static public class HelloHandler extends AbstractHandler
	{
	    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	    {
	    	System.out.println(request.getRequestURI());
	    	
	        response.setContentType("text/html;charset=utf-8");
	        response.setStatus(HttpServletResponse.SC_OK);
	        baseRequest.setHandled(true);
	        
	        System.out.println(request.getHeaderNames());
	        
	        response.getWriter().println(String.format("<h1>Hello World -- %s</h1>",target));
	    }
	}
}
