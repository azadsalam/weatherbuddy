package com.weather.buddy;

import junit.framework.Assert;
import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.util.Log;


public class SomeTest extends AndroidTestCase {

    public void testSomething() throws Throwable 
    {
       Assert.assertTrue(1 + 1 == 2);
    }

    public void testSomethingElse() throws Throwable {
       Assert.assertTrue(1 + 1 == 3);
    }
 
    public void testWeatherUpdate() throws Throwable 
    {
    	//for(int i=0;i<5;i++)
    	TestSetup.enableTest();
    	TestSetup.initialiseWeatherUpdateTest();
    	
    	/*
    	Intent intent = new Intent(getApplicationContext(), WeatherUpdateActivity.class);
		
		Bundle extra = new Bundle();
		
		extra.putDouble("lat", 23);
		extra.putDouble("lon", 90);
		intent.putExtras(extra);
		
		startActivity(intent);
    	    	*/
    	Double rainfall = Double.parseDouble(WeatherUpdateActivity.testMap.get("Rainfall")) ;
    	
    	Log.d("TEST", "Rainfall : "+ rainfall);
    	Assert.assertTrue("Rainfall", rainfall == rainfall);
    }
    
}