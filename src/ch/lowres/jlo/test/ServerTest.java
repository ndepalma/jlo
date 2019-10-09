//tb130425

package ch.lowres.jlo.test;

import ch.lowres.jlo.Server;
import ch.lowres.jlo.Message;
import ch.lowres.jlo.NetAddress;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;


public class ServerTest
{
	static long receiveCount=0l;

	public interface EventCallback extends Callback 
	{
		boolean newOscMatch(String path, String types, Pointer lo_args, int argc, Pointer lo_message, Pointer user_data);
	}

	static class ECALL implements EventCallback 
	{
		public boolean newOscMatch(String path, String types, Pointer lo_args, int argc, Pointer lo_message, Pointer user_data)
		{
			receiveCount++;
			System.out.println("===message received! "+receiveCount);

			Message msg=new Message(lo_message);
			System.out.println("string "+msg.getString(0));
			System.out.println("float "+msg.getFloat(1));
			System.out.println("int "+msg.getInt(2));
			System.out.println("long "+msg.getLong(3));
			System.out.println("double "+msg.getDouble(4));
			System.out.println("char "+msg.getChar(5));

			//IntBuffer buf=msg.getBlob(6).getByteBuffer().order(ByteOrder.BIG_ENDIAN).asIntBuffer();
			FloatBuffer buf=msg.getBlob(6).getByteBuffer().order(ByteOrder.BIG_ENDIAN).asFloatBuffer();
			System.out.println("buf info "+buf);

			buf.position(buf.capacity()-1);
			System.out.println("buf last element "+buf.get());

			NetAddress na=msg.getSource();

			System.out.println("host "+na.getHostname());
			System.out.println("port "+na.getPort());
			System.out.println("proto "+na.getProtocol());
			System.out.println("url "+na.getUrl());

			///////////////! will lead to lost messages
			//System.gc();

			return true;
		}
	}

	public static void main(String[] args)
	{
		Server srv=null;

		try
		{
			if(args.length>0)
			{
				srv=new Server(args[0]);
			}
			else
			{
				srv=new Server();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("server started on port: "+srv.getPort());

		srv.addMethod("/my/path","sfihdcb",new ECALL());
		//srv.addMethod("/my/path","fis",new ECALL());
		
		System.out.println("method added: /my/path;sfihdcb");

		//local variable srv_ is accessed from within inner class; needs to be declared final
		final Server srv_=srv;

		new Thread()
		{
			public void run()
			{
				//wait for incoming messages
				while(true)
				{
					try
					{
						//!
						//Thread.sleep(1);
						srv_.recv();
					}
					catch(Exception e)
					{
						System.out.println("ERROR "+e.getMessage());
					}
				}
			}
		}.start();//end new thread

	}//end main

}//end class ServerTest
