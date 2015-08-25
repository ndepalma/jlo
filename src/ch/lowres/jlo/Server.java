//tb130425

package ch.lowres.jlo;

import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.Callback;

import java.net.ServerSocket;
import java.net.DatagramSocket;
import java.io.IOException;

public class Server
{
	//interface describing liblo
	static LO lo = LIBLO.getInstance();

	//pointer to liblo server
	Pointer loServer = null;
        Pointer loServerThread = null;

	//String sPort="4444";
	int iProto=1; //UDP

	public Server()
	{
		//using random port, no error handler
		loServerThread=lo.lo_server_thread_new_with_proto(null,iProto,null);
                loServer = lo.lo_server_thread_get_server(loServerThread);
		//System.out.println("server created: "+getUrl());
	}

        public Server(String sport) throws Exception {
                this(sport, LO.LO_PROTOCOL.LO_TCP);
        }

        public Server(String sport, LO.LO_PROTOCOL proto) throws Exception
	{
		int port=0;
                iProto = proto.getVal();
		//int port_use=0;
		boolean isavail=false;
		try
		{
			port=Integer.parseInt(sport);
			isavail=checkIfPortAvail(port);
			//port_use=findNextFreePort(port);
			//System.out.println("found free port "+port_use);
		}
		catch(Exception e)
		{
			throw new Exception("error: port is not an integer number.");
		}
		if(!isavail)
		{
			throw new Exception("error: port "+port+" is already used by another process.");
		}

		//using given port, no error handler
		//loServerThread=lo.lo_server_thread_new_with_proto(""+port,iProto,null);
                //loServer = lo.lo_server_thread_get_server(loServerThread);
                loServer = lo.lo_server_new_with_proto(""+port,iProto,null);
		//System.out.println("server created: "+getUrl());
	}
	public Server(Pointer server)
	{
		loServer=server;
	}
	public Pointer getPointer()
	{
		return loServer;
	}
	public int getPort()
	{
		return lo.lo_server_get_port(loServer);
	}
	public String getUrl()
	{
		return lo.lo_server_get_url(loServer);
	}
	public void free()
	{
            if(loServerThread != null)
		lo.lo_server_thread_free(loServerThread);
            else if(loServer != null)
                lo.lo_server_free(loServer);
	}
	//handle to method ? return method ?
	public void addMethod(String path,String types,Callback handler)
	{
		lo.lo_server_add_method(loServer, path, types, handler, null);
	}

        public void rmMethod(String path, String types) {
                lo.lo_server_del_method (loServer, path, types);
	}

	public void start()
	{
            lo.lo_server_thread_start(loServer);
	}
	public void recv()
	{
		lo.lo_server_recv(loServer);
	}
	public void recv_noblock(int timeout)
	{
		lo.lo_server_recv_noblock(loServer, timeout);
	}
	public int send(NetAddress na,String path,Message msg)
	{
		return lo.lo_send_message_from(na.getPointer(),loServer,path,msg.getPointer());
	}

	public int findNextFreePort(int osc_server_port)
	{
		int scan_limit=osc_server_port+100;

		while (osc_server_port<=scan_limit && !checkIfPortAvail (osc_server_port))
		{
			//keep on seeking next free port
			osc_server_port++;
		}
		//final check
		if (!checkIfPortAvail(osc_server_port))
		{
			return -1;
		}
		return osc_server_port;
	}

	/**
	* Checks to see if a specific port is available.
	*
	* @param port the port to check for availability
	*
	* http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
	*/
	public static boolean checkIfPortAvail(int port)
	{
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		}
		catch (IOException e)
		{
		}
		finally
		{
			if (ds != null)
			{
				ds.close();
			}

			if (ss != null)
			{
				try {
					ss.close();
				}
				catch (IOException e)
				{
					/* should not be thrown */
				}
			}
		}
		return false;
	} //end checkIfPortAvail


}//end class Server
