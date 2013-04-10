package com.sega.check_lunchbox.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.sega.check_lunchbox.tools.query.SQL_server;
import com.sega.check_lunchbox.tools.struc.struc_Login;
import com.sega.check_lunchbox.tools.struc.struc_Login;

public class Model_Login
{
	SQL_server query;
	
	public Model_Login(Context context)
	{
		query = new SQL_server(context, "");
	}
	
	public Vector<struc_Login> Get_login() throws SQLException
	{
		ResultSet cursor;
		Vector<struc_Login> result = new Vector<struc_Login>();
		String sql = "SELECT * FROM Comedores";
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		while ( cursor.next() )
		{
			//result = cursor.getString(1);
			result.add( new struc_Login(cursor.getInt(1), cursor.getString(2),
					cursor.getBoolean(3), cursor.getBoolean(4),
					cursor.getBoolean(5), cursor.getBoolean(6)) );
		}
		cursor.close();
		query.Close();
		return result;
	}
	
	public struc_Login Get_comedor(int id) throws SQLException
	{
		ResultSet cursor;
		struc_Login result = new struc_Login();
		String sql = "SELECT * FROM Comedores WHERE idComedor = " + Integer.toString(id);
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		if ( cursor.next() )
		{
			//result = cursor.getString(1);
			result = new struc_Login(cursor.getInt(1), cursor.getString(2),
					cursor.getBoolean(3), cursor.getBoolean(4),
					cursor.getBoolean(5), cursor.getBoolean(6)) ;
		}
		return result;
	}

}
