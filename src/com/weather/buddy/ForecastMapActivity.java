package com.weather.buddy;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

public class ForecastMapActivity extends MapActivity {

	Button back_btn;
	Double lat,lon;
	Bundle bundle;
	MapView mapView;
	MapController mc;
	GeoPoint p;
	int zoom=7;
	private	MapForecastAsyncTask fetchForecastUpdateAsyncTask;
	private ProgressDialog pd;
	private HashMap<String,Double> map = new HashMap<String,Double>();
	
	 /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.forecast_layout);
       
       mapView = (MapView) findViewById(R.id.map_view_id);
       mapView.setBuiltInZoomControls(true);

       bundle = getIntent().getExtras();
       lat = bundle.getDouble("lat");
       lon = bundle.getDouble("lon");
       
       back_btn  = (Button) findViewById(R.id.btn_back_id);
       back_btn.setOnClickListener(new BackButtonHandler());
       
       Toast.makeText(getApplicationContext(), "You are in Lat. : "+lat +" Long." + lon, Toast.LENGTH_LONG).show();
       
       
       fetchForecastUpdateAsyncTask = new MapForecastAsyncTask(this);
       getForecast();
       
       
       mapView = (MapView) findViewById(R.id.map_view_id);
       
       mapView.setBuiltInZoomControls(true);
       mapView.displayZoomControls(true);
       
       mc = mapView.getController();
       
       //lat =new Double(90);
       //lon=new Double(24);
       
       p = new GeoPoint(
       (int) (lat * 1E6),
       (int) (lon * 1E6));
      // mc.animateTo(p);
       
       mc.setCenter(p);
       mc.setZoom(zoom);
          
       List<Overlay> overlays = mapView.getOverlays();
       MapDrawingLayer drawingLayer = new MapDrawingLayer(lat,lon,this);
       overlays.add(drawingLayer);
       
       mapView.postInvalidate();
       
   }
   
   public void getForecast() 
   { 
	   fetchForecastUpdateAsyncTask.execute(lat,lon);
   }
   
   
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP)
		{
			zoom++;
			mc.setZoom(zoom);
			 mapView.displayZoomControls(true);
			return(true);
		}
		else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			zoom--;
			mc.setZoom(zoom);
			 mapView.displayZoomControls(true);
			return(true);
		}
		
		return (super.onKeyDown(keyCode, event));
		
		}
	
	
	   private void parseJson(String text) 
	   {
		 String ret="";
		 try 
		 {
			JSONObject jsonObject = new JSONObject(text);
			
			map.put("dhaka", new Double(jsonObject.getString("dhaka")));
			map.put("chittagong", new Double(jsonObject.getString("chittagong")));
			map.put("rajshahi", new Double(jsonObject.getString("rajshahi")));
			map.put("khulna", new Double(jsonObject.getString("khulna")));
			map.put("barishal", new Double(jsonObject.getString("barishal")));
			map.put("sylhet", new Double(jsonObject.getString("sylhet")));
			map.put("rangpur", new Double(jsonObject.getString("rangpur")));
			
		 } 
		 catch (JSONException e) 
		 {
			 
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			map.put("dhaka", new Double(10));
			map.put("chittagong", new Double(20));
			map.put("rajshahi", new Double(30));
			map.put("khulna", new Double(40));
			map.put("barishal", new Double(50));
			map.put("sylhet", new Double(60));
			map.put("rangpur", new Double(70));

			
		 }
		 
	   }

	
	
	public void showProcessDialog(String msg) 
    {
    	 pd = ProgressDialog.show(this, "", msg);
    }
    
	
    public void publishResult(String result) 
    {
 	   // tv_main.setText(parseJson(result));
    	
    	parseJson(result);
    	Log.d("MAPP", result);
 		pd.dismiss();
    }
	
	
}
