package com.sega.check_lunchbox.tools;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class Notifications
{
	static public void Play_sound(Context context)
	{
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		android.media.Ringtone r = RingtoneManager.getRingtone(context, notification);
		r.play();
		Log.v("dem.tools.Notification", "Play_sound");
	}
}
