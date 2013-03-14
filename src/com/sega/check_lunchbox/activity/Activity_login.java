package com.sega.check_lunchbox.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.sega.check_lunchbox.R;
import com.sega.check_lunchbox.tools.Preferences;

public class Activity_login extends Activity
{
	static final int DATE_DIALOG_ID = 0;
	
	private EditText edt_date;
	private Spinner spn_food, spn_type_food, spn_dinner_room;
	private Button btn_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edt_date = (EditText)findViewById(R.id.edt_date);
		spn_food = (Spinner)findViewById(R.id.spn_food);
		spn_type_food = (Spinner)findViewById(R.id.spn_type_food);
		spn_dinner_room = (Spinner)findViewById(R.id.spn_dinner_room);
		btn_login = (Button)findViewById(R.id.btn_login);
		
		edt_date.setOnTouchListener(new OnTouchListener(){
			@SuppressWarnings("deprecation")
			public boolean onTouch(View v, MotionEvent event){
			if(v == edt_date)
				showDialog(DATE_DIALOG_ID);
			return false;
			}
		});
		
		btn_login.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				_Click_login();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id)
		{
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
						cday);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
	{
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			String date_selected = String.valueOf(dayOfMonth) + " /"
					+ String.valueOf(monthOfYear + 1) + " /" + String.valueOf(year);
			edt_date.setText(date_selected);
		}
	};
	
	private void _Click_login()
	{
		Preferences pref = new Preferences( getApplicationContext() );
		Intent intent = new Intent(this, Activity_check.class);
		
		String date = edt_date.getText().toString();
		String str_dinner_room = spn_dinner_room.getSelectedItem().toString();
		int food = spn_food.getSelectedItemPosition();
		int type_food = spn_type_food.getSelectedItemPosition();
		int dinner_room = spn_dinner_room.getSelectedItemPosition();
		
		Log.v("date", date);
		Log.v("food", Integer.toString(food));
		Log.v("type_food", Integer.toString(type_food));
		Log.v("dinner_room", Integer.toString(dinner_room));
		
		pref.Set_login(date, food, type_food, dinner_room, str_dinner_room);
		
		startActivity(intent);
	}
}
