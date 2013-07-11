//tb130425

package ch.lowres.jlo;

import com.sun.jna.Pointer;

import java.nio.ByteBuffer;

public class Blob
{
	//interface describing liblo
	static LO lo = LIBLO.getInstance();

	//pointer to liblo server
	Pointer loBlob;

	public Blob(Pointer lo_blob)
	{
		loBlob=lo_blob;
	}

	public Blob(int size, ByteBuffer buf)
	{
		loBlob=lo.lo_blob_new(size,buf);
	}

	public Pointer getPointer()
	{
		return loBlob;
	}

	public Pointer getDataPointer()
	{
		return lo.lo_blob_dataptr(loBlob);
	}

	public ByteBuffer getByteBuffer()
	{
		return getDataPointer().getByteBuffer(0l,getSize());
	}

	public ByteBuffer getByteBuffer(long offset,long length)
	{
		return getDataPointer().getByteBuffer(offset,length);
	}

	public int getSize()
	{
		return lo.lo_blob_datasize(loBlob);
	}

	public void free()
	{
		lo.lo_blob_free(loBlob);
	}
}
