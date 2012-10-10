package com.weather.buddy;



import java.security.acl.LastOwnerException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WeatherBuddyActivity extends Activity implements OnClickListener{
	
	private static final float MINIMUM_DISTANCE_CHANGE_FOR_UPDATES =(float) 10000.0; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES =60*60*1000; // in Milliseconds - 1 Hour
	protected LocationManager locationManager;
	private MyLocationListener myLocationListener;
	
	/*SET DEFAULT LOCATION AS DHAKA*/
	private static double lat=23.7,lon=90.38;
	
	private static Location location;
	private String providerName;
	
	Button my_forecast_btn,forecast_btn,my_weather_btn,weather_btn,forecast_map_btn;
	Button exit_btn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        startSensingLocation();
        
        
        /*INITIALISING BUTTONS and ASSIGNING ACTION LISTENERS*/
        my_forecast_btn = (Button) findViewById(R.id.btn_myforecast_id);
        forecast_btn = (Button) findViewById(R.id.btn_forecast_id);
        my_weather_btn = (Button) findViewById(R.id.btn_weather_id);
        weather_btn = (Button) findViewById(R.id.btn_friend_wupdate);
        forecast_map_btn = (Button) findViewById(R.id.btn_forecast_map_id);
        //exit_btn = (Button) findViewById(R.id.btn_exit_id);
        
        my_forecast_btn.setOnClickListener(this);
        forecast_btn.setOnClickListener(this);
        my_weather_btn.setOnClickListener(this);
        weather_btn.setOnClickListener(this);
        forecast_map_btn.setOnClickListener(this);
        //exit_btn.setOnClickListener(this);
        
    }
    
    private void startSensingLocation() {
    	locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	providerName = locationManager.GPS_PROVIDER;
    	
    	myLocationListener = new MyLocationListener();
        
        
        locationManager.requestLocationUpdates(
        		providerName,   //service provider
        		MINIMUM_TIME_BETWEEN_UPDATES,   //frequency 
        		MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, //distance interval
        		myLocationListener // location updates.
        );
	}
    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }
    
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	
    	boolean gpsEnabled = locationManager.isProviderEnabled(providerName);
        if (!gpsEnabled) {
            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            // call enableLocationSettings()
        	
        	Toast.makeText(this,"Enable Location Service!", Toast.LENGTH_LONG).show();
        	enableLocationSettings();
        }
        
        
    	
    	location = locationManager.getLastKnownLocation(providerName);
        if(location!=null)
        {
	        lat = location.getLatitude();
	        lon = location.getLongitude();
	        Toast.makeText(this,"We got your last updated position "+lat+" "+lon, Toast.LENGTH_LONG).show();
        }
    	
    }
    private class MyLocationListener implements LocationListener 
	{
    	public MyLocationListener()
    	{
    		//Toast.makeText(getApplicationContext(), "CREATED !!", Toast.LENGTH_LONG).show();
    	}

		public void onLocationChanged(Location l) 
		{
			location = l;
			lon=location.getLongitude();
  			lat=location.getLatitude();
  			
  			Toast.makeText(getApplicationContext(), lat + " " + lon, Toast.LENGTH_LONG).show();
		}

		public void onStatusChanged(String s, int i, Bundle b)
		{
		//	Toast.makeText(Home.this, "Provider status changed",
				//	Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String s)
		{
		//	Toast.makeText(Home.this,
				//	"Provider disabled by the user. GPS turned off",
				//	Toast.LENGTH_LONG).show();
		}

		public void onProviderEnabled(String s) 
		{
			//Toast.makeText(Home.this,
				//	"Provider enabled by the user. GPS turned on",
				//	Toast.LENGTH_LONG).show();
		}
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(),v.getId(), Toast.LENGTH_LONG).show();
		
		if(v.getId() == R.id.btn_myforecast_id) // my forecast button
		{
			Intent intent = new Intent(this, LocalizedForecast.class);
			
			Bundle extra = new Bundle();
			
			extra.putDouble("lat", lat);
			extra.putDouble("lon", lon);
			intent.putExtras(extra);
			

			startActivity(intent);

			//Toast.makeText(getApplicationContext(),"My Forecast", Toast.LENGTH_LONG).show();
		}
		
		else if(v.getId() == R.id.btn_forecast_id) // forecast button
		{
			Intent intent = new Intent(WeatherBuddyActivity.this, ForecastActivity.class);
			
			Bundle extra = new Bundle();
			
			extra.putDouble("lat", lat);
			extra.putDouble("lon", lon);
			intent.putExtras(extra);
			
			//intent.putExtra("lat", lat);
			//intent.putExtra("lon", lon);
			startActivity(intent);
			//Toast.makeText(getApplicationContext(),"Forecast", Toast.LENGTH_LONG).show();
		}
		
		else if(v.getId() == R.id.btn_weather_id) // weather button
		{
			
			Intent intent = new Intent(WeatherBuddyActivity.this, WeatherUpdateActivity.class);
			
			Bundle extra = new Bundle();
			
			extra.putDouble("lat", lat);
			extra.putDouble("lon", lon);
			intent.putExtras(extra);
			
			startActivity(intent);
			//Toast.makeText(getApplicationContext(),"Weather", Toast.LENGTH_LONG).show();
		}
		
		else if(v.getId() == R.id.btn_forecast_map_id)
		{
			
			Intent intent = new Intent(WeatherBuddyActivity.this, ForecastMapActivity.class);
			Bundle extra = new Bundle();
			
			extra.putDouble("lat", lat);
			extra.putDouble("lon", lon);
			intent.putExtras(extra);
			
			startActivity(intent);
		}
		/*
		else if(v.getId() == R.id.btn_exit_id) // exit button
		{
			WeatherBuddyActivity.this.finish();
			//Intent intent = new Intent(WeatherBuddyActivity.this, WeatherActivity.class);
			//startActivity(intent);
			//Toast.makeText(getApplicationContext(),"Weather", Toast.LENGTH_LONG).show();
		}*/
		
		
	}
}