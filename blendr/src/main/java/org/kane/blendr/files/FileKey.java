package org.kane.blendr.files;

import org.eclipse.jetty.http.MimeTypes;
import org.jimmutable.core.exceptions.ValidationException;
import org.jimmutable.core.objects.Stringable;
import org.jimmutable.core.utils.Optional;
import org.jimmutable.core.utils.Validator;

public class FileKey extends Stringable
{
	transient private String extension;
	
	public FileKey(String path)
	{
		super(path);
	}

	
	public void normalize()  
	{
		normalizeTrim();
		normalizeLowerCase(); // always lower case...
		
		String value = getSimpleValue();
		
		if ( value != null )
		{
			while( value.startsWith("/") )
				value = value.substring(1);
			
			while( value.endsWith("/") )
				value = value.substring(0, value.length()-1);
			
			super.setValue(value);
			
			int dot_idx = value.lastIndexOf('.');
			if ( dot_idx != -1 )
			{
				extension = value.substring(dot_idx+1);
				
				if ( extension.contains("/") || extension.length() == 0 )
					extension = null;
			}
		}
	}

	@Override
	public void validate() 
	{
		Validator.notNull(getSimpleValue());
		Validator.min(getSimpleValue().length(), 1);
		
		for ( char ch : getSimpleValue().toCharArray() )
		{
			if ( ch == '/' ) continue;
			if ( ch >= '0' && ch <= '9' ) continue;
			if ( ch >= 'a' && ch <= 'z' ) continue;
			if ( ch == '-' ) continue;
			if ( ch == '.' ) continue;
			if ( ch == '_' ) continue;
			
			throw new ValidationException(String.format("Illegal character in file key %s (invalid character is '%c')", getSimpleValue(), ch));
		}
	}
	
	public String getDefaultMimeType(String default_value)
	{
		if ( extension == null ) return "content/unknown";
		
		switch(extension)
		{
		case "uu": return "application/octet-stream";
		case "exe": return "application/octet-stream";
		case "ps": return "application/postscript";
		case "zip": return "application/zip";
		case "sh": return "application/x-shar";
		case "tar": return "application/x-tar";
		case "snd": return "audio/basic";
		case "au": return "audio/basic";
		case "wav": return "audio/x-wav";
		case "gif": return "image/gif";
		case "png": return "image/png";
		case "jpg": return "image/jpeg";
		case "jpeg": return "image/jpeg";
		case "htm": return "text/html";
		case "html": return "text/html";
		case "php": return "text/html";
		case "cfm": return "text/html";
		case "aspx": return "text/html";
		case "asp": return "text/html";
		case "jsp": return "text/html";
		case "text": return "text/plain";
		case "c": return "text/plain";
		case "cc": return "text/plain";
		case "c++": return "text/plain";
		case "h": return "text/plain";
		case "pl": return "text/plain";
		case "txt": return "text/plain";
		case "java": return "text/plain";
		case "xml": return "application/xml";
		case "pdf": return "application/pdf";
		case "svg": return "image/svg+xml";
		case "csv": return"application/csv";
		case "css": return"text/css";
		case "mer": return "application/octet-stream";
		case "flv": return "flv-application/octet-stream";
		case "swf": return "application/x-shockwave-flash";
		case "ogg": return "video/ogg";
		case "ico": return "image/x-icon";
		case "js": return "text/javascript";
		case "xlsx": return"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";		
		case "xltx": return"application/vnd.openxmlformats-officedocument.spreadsheetml.template";
		case "potx": return"application/vnd.openxmlformats-officedocument.presentationml.template";
		case "ppsx": return"application/vnd.openxmlformats-officedocument.presentationml.slideshow";
		case "pptx": return"application/vnd.openxmlformats-officedocument.presentationml.presentation";
		case "sldx": return"application/vnd.openxmlformats-officedocument.presentationml.slide";
		case "docx": return"application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		case "dotx": return"application/vnd.openxmlformats-officedocument.wordprocessingml.template";		
		case "xlam": return"application/vnd.ms-excel.addin.macroEnabled.12";
		case "xlsb": return"application/vnd.ms-excel.sheet.binary.macroEnabled.12";
		case "mobi": return "application/x-mobipocket-ebook";
		case "epub": return "application/epub+zip";

		default: 
			String ret = MimeTypes.getDefaultMimeByExtension(getSimpleValue());
			if ( ret == null ) return "content/unknown";
			return ret;
		}
	}
	
	public boolean hasExtension() { return Optional.has(extension, null); }
	public String getOptionalExtension(String default_value) { return Optional.getOptional(extension, null, default_value); }
	
	static public FileKey createFileKey(String path, FileKey default_value)
	{
		try
		{
			return new FileKey(path);
		}
		catch(Exception e)
		{
			return default_value;
		}
	}
}
