package com.weather.buddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MyForecastActivity extends Activity {

	Button back_btn;
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_forecast_layout);
        
        back_btn  = (Button) findViewById(R.id.btn_back_id);
        back_btn.setOnClickListener(new BackButtonHandler());
    }
}
