package com.sega.check_lunchbox.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.sega.check_lunchbox.tools.query.SQL_server;

public class Model_Check
{
	SQL_server query;
	
	public Model_Check(Context context)
	{
		query = new SQL_server(context, "");
	}
	
	public boolean Check_qr(String qr, String date, int dinner_room, int food, int type_food) throws SQLException
	{
		ResultSet cursor;
		String result = "";
		String sql = "SELECT * FROM ValidarAccesoComedor('%s', %d, '%s', %d, %d)";
		sql = String.format(sql, qr, dinner_room, date, food, type_food);
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		if ( cursor.next() )
		{
			result = cursor.getString(2);
		}
		
		//Log.v("Check_qr", "result: " + Integer.toString(result) );
		Log.v("Check_qr", result );
		cursor.close();
		query.Close();
		return result != "";
	}

}
