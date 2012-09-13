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
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {

	//TextView tvTest;
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
       
       try {
    	   getWeatherUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		tv_weather_text.setText(e.toString());
		e.printStackTrace();
		Toast.makeText(this,"CAN NOT CONNECT TO SERVER "+e.toString(),Toast.LENGTH_LONG).show();
	}
       
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
   public void getWeatherUpdate() throws JSONException{ 

		
	   String text ="INIT";
       // Create a new HttpClient and Post Header

       HttpClient httpclient = new DefaultHttpClient();

       HttpPost httppost = new HttpPost("http://10.0.2.2/agro/index.php/android");

       JSONObject json = new JSONObject();

       try {

           // JSON data:

           json.put("lat", latitude);
           json.put("long", longtitude);

           //json.put("position", "sysdev");



           JSONArray postjson=new JSONArray();

           postjson.put(json);


           // Post the data:

           httppost.setHeader("json",json.toString());

           httppost.getParams().setParameter("jsonpost",postjson);

           // Execute HTTP Post Request

           System.out.print(json);

           HttpResponse response = httpclient.execute(httppost);

           // for JSON:

           if(response != null)
           {

               InputStream is = response.getEntity().getContent();
               BufferedReader reader = new BufferedReader(new InputStreamReader(is));
               StringBuilder sb = new StringBuilder();
               String line = null;
               
               try 
               {
                   while ((line = reader.readLine()) != null) 
                   {
                       sb.append(line + "\n");
                   }

               } 
               catch (IOException e) 
               {
                   e.printStackTrace();
               } 
               finally {
                   try {
                       is.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               text = sb.toString();
           }
           
           
           
           showWeatherUpdate(parseJson(text));
          
          // Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

       }catch (ClientProtocolException e) {

           // TODO Auto-generated catch block

       } catch (IOException e) {

           // TODO Auto-generated catch block

       }

   }
   
   private String parseJson(String text) {
	  
	   
	   //wdate,rainfall,mintemp,maxtemp,humidity
	   //text.indexOf("wdate")
	   //text.indexOf("wdate", start)
	   
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
   	
   private void showWeatherUpdate(String str) {
	   tv_weather_text.setText(str);
   }
   
}

