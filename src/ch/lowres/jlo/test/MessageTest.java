//tb130425

package ch.lowres.jlo.test;

import ch.lowres.jlo.Message;
import ch.lowres.jlo.Blob;

import java.nio.ByteBuffer;

public class MessageTest
{
	public static void main(String[] args)
	{
		Message msg=new Message();

		msg.addString("gugus");
		msg.addFloat(123.45f);
		msg.addInt(1234);
		msg.addLong(999999999l);
		msg.addDouble(2222.3333d);
		msg.addChar('^');

		Blob blob=new Blob(1024,ByteBuffer.allocate(1024));
		msg.addBlob(blob);

		msg.addTrue().addFalse().addNil().addInfinitum();

		System.out.println("typetag: "+msg.getTypes());
		System.out.println("argc: "+msg.getArgc());
		System.out.println("source: "+msg.getSource());

		System.out.println("read arg0: "+msg.getArg(0));
		System.out.println("read arg1: "+msg.getArg(1));
		System.out.println("read arg2: "+msg.getArg(2));
		System.out.println("read arg3: "+msg.getArg(3));
		System.out.println("read arg4: "+msg.getArg(4));
		System.out.println("read arg5: "+msg.getArg(5));
		System.out.println("read arg6: "+msg.getArg(6));

		System.out.println("read arg0: "+msg.getString(0));
		System.out.println("read arg1: "+msg.getFloat(1));
		System.out.println("read arg2: "+msg.getInt(2));
		System.out.println("read arg3: "+msg.getLong(3));
		System.out.println("read arg4: "+msg.getDouble(4));
		System.out.println("read arg5: "+msg.getChar(5));
		System.out.println("read arg6: "+msg.getBlob(6));

		ByteBuffer buf=msg.getBlob(6).getByteBuffer();
		System.out.println("buf info "+buf);
	}
}
