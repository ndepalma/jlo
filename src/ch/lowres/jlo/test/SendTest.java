//tb130425

package ch.lowres.jlo.test;

import ch.lowres.jlo.Message;
import ch.lowres.jlo.NetAddress;
import ch.lowres.jlo.Blob;
import ch.lowres.jlo.Server;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;


public class SendTest
{
	static String sHost="localhost";
	static String sPort="7777";
	static String sPath="/my/path";

	static int iSendCount=100;

	static long msgCounter=0l;

	public static void main(String[] args) throws Exception
	{

		if(args.length>1)
		{
			iSendCount=Integer.parseInt(args[1]);
		}
		if(args.length>0)
		{
			sPort=args[0];
		}

		//optional. messages can be sent without server using random port for each message
		Server srv=new Server("4545");

		for(int i=0;i<iSendCount;i++)
		{

			Message msg=new Message();
			msg.addString("gugus"); //s
			msg.addFloat(123.45f); //f
			msg.addInt(1234); //i
			msg.addLong(msgCounter++); //h
			msg.addDouble(2222.3333d); //d
			msg.addChar('^'); //c

			ByteBuffer buf=ByteBuffer.allocate(1024*4);
			//IntBuffer ibuf=buf.asIntBuffer();
			FloatBuffer fbuf=buf.asFloatBuffer();

			for(int k=0;k<1024;k++)
			{
				//ibuf.put(k);
				fbuf.put(k);
			}

			Blob blob=new Blob(1024*4,buf);
			msg.addBlob(blob); //b

			//msg.addTrue().addFalse().addNil().addInfinitum();

			NetAddress na=new NetAddress("localhost",sPort);

			//================
			//msg.send(na,sPath);

			//sending from server (keeping server port as 'our' originating port)
			srv.send(na,sPath,msg);

			//
			Thread.sleep(10);
		}

		System.out.println("sent "+iSendCount+" times "+sPath+" to host "+sHost+", port "+sPort);
	}
}
