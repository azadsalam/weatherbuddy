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
    
    public void testNew() throws Throwable {
        Assert.assertTrue(5 + 5 == 10);
     }
}