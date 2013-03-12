package com.sega.check_lunchbox.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sega.check_lunchbox.tools.struc.struc_Params_sql_server;

public class Preferences
{
	public SharedPreferences prefs_SQL;
	public Context context;
	
	public Preferences(Context context)
	{
		this.context = context;
		 prefs_SQL = context.getSharedPreferences("SQLServer", Context.MODE_PRIVATE);
	}
	
	public void Set_default_preferences()
	{
		Editor edit = prefs_SQL.edit();
		
		edit.putString("url", "jdbc:jtds:sqlserver://");
		edit.putString("server", "72.167.245.123");
		edit.putString("port", "1500");
		edit.putString("database", "BDUniversiad");
		edit.putString("user", "UNIVERSIADA2013");
		edit.putString("pass", "UAS2013");
		edit.putString("selectMethod", "Direct");
		
		edit.commit();
	}
	
	public struc_Params_sql_server Get_params_sql_server()
	{
		struc_Params_sql_server result = new struc_Params_sql_server();
		result.url = prefs_SQL.getString("url", "jdbc:jtds:sqlserver://");
		result.server = prefs_SQL.getString("server", "72.167.245.123");
		result.port = prefs_SQL.getString("port", "1500");
		result.database = prefs_SQL.getString("database", "BDUniversiad");
		result.user = prefs_SQL.getString("user", "UNIVERSIADA2013");
		result.pass = prefs_SQL.getString("pass", "UAS2013");
		result.select_method = prefs_SQL.getString("select_method", "Direct");
		return result;
	}
	
	
}
