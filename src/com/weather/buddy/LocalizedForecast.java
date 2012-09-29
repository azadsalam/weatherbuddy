package com.weather.buddy;

import java.util.StringTokenizer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LocalizedForecast extends Activity {

	private double lat;
	private double lon;
	private TextView tv_main;
	private	FetchForecastUpdateAsyncTask fetchForecastUpdateAsyncTask;
	private ProgressDialog pd;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localized_forecast_layout);
        
        
        
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getDouble("lat");
        lon = bundle.getDouble("lon");
        
        //lat /= 1E6;
        //lon /= 1E6;
        
        tv_main = (TextView)findViewById(R.id.tv_localized_forecast_main);
        
        fetchForecastUpdateAsyncTask = new FetchForecastUpdateAsyncTask(this);
        
        tv_main.setText("Forecast");
        getForecast();
        
    }
    
    public void getForecast() 
    { 
 	   fetchForecastUpdateAsyncTask.execute(lat,lon);
    }
    
    private String parseJson(String text) 
    {


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
 	    tv_main.setText(parseJson(result));
 		pd.dismiss();
    }
}
