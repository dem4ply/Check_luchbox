package com.sega.check_lunchbox.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.sega.check_lunchbox.R;

public class Activity_check extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}