package com.sega.check_lunchbox.activity;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sega.check_lunchbox.R;
import com.sega.check_lunchbox.model.Model_Check;
import com.sega.check_lunchbox.model.Model_Login;
import com.sega.check_lunchbox.tools.Notifications;
import com.sega.check_lunchbox.tools.Preferences;
import com.sega.check_lunchbox.tools.struc.struc_Entradas;
import com.sega.check_lunchbox.tools.struc.struc_Login;
import com.sega.check_lunchbox.tools.struc.struc_Param_login;
import com.sega.check_lunchbox.tools.struc.struc_check_qr;

public class Activity_check extends Activity
{
	private String str_date;
	private int id_comida, id_comedor;
	
	private ImageView img_ok, img_error;
	private TextView txt_siglas, txt_univercidad, txt_diciplina, txt_nombre;
	private TextView txt_data_dinner_room;
	private TextView chk_sportman, chk_comitiva, chk_judge, chk_staff;
	private TextView txt_data_count, txt_data_luchbox;
	private Button btn_scan;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);
		txt_data_dinner_room = (TextView)findViewById(R.id.txt_data_dinner_room);
		txt_siglas = (TextView)findViewById(R.id.txt_siglas);
		txt_univercidad = (TextView)findViewById(R.id.txt_univercidad);
		txt_diciplina = (TextView)findViewById(R.id.txt_diciplina);
		txt_nombre = (TextView)findViewById(R.id.txt_nombre);
		
		txt_data_count = (TextView)findViewById(R.id.txt_data_count);
		txt_data_luchbox = (TextView)findViewById(R.id.txt_data_boxlunch);
		
		chk_sportman = (TextView)findViewById(R.id.chk_sportsman);
		chk_comitiva = (TextView)findViewById(R.id.chk_comitiva);
		chk_judge = (TextView)findViewById(R.id.chk_judge);
		chk_staff = (TextView)findViewById(R.id.chk_staff);
		
		img_ok = (ImageView)findViewById(R.id.img_ok);
		img_error = (ImageView)findViewById(R.id.img_error);
		
		btn_scan = (Button)findViewById(R.id.btn_scan);
		btn_scan.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
			}
		});
		Preferences pref = new Preferences( getApplicationContext() );
		str_date = pref.Get_params_login().date;
		id_comida = pref.Get_params_login().type_food;
		id_comedor = pref.Get_params_login().dinner_room;
		_Set_dashboard_init();
		new Timer().schedule(new timer_Get_entrada(), 0, 10000);
		new tsk_Get_comedor().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		//new tsk_Get_Entradas().execute();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		String contents = "vacio";
		if (requestCode == 0)
		{
			if (resultCode == RESULT_OK)
				contents = intent.getStringExtra("SCAN_RESULT");
			else if (resultCode == RESULT_CANCELED)
				contents = "cancelacion";
			
			Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
			new tsk_Send_qr().execute(contents);
		}
	}
	
	private void _Set_dashboard_init()
	{
		Preferences prefs = new Preferences(this.getApplicationContext());
		struc_Param_login param = prefs.Get_params_login();
		txt_data_dinner_room.setText(param.str_dinner_room);
		
		_Set_dashboard_type(false, true, false, true);
	}
	
	private void _Set_dashboard_type(boolean sportman, boolean comitiva, boolean judge, boolean staff)
	{
		if (sportman)
			chk_sportman.setBackgroundResource(R.color.dashboard_ok);
		else
			chk_sportman.setBackgroundResource(R.color.dashboard_no);
		if (comitiva)
			chk_comitiva.setBackgroundResource(R.color.dashboard_ok);
		else
			chk_comitiva.setBackgroundResource(R.color.dashboard_no);
		if (judge)
			chk_judge.setBackgroundResource(R.color.dashboard_ok);
		else
			chk_judge.setBackgroundResource(R.color.dashboard_no);
		if (staff)
			chk_staff.setBackgroundResource(R.color.dashboard_ok);
		else
			chk_staff.setBackgroundResource(R.color.dashboard_no);
	}
	
	private class tsk_Send_qr extends AsyncTask<String, Void, struc_check_qr>
	{
		@Override
		protected struc_check_qr doInBackground(String... qrs)
		{
			//Toast.makeText(getApplicationContext(), "qr-init", Toast.LENGTH_SHORT).show();
			String qr = qrs[0];
			struc_check_qr result = new struc_check_qr();
			Model_Check model = new Model_Check( getApplicationContext() );
			try
			{
				//result = model.Check_qr(qr, Get_Date.Get_date_now(), 1, 1, 1);
				result = model.Check_qr(qr, str_date, 1, 1, 1);
			}
			catch (SQLException e)
			{
				Log.e("Check_qr", e.getMessage() );
			}
			return result;
		}
		
		protected void onPostExecute(struc_check_qr result)
		{
			//Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), "qr-end", Toast.LENGTH_SHORT).show();
			txt_siglas.setText(result.siglas);
			txt_univercidad.setText(result.universidad);
			txt_diciplina.setText(result.diciplina);
			txt_nombre.setText(result.nombre);
			if (result.result)
			{
				new tsk_Play_sound().execute(1L);
				img_error.setVisibility(View.GONE);
				img_ok.setVisibility(View.VISIBLE);
				
				//new tsk_Click_scan().execute(300L);
			}
			else
			{
				new tsk_Play_sound().execute(4L);
				img_error.setVisibility(View.VISIBLE);
				img_ok.setVisibility(View.GONE);
			}
		}
	}
	
	private class tsk_Get_Entradas extends AsyncTask<Void, Void, struc_Entradas>
	{
		@Override
		protected struc_Entradas doInBackground(Void ...params)
		{
			//Toast.makeText(getApplicationContext(), "entradas-init", Toast.LENGTH_SHORT).show();
			struc_Entradas result = new struc_Entradas();
			Model_Check model = new Model_Check( getApplicationContext() );
			try
			{
				//result = model.Check_qr(qr, Get_Date.Get_date_now(), 1, 1, 1);
				result = model.Get_entradas(id_comedor, str_date, id_comida);
			}
			catch (SQLException e)
			{
				Log.e("Check_qr", e.getMessage() );
			}
			/*
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				Log.e("tsk_play_sound", e.getMessage() );
			}
			new tsk_Get_Entradas().execute();
			*/
			return result;
		}
		
		protected void onPostExecute(struc_Entradas result)
		{
			//Toast.makeText(getApplicationContext(), "entradas-end", Toast.LENGTH_SHORT).show();
			txt_data_count.setText(Integer.toString( result.normal ) );
			txt_data_luchbox.setText(Integer.toString( result.boxlunch ) );
		}
	}
	
	private class tsk_Play_sound extends AsyncTask<Long, Void, Void>
	{
		@Override
		protected Void doInBackground(Long... sounds)
		{
			//Toast.makeText(getApplicationContext(), "sonidos", Toast.LENGTH_SHORT).show();
			Long count = sounds[0];
			for (int i = 0; i < count; ++i)
			{
				Notifications.Play_sound(getApplicationContext() );
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					Log.e("tsk_play_sound", e.getMessage() );
				}
			}
			//new tsk_Get_Entradas().execute();
			return null;
		}

	}
	
	private class tsk_Click_scan extends AsyncTask<Long, Void, Void>
	{
		@Override
		protected Void doInBackground(Long... times)
		{
			try
			{
				Thread.sleep(times[0]);
				Toast.makeText(getApplicationContext(), "time end", Toast.LENGTH_SHORT).show();
				btn_scan.performClick();
			}
			catch (InterruptedException e)
			{
				Log.e("tsk_play_sound", e.getMessage() );
			}
			return null;
		}

	}

	private class timer_Get_entrada extends TimerTask
	{
		@Override
		public void run()
		{
			struc_Entradas result = new struc_Entradas();
			Model_Check model = new Model_Check( getApplicationContext() );
			try
			{
				//result = model.Check_qr(qr, Get_Date.Get_date_now(), 1, 1, 1);
				result = model.Get_entradas(id_comedor, str_date, id_comida);
			}
			catch (SQLException e)
			{
				Log.e("Check_qr", e.getMessage() );
			}
			final int normal = result.normal;
			final int boxlunch = result.normal;
			txt_data_count.post(new Runnable()
			{
				@Override
				public void run()
				{
					txt_data_count.setText(Integer.toString( normal ) );
				}
			});
			txt_data_luchbox.post(new Runnable()
			{
				@Override
				public void run()
				{
					txt_data_luchbox.setText(Integer.toString( boxlunch ) );
				}
			});
			
		}
	}
	
	private class tsk_Get_comedor extends AsyncTask<Void, Void, struc_Login>
	{
		@Override
		protected struc_Login doInBackground(Void ... params)
		{
			//Toast.makeText(getApplicationContext(), "qr-init", Toast.LENGTH_SHORT).show();
			struc_Login result = new struc_Login();
			Model_Login model = new Model_Login( getApplicationContext() );
			try
			{
				//result = model.Check_qr(qr, Get_Date.Get_date_now(), 1, 1, 1);
				result = model.Get_comedor(id_comedor);
			}
			catch (SQLException e)
			{
				Log.e("Check_qr", e.getMessage() );
			}
			return result;
		}
		
		protected void onPostExecute(struc_Login result)
		{
			_Set_dashboard_type(result.sportman, result.comitiva, result.judge, result.staff);
		}
	}
}