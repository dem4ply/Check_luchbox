package com.sega.check_lunchbox.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.sega.check_lunchbox.tools.query.SQL_server;
import com.sega.check_lunchbox.tools.struc.struc_Entradas;
import com.sega.check_lunchbox.tools.struc.struc_check_qr;

public class Model_Check
{
	SQL_server query;
	
	public Model_Check(Context context)
	{
		query = new SQL_server(context, "");
	}
	
	public struc_check_qr Check_qr(String qr, String date, int dinner_room, int food, int type_food) throws SQLException
	{
		ResultSet cursor;
		struc_check_qr result = new struc_check_qr();
		String sql = "SELECT * FROM ValidarAccesoComedor('%s', %d, '%s', %d, %d)";
		sql = String.format(sql, qr, dinner_room, date, food, type_food);
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		if ( cursor.next() )
		{
			//result = cursor.getString(1);
			result = new struc_check_qr(cursor.getBoolean(1),
					cursor.getString(2), cursor.getString(3), cursor.getString(4),
					cursor.getString(5), cursor.getString(6), cursor.getString(7) );
		}
		
		//Log.v("Check_qr", "result: " + Integer.toString(result) );
		//Log.v("Check_qr", result );
		cursor.close();
		query.Close();
		return result;
	}
	
	public struc_Entradas Get_entradas(int id_comedor, String date, int id_comida) throws SQLException
	{
		ResultSet cursor;
		struc_Entradas result = new struc_Entradas();
		String sql = "SELECT * FROM ConsultarEntradasComedor(%d, '%s', %d)";
		sql = String.format(sql, id_comedor, date, id_comida);
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		if ( cursor.next() )
		{
			//result = cursor.getString(1);
			result = new struc_Entradas(cursor.getInt(1), cursor.getInt(2) );
		}
		
		//Log.v("Check_qr", "result: " + Integer.toString(result) );
		//Log.v("Check_qr", result );
		cursor.close();
		query.Close();
		return result;
	}
	
	public boolean Is_sudo(String qr) throws SQLException
	{
		ResultSet cursor;
		boolean result = false;
		String sql = "Select dbo.ValidarGafeteSupervisor('%s')";
		sql = String.format(sql, qr);
		query.sql = sql;
		
		query.Open();
		cursor = query.Exec();
		Log.v("Check_qr", Integer.toString( cursor.getMetaData().getColumnCount() ) );
		if ( cursor.next() )
		{
			result = cursor.getBoolean(1);
		}
		
		cursor.close();
		query.Close();
		return result;
	}

}
