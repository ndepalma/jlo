//tb130425

package ch.lowres.jlo.test;

import ch.lowres.jlo.Blob;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class BlobTest
{
	public static void main(String[] args)
	{
		ByteBuffer buf=ByteBuffer.allocate(1024*4);

		buf.order(ByteOrder.BIG_ENDIAN);
		//buf.order(ByteOrder.LITTLE_ENDIAN);

		for(int i=0;i<1024;i++)
		{
			buf.putInt(i);
		}

		Blob blob=new Blob(1024*4,buf);

		System.out.println("size: "+blob.getSize());

		//============

		ByteBuffer buf2=blob.getByteBuffer();

		System.out.println("order "+buf2.order());
		//must match order of sent buffer!!
		buf2.order(ByteOrder.BIG_ENDIAN);
		System.out.println("order "+buf2.order());

		IntBuffer ib = buf2.asIntBuffer();
		ib.position(1024-1);
		System.out.println("pos 1024-1 "+ib.get());
/*
		ib.rewind();
		for(int j=0;j<1024;j++)
		{
			System.out.print("."+ib.get());
		}
*/

	} //end main
}//end class BlobTest
