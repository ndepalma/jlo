//tb130425

package ch.lowres.jlo;

import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.Callback;

public class Server
{
	//interface describing liblo
	static LO lo = LIBLO.getInstance();

	//pointer to liblo server
	Pointer loServer;

	//String sPort="4444";
	int iProto=1; //UDP

	public Server()
	{
		//using random port, no error handler
		loServer=lo.lo_server_new_with_proto(null,iProto,null);
		//System.out.println("server created: "+getUrl());
	}
	public Server(String port)
	{
		//using given port, no error handler
		loServer=lo.lo_server_new_with_proto(port,iProto,null);
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
		lo.lo_server_free(loServer);
	}
	//handle to method ? return method ?
	public void addMethod(String path,String types,Callback handler)
	{
		lo.lo_server_add_method(loServer, path, types, handler, null);
	}
	public void start()
	{
	}
	public void recv()
	{
		lo.lo_server_recv(loServer);
	}
	public int send(NetAddress na,String path,Message msg)
	{
		return lo.lo_send_message_from(na.getPointer(),loServer,path,msg.getPointer());
	}
}
