//tb130425

package ch.lowres.jlo;

import com.sun.jna.Pointer;

public class NetAddress
{
	//interface describing liblo
	static LO lo = LIBLO.getInstance();

	Pointer loAddress;

	public NetAddress(Pointer address)
	{
		loAddress=address;
	}

	public NetAddress(String dsthost, String dstport)
	{
		loAddress=lo.lo_address_new(dsthost,dstport);
	}

	public NetAddress(LO.LO_PROTOCOL proto,String dsthost, String dstport)
	{
                loAddress=lo.lo_address_new_with_proto(proto.getVal(),dsthost,dstport);
	}

	public Pointer getPointer()
	{
		return loAddress;
	}

	//====================================

	public String getHostname()
	{
		return lo.lo_address_get_hostname(loAddress);
	}

	public String getPort()
	{
		return lo.lo_address_get_port(loAddress);
	}

	public int getProtocol()
	{
		return lo.lo_address_get_protocol(loAddress);
	}

	public String getUrl()
	{
		return lo.lo_address_get_url(loAddress);
	}

	public void setTTL(int ttl)
	{
		lo.lo_address_set_ttl(loAddress, ttl);
	}

	public int getTTL()
	{
		return lo.lo_address_get_ttl(loAddress);
	}

        public void free()
        {
                lo.lo_address_free(loAddress);
        }

        public String getErrorString() {
               return lo.lo_address_errstr(loAddress);
        }

        public int getLastError() {
            return lo.lo_address_errno(loAddress);
        }
} //end class NetAddress
