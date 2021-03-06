package com.weather.buddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import junit.framework.Assert;

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
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherUpdateActivity extends Activity {

	public static HashMap<String,String> testMap ;
	
	public int result ;
	
	//TextView tvTest;
	FetchWeatherUpdateAsyncTask fetchWeatherUpdateAsyncTask;
	ProgressDialog pd;
	Button back_btn;
	Double latitude=90.41,longtitude=23.7;
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
       
       if(TestSetup.testEnabled)
       {
    	   latitude = 23.77;
           longtitude = 90.41;
         
       }
       Log.d("TEST","From test : LAT LONG SET FROM TEST");
    //   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    //   StrictMode.setThreadPolicy(policy); 
       
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
   
   
   private String parseJson(String text) 
   {
	   /*
	                 $tosend['Date']=$row->wdate;
             $tosend['Rainfall']=$row->rainfall;
             $tosend['Minimimum Temp']=$row->mintemp;
             $tosend['Maximum Temp']=$row->maxtemp;
             $tosend['Humidity']=$row->humidity; */
	 String ret="";
	 try 
	 {
		JSONObject jsonObject = new JSONObject(text);
		
		String date = jsonObject.getString("date"); 
		String rainfall = jsonObject.getString("rainfall");
		String minTemp = jsonObject.getString("mintemp");
		String maxTemp = jsonObject.getString("maxtemp");
		String humidity = jsonObject.getString("humidity");
		
		
		if(!date.isEmpty()) ret += ("Date : "+date+"\n");
		if(!rainfall.isEmpty())ret += ("Rainfall : "+rainfall+"\n");
		if(!minTemp.isEmpty())ret += ("Minimum Temperature : "+minTemp+"\n");
		if(!maxTemp.isEmpty())ret += ("Maximum Temperature : "+maxTemp+"\n");
		if(!humidity.isEmpty())ret += ("Humidity : "+humidity+"\n");
		
		//ret += ("Minimum Temperature : "+minTemp+"\n");
		return ret;
	 } 
	 catch (JSONException e) {
		 
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "No Data Found";
	 }
	 
   }
   private String parseJsonExtra(String text) 
   {

	   
	   Log.d("TEST","IN PARSE JSON");
	   
	   StringTokenizer stringTokenizer = new StringTokenizer(text,"\"{},:");
	   
	   String key="",value="",str="\n";
	   while(stringTokenizer.hasMoreTokens())
	   {
		   key = stringTokenizer.nextToken();
		   if(stringTokenizer.hasMoreTokens())
		   {
		      value = stringTokenizer.nextToken();
		   
		      if(value == null || value.isEmpty() || value.equals("null")) 
		    	  value = new String("Not Found");
		      
		      //Log.d("value", value);
		      str = str.concat("\n"+ key + " : " + value);
		      
		      if(TestSetup.testEnabled)
		      {
		    	  Log.d("TEST"," PUT : KEY :"+key + " -> "+value);
		    	  if(key.endsWith("Date"))
		    		  testMap.put(key, value);
		    	  else
		    		  testMap.put(key, value);
		      }
		   }
	   }
	   return str;
	   
   }
   	
 
   public static void initialiseTest() 
   {
	   Assert.assertTrue(TestSetup.testEnabled);
	   
	   testMap = new HashMap<String, String>();
	   
	  
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

