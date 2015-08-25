//tb130425

//http://liblo.sourceforge.net/docs/group__liblolowlevel.html

//needs jna.jar in classpath

package ch.lowres.jlo;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Callback;

import java.nio.ByteBuffer;

//methods that are available in liblo.so
public interface LO extends Library
{
	//============N E W  /  C R E A T E

	//lo_message
	public Pointer lo_message_new();

	//lo_address
	public Pointer lo_address_new(String host,String port);

	//lo_address
	public Pointer lo_address_new_with_proto(int proto, String host, String port);

	//lo_server
	public Pointer lo_server_new(String port, Pointer lo_err_handler);

        public enum LO_PROTOCOL {
            LO_DEFAULT(0x0),
            LO_UDP(0x1),
            LO_UNIX(0x2),
            LO_TCP(0x4);
            private final int val;
            private LO_PROTOCOL(int v) { val = v; }
            public int getVal() { return val; }
        };
	public Pointer lo_server_new_with_proto(String port, int proto, Pointer lo_err_handler);

	//lo_server
	public Pointer lo_server_new_multicast(String group, String port, Pointer lo_err_handler);

	//lo_server_thread
	public Pointer lo_server_thread_new(String port, Pointer p_err);
        public Pointer lo_server_thread_new_with_proto(String port, int proto, Pointer p_err);
	public int lo_server_thread_start(Pointer lo_server_thread);
	public Pointer lo_server_thread_get_server(Pointer p_err);

	//lo_blob, const void* data
	public Pointer lo_blob_new(int size, ByteBuffer buf);

	public int lo_blob_datasize(Pointer lo_blob);
	Pointer lo_blob_dataptr(Pointer lo_blob);

	//============S E R V E R    R E A D

	public int lo_server_get_port(Pointer lo_serer);
	public int lo_server_get_protocol(Pointer lo_serer);
	public String lo_server_get_url(Pointer lo_serer);

	//============M E S S A G E   A D D

	public int lo_message_add_float(Pointer lo_message,float f);
	public int lo_message_add_string(Pointer lo_message, String s);
	public int lo_message_add_int32(Pointer lo_message, int i);
	public int lo_message_add_int64(Pointer lo_message, long l);
	public int lo_message_add_double(Pointer lo_message, double a);
	public int lo_message_add_char(Pointer lo_message, char c);

	//lo_blob
	public int lo_message_add_blob(Pointer lo_message, Pointer lo_blob );

	//public int lo_message_add_symbol(Pointer lo_message, String s);
	//public int lo_message_add_midi(Pointer lo_message, uint8_t a[4]);

	public int lo_message_add_true(Pointer lo_message);
	public int lo_message_add_false(Pointer lo_message);
	public int lo_message_add_nil(Pointer lo_message);
	public int lo_message_add_infinitum(Pointer lo_message);

	//lo_timetag
	public int lo_message_add_timetag(Pointer lo_message, Pointer lo_timetag);

	//============M E S S A G E   R E A D

	//lo_address
	public Pointer lo_message_get_source(Pointer lo_message);
	//public void lo_message_pp(Pointer lo_message);

	public int lo_message_get_argc(Pointer lo_message);
	public String lo_message_get_types(Pointer lo_message);
	public int lo_message_length(Pointer lo_message,String path);

	//lo_arg**
	public Pointer[] lo_message_get_argv(Pointer lo_message);

	/*
	uint32_t lo_timetag::sec
	The number of seconds since Jan 1st 1900 in the UTC timezone.
	uint32_t lo_timetag::frac
	The fractions of a second offset from above, expressed as 1/2^32nds of a second
	*/
	//lo_timetag
	public Pointer lo_message_get_timestamp(Pointer lo_message);


	//============A D D R E S S   R E A D

	public String lo_address_get_hostname(Pointer lo_address);
	public String lo_address_get_port(Pointer lo_address);
	public int lo_address_get_protocol(Pointer lo_address);
	public String lo_address_get_url(Pointer lo_address);

	public void lo_address_set_ttl(Pointer lo_address, int ttl);
	public int lo_address_get_ttl(Pointer lo_address);
	public int lo_address_errno(Pointer lo_address);
	public String lo_address_errstr(Pointer lo_address);

	//============S E N D  /  S E R V E R  /  M E T H O D

	public int lo_send_message(Pointer lo_address, String path, Pointer lo_message);
	public int lo_send_message_from(Pointer lo_address_targ, Pointer lo_server_serv, String path, Pointer lo_message);

	//lo_method
	public Pointer lo_server_add_method(Pointer lo_server, String path, String typespec, Callback my_method_handler, Pointer user_data);

	//lo_method
	public Pointer lo_server_thread_add_method(Pointer lo_server_thread, String path, String typespec, Callback my_method_handler, Pointer user_data);

        // lo_del_method
        public void lo_server_del_method (Pointer lo_server, String path, String typespec);
	public int lo_server_thread_events_pending(Pointer lo_server_thread);

	public int lo_server_recv(Pointer lo_server);
	public int lo_server_recv_noblock(Pointer lo_server, int timeout);

	//============F R E E

	public void lo_message_free(Pointer lo_message);

	public void lo_address_free(Pointer lo_address);

	public void lo_blob_free(Pointer lo_blob);

	public void lo_server_free(Pointer lo_server);

	public void lo_server_thread_free(Pointer p_err);
} //end interface LO
