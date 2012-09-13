package com.weather.buddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LocalizedForecast extends Activity {

	private double lat;
	private double lon;
	private TextView tv_main;
	

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localized_forecast_layout);
        
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("lat");
        lon = bundle.getDouble("lon");
        
        tv_main = (TextView)findViewById(R.id.tv_localized_forecast_main);
        
        tv_main.setText("Forecast of location : \nLat - "+ (lat/1e6) +" long - " + (lon/1e6));
        
    }
}
