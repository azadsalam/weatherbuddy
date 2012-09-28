package com.weather.buddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherUpdateActivity extends Activity {

	//TextView tvTest;
	FetchWeatherUpdateAsyncTask fetchWeatherUpdateAsyncTask;
	ProgressDialog pd;
	Button back_btn;
	Double latitude=90.0,longtitude=24.0;
	TextView tv_weather_text;
	Bundle bundle;
	 /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.weather_layout);
       
       bundle = getIntent().getExtras();
       latitude = bundle.getDouble("lat");
       longtitude = bundle.getDouble("lon");
       
       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy); 
       
       back_btn  = (Button) findViewById(R.id.btn_back_id);
       back_btn.setOnClickListener(new BackButtonHandler());
       
       //tvTest = (TextView) findViewById(R.id.tv_test_id);
       
       //tvTest.setText("LAT LNG");
       
       
       tv_weather_text = (TextView) findViewById(R.id.tv_weather_text_id);
       
       fetchWeatherUpdateAsyncTask = new FetchWeatherUpdateAsyncTask(this);
       
       getWeatherUpdate();
	
       
   }
   /*
   private void updateWithNewLocation(Location location) {
	   String latLongString;
	
	   if (location != null) 
	   {
		   double lat = location.getLatitude();
		   double lng = location.getLongitude();
		   latLongString = "Lat:" + lat + "\nLong:" + lng;
	   } 
	   else 
	   {
		  latLongString = "No location found";
	   }
	   	   //tvTest.setText("Your Current Position is:\n" + latLongString);
	  }
   */
   public void getWeatherUpdate() 
   { 
	   fetchWeatherUpdateAsyncTask.execute(latitude,longtitude);
   }
   
   private String parseJson(String text) {


	   StringTokenizer stringTokenizer = new StringTokenizer(text,"\"{},:");
	   
	   String key="",value="",str="\n";
	   while(stringTokenizer.hasMoreTokens())
	   {
		   key = stringTokenizer.nextToken();
		   if(stringTokenizer.hasMoreTokens())
		   {
		      value = stringTokenizer.nextToken();
		   
		      str = str.concat("\n"+ key + " : " + value);
		   }
	   }
	   return str;
	   
   }
   	
 
   
   public void showProcessDialog(String msg) 
   {
   	 pd = ProgressDialog.show(this, "", msg);
   }
   
   public void publishResult(String result) 
   {
	    tv_weather_text.setText(parseJson(result));
		pd.dismiss();
   }
}

