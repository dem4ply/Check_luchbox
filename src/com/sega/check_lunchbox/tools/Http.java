package com.sega.check_lunchbox.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Http
{
	static public String Get_Http(String s_url)
	{
		try
		{
			URL url = new URL(s_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			return _Read_stream(con.getInputStream() );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	static public boolean Is_Network_Available(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected() )
		{
			return true;
		}
		return false;
	}
	
	static private String _Read_stream(InputStream in)
	{
		BufferedReader reader = null;
		String result = "";
		try
		{
			reader = new BufferedReader(new InputStreamReader(in) );
			String line = "";
			while ((line = reader.readLine() ) != null)
			{
				result += line;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
