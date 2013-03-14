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
	
	public boolean Check_qr(String qr, String date, int dinner_room) throws SQLException
	{
		ResultSet cursor;
		int result = 0;
		// TODO: asignar el sql
		
		query.Open();
		cursor = query.Exec();
		if ( cursor.next() )
			result = cursor.getInt(0);
		
		Log.v("Check_qr", "result: " + Integer.toString(result) );
		cursor.close();
		query.Close();
		return result != 0;
	}

}
