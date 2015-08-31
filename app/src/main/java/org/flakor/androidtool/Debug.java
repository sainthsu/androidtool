package org.flakor.androidtool;

import android.util.Log;

/**
 * Debug class for development
 */
public class Debug
{
	public static boolean DebugMode = true;
	
	public static void debug(String tag,String info)
	{
		if(DebugMode)
			Log.d(tag, info);
	}
	
	public static void error(String tag,String info)
	{
		if(DebugMode)
			Log.e(tag, info);
	}
	
	public static void info(String tag,String info)
	{
		if(DebugMode)
			Log.i(tag, info);
	}
	
	public static void verbose(String tag,String info)
	{
		if(DebugMode)
			Log.v(tag, info);
	}
}
