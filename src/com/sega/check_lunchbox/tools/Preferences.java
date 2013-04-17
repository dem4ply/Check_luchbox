package com.sega.check_lunchbox.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sega.check_lunchbox.tools.struc.struc_Param_login;
import com.sega.check_lunchbox.tools.struc.struc_Params_sql_server;

public class Preferences
{
	public SharedPreferences prefs_SQL, prefs_login;
	public Context context;
	
	public Preferences(Context context)
	{
		this.context = context;
		prefs_SQL = context.getSharedPreferences("SQLServer", Context.MODE_PRIVATE);
		prefs_login = context.getSharedPreferences("login", Context.MODE_PRIVATE);
	}
	
	public void Set_default_preferences()
	{
		Editor edit = prefs_SQL.edit();
		
		edit.putString("url", "jdbc:jtds:sqlserver://");
		edit.putString("server", "148.227.75.21");
		edit.putString("port", "1500");
		edit.putString("database", "BDUniversiada");
		edit.putString("user", "UNIVERSIADA2013");
		edit.putString("pass", "UAS2013");
		edit.putString("selectMethod", "Direct");
		
		edit.commit();
	}
	
	public void Set_login(String date, int food, int type_food, int dinner_room, String str_dinner_room, String str_food)
	{
		Editor edit = prefs_login.edit();
		
		edit.putString("date", date);
		edit.putString("str_dinner_room", str_dinner_room);
		edit.putString("str_food", str_food);
		edit.putInt("food", food);
		edit.putInt("type_food", type_food);
		edit.putInt("dinner_room", dinner_room);
		
		edit.commit();
	}
	
	public struc_Param_login Get_params_login()
	{
		String date = prefs_login.getString("date", "");
		String str_dinner_room = prefs_login.getString("str_dinner_room", "");
		String str_food = prefs_login.getString("str_food", "");
		int food = prefs_login.getInt("food", 0);
		int type_food = prefs_login.getInt("type_food", 0);
		int dinner_room = prefs_login.getInt("dinner_room", 0);
		
		return new struc_Param_login(date, food, type_food, dinner_room, str_dinner_room, str_food);
	}
	
	public struc_Params_sql_server Get_params_sql_server()
	{
		struc_Params_sql_server result = new struc_Params_sql_server();
		result.url = prefs_SQL.getString("url", "jdbc:jtds:sqlserver://");
		result.server = prefs_SQL.getString("server", "148.227.75.21");
		result.port = prefs_SQL.getString("port", "1500");
		result.database = prefs_SQL.getString("database", "BDUniversiada");
		result.user = prefs_SQL.getString("user", "UNIVERSIADA2013");
		result.pass = prefs_SQL.getString("pass", "UAS2013");
		result.select_method = prefs_SQL.getString("select_method", "Direct");
		return result;
	}
	
	
}
