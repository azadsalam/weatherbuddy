package com.weather.buddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class FetchForecastUpdateAsyncTask extends AsyncTask<Double, String, String> {

	private LocalizedForecast callingActivity;
	
	public FetchForecastUpdateAsyncTask(LocalizedForecast acivity) {
		// TODO Auto-generated constructor stub
		callingActivity = acivity;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		callingActivity.showProcessDialog("Downloading Forecast!");
	}
	
	@Override
	protected String doInBackground(Double... params) {
		
		double latitude = params[0].doubleValue();
		double longtitude = params[1].doubleValue();
		
		// TODO Auto-generated method stub
		String text ="INIT";
	       // Create a new HttpClient and Post Header

       HttpClient httpclient = new DefaultHttpClient();

       HttpPost httppost = new HttpPost("http://10.0.2.2/cpb/index.php/forecastForAndroid");

       JSONObject json = new JSONObject();

       try 
       {

	           // JSON data:

           json.put("lat", latitude);
           json.put("long", longtitude);

           JSONArray postjson=new JSONArray();
           postjson.put(json);


           // Post the data:

           httppost.setHeader("json",json.toString());

           httppost.getParams().setParameter("jsonpost",postjson);

	           // Execute HTTP Post Request

           //System.out.print(json);

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
	           
	           
	           
	           return text;
	          
	          // Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

	   
       }
       
	   catch (Exception e) 
       {
	   		// TODO Auto-generated catch block
	   		
	   		e.printStackTrace();
	   		callingActivity.publishResult("CAN NOT CONNECT TO SERVER");
	   		return "CAN NOT CONNECT TO SERVER ";
	   		
	   	}
	   	
		
	
		
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		Log.d("FORECAST", result);
		callingActivity.publishResult(result);
	}
	
}
