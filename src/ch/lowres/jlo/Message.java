//tb130425

package ch.lowres.jlo;

import com.sun.jna.Pointer;

public class Message
{
	//interface describing liblo
	static LO lo = LIBLO.getInstance();

	Pointer loMessage;

	public Message()
	{
		loMessage=lo.lo_message_new();	
	}
	public Message(Pointer message)
	{
		loMessage=message;
	}
	public Pointer getPointer()
	{
		return loMessage;
	}
	public Message addString(String s)
	{
		lo.lo_message_add_string(loMessage,s);
		return this;
	}
	public Message addFloat(float f)
	{
		lo.lo_message_add_float (loMessage, f);
		return this;
	}
	//int32
	public Message addInt(int i)
	{
		lo.lo_message_add_int32 (loMessage, i);
		return this;
	}
	//int64
	public Message addLong(long l)
	{
		lo.lo_message_add_int64 (loMessage, l);
		return this;
	}
	public Message addDouble(Double d)
	{
		lo.lo_message_add_double (loMessage, d);
		return this;
	}
	public Message addChar(char c)
	{
		lo.lo_message_add_char (loMessage, c);
		return this;
	}
	public Message addBlob(Blob blob)
	{
		lo.lo_message_add_blob(loMessage, blob.getPointer());
		return this;
	}
	//these methods can not be read correctly from argv (atm)
	//add for send anyway
	public Message addTrue()
	{
		lo.lo_message_add_true (loMessage);
		return this;
	}
	public Message addFalse()
	{
		lo.lo_message_add_false (loMessage);
		return this;
	}
	public Message addNil()
	{
		lo.lo_message_add_nil (loMessage);
		return this;
	}
	public Message addInfinitum()
	{
		lo.lo_message_add_infinitum (loMessage);
		return this;
	}
	public NetAddress getSource()
	{
		return new NetAddress(lo.lo_message_get_source(loMessage));
	}
	public int getArgc()
	{
		return lo.lo_message_get_argc(loMessage);
	}
	public String getTypes()
	{
		return lo.lo_message_get_types(loMessage);
	}
	public int getLength(String path)
	{
		return lo.lo_message_length(loMessage,path);
	}
	private Object readTypeAt(Pointer p,int index)
	{
		char c=getTypes().charAt(index);

		//incomplete!

		if(c==Type.LO_FLOAT)
		{
			return p.getFloat(0);
		}
		else if(c==Type.LO_INT32)
		{
			return p.getInt(0);
		}
		else if(c==Type.LO_INT64)
		{
			return p.getLong(0);
		}
		else if(c==Type.LO_DOUBLE)
		{
			return p.getDouble(0);
		}
		else if(c==Type.LO_STRING)
		{
			return p.getString(0);
		}
		else if(c==Type.LO_CHAR)
		{
			return p.getChar(0);
		}
		else if(c==Type.LO_BLOB)
		{
			return new Blob(p);
		}
		else
		{
			return null;
		}
	}
	public Object getArg(int index)
	{
		if(index<0 || index > getArgc()-1)
		{
			return null;
		}

		Pointer[] argv=lo.lo_message_get_argv(loMessage);
		return readTypeAt(argv[index],index);
	}
	public int getInt(int index)
	{
		return (int)getArg(index);
	}
	public float getFloat(int index)
	{
		return (float)getArg(index);
	}
	public long getLong(int index)
	{
		return (long)getArg(index);
	}
	public double getDouble(int index)
	{
		return (double)getArg(index);
	}
	public String getString(int index)
	{
		return (String)getArg(index);
	}
	public char getChar(int index)
	{
		return (char)getArg(index);
	}
	public Blob getBlob(int index)
	{
		return (Blob)getArg(index);
	}
	public int send(NetAddress na,String path)
	{
		return lo.lo_send_message(na.getPointer(),path,loMessage);
	}
	public void free()
	{
		lo.lo_message_free(loMessage);
	}
} //end class Message
