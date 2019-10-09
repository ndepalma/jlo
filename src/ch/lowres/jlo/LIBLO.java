//tb130425

package ch.lowres.jlo;

import com.sun.jna.Native;

public class LIBLO
{
	//name of library (without lib prefix, .so extension). liblo.so -> lo
	private static final String sLibraryName="lo";

	//interface describing liblo
	private static LO lo = (LO) Native.loadLibrary(sLibraryName, LO.class);

	public static LO getInstance()
	{
		return lo;
	}
}
