package com.weather.buddy;

import junit.framework.Assert;

public class TestSetup 
{
	public static boolean testEnabled=false;
	
	public static void enableTest()
	{
		testEnabled = true;
	}
	
	public static void initialiseWeatherUpdateTest() 
	{
		Assert.assertTrue(testEnabled);
		WeatherUpdateActivity.initialiseTest();
	}
}
