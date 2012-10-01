package com.weather.buddy;

import junit.framework.Assert;
import android.test.AndroidTestCase;


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
    	
    	WeatherUpdateActivity instance = new WeatherUpdateActivity();
    	
    	Assert.assertTrue("Rainfall", instance.result == 1);
    }
    
}