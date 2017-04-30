package org.kane.blendr.files;

import org.jimmutable.core.objects.TransientImmutableObject;
import org.jimmutable.core.utils.Comparison;
import org.jimmutable.core.utils.Optional;
import org.jimmutable.core.utils.Validator;

public class FileStatus extends StandardImmutableObject<FileStatus>
{
	private FileKey key; // requred
	private boolean exists; // required
	private long last_modified_time; // optional, unset value = -1
	private long file_size; // optional, unset value = -1
	
	public FileStatus(FileKey key, boolean exists, long last_modified_time, long file_size)
	{
		this.key = key;
		this.exists = exists;
		
		if ( exists )
		{
			this.last_modified_time = last_modified_time;
			this.file_size = file_size;
		}
		else
		{
			this.last_modified_time = -1;
			this.file_size = -1;
		}
	}

	public FileKey getSimpleKey() { return key; }
	public boolean getSimpleExists() { return exists; }
	
	public boolean hasLastModifiedTime() { return Optional.has(last_modified_time, -1); }
	public long getOptionalLastModifiedTime(long default_value) { return Optional.getOptional(last_modified_time, -1, default_value); }
	
	public boolean hasFileSize() { return Optional.has(file_size, -1); }
	public long getOptinoalFileSize(long default_value) { return Optional.getOptional(file_size, -1, default_value); }
	
	
	public int compareTo(FileStatus o) 
	{
		int ret = Comparison.startCompare();
		
		ret = Comparison.continueCompare(ret, key.getSimpleValue(), o.key.getSimpleValue());
		ret = Comparison.continueCompare(ret, last_modified_time, o.last_modified_time);
		
		return ret;
	}

	public void freeze() 
	{	
	}

	
	public void normalize() 
	{
		if ( !exists )
		{
			last_modified_time = -1;
			file_size = -1;
		}
	}

	
	public void validate() 
	{
		Validator.notNull(key);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
