package com.sega.check_lunchbox.activity;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sega.check_lunchbox.R;
import com.sega.check_lunchbox.model.Model_Check;
import com.sega.check_lunchbox.tools.Get_Date;
import com.sega.check_lunchbox.tools.Preferences;
import com.sega.check_lunchbox.tools.struc.struc_Param_login;

public class Activity_check extends Activity
{
	private TextView txt_data_dinner_room;
	private Button btn_scan;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);
		txt_data_dinner_room = (TextView)findViewById(R.id.txt_data_dinner_room);
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
		
		_Set_dashboard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
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
	
	private void _Set_dashboard()
	{
		Preferences prefs = new Preferences(this.getApplicationContext());
		struc_Param_login param = prefs.Get_params_login();
		txt_data_dinner_room.setText(param.str_dinner_room);
	}
	
	private class tsk_Send_qr extends AsyncTask<String, Void, Boolean>
	{

		@Override
		protected Boolean doInBackground(String... qrs)
		{
			String qr = qrs[0];
			boolean result = false;
			Model_Check model = new Model_Check( getApplicationContext() );
			try
			{
				result = model.Check_qr(qr, Get_Date.Get_date_now(), 0);
			}
			catch (SQLException e)
			{
				Log.e("Check_qr", e.getMessage() );
			}
			return result;
		}
		
		protected void onPostExecute(Boolean result)
		{
			Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
		}

	}
}