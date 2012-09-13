package com.weather.buddy;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class BackButtonHandler implements OnClickListener
{
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(v.getContext(), WeatherBuddyActivity.class);
		v.getContext().startActivity(intent);
	
	}
	
}
