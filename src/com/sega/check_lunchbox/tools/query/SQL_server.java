package com.sega.check_lunchbox.tools.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.sega.check_lunchbox.tools.Preferences;
import com.sega.check_lunchbox.tools.struc.struc_Params_sql_server;

public class SQL_server
{
	private Connection _conn;
	public String sql;
	public struc_Params_sql_server params;
	
	public Context context;
	
	public SQL_server(Context context, String sql)
	{
		this.context = context;
		this.sql = sql;
		this._Get_preferece_params();
	}
	
	public void Open()
	{
		StrictMode.ThreadPolicy policy =
				new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		String url_connection = _Contruct_string_connect();
		try
		{
			//Class.forName("com.microsoft.jdbc.SQLServerDriver");
			Log.v("SQL_Server.Open.url connecion", ""+ url_connection);
			
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			_conn = DriverManager.getConnection(url_connection,
					params.user, params.pass);
			
			if (_conn != null)
				Log.v("SQL_Server.Open", "Connection Successful!");
			else
				Log.e("SQL_Server.Open", "Connection fail!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("SQL_Server.Open.getConnection", e.getMessage() );
		}
	}
	
	public void Close()
	{
		try
		{
			if (_conn != null)
				_conn.close();
			_conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("SQL_Server.Close", e.getMessage() );
		}
	}
	
	public ResultSet Exec() throws SQLException
	{
		Log.v("SQL server.sql", this.sql);
		ResultSet result = null;
		try
		{
			result = _conn.createStatement().executeQuery(this.sql);
		}
		catch (Exception e)
		{
			String err = e.getMessage();
			Log.e("JDBC", err);
		}
		return result;
	}
	
	private void _Get_preferece_params()
	{
		Preferences prefs = new Preferences(this.context);
		params = prefs.Get_params_sql_server();
	}
	
	private String _Contruct_string_connect()
	{
		String result;
		result = String.format("%s%s:%s;databaseName=%s;", 
				params.url, params.server, params.port, params.database);
		return result;
	}

}