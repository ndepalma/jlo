//tb130425

package ch.lowres.jlo.test;

import ch.lowres.jlo.NetAddress;

public class NetAddressTest
{
	public static void main(String[] args)
	{
		NetAddress na=new NetAddress("localhost","7777");

		System.out.println("hostname: "+na.getHostname());
		System.out.println("port: "+na.getPort());
		System.out.println("protocol: "+na.getProtocol());
		System.out.println("url: "+na.getUrl());
	}
}
