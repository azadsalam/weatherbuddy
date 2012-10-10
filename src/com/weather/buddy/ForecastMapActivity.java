package com.weather.buddy;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;



import android.content.Intent;
import android.os.Bundle;
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
	int zoom=6;
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
       
       mapView = (MapView) findViewById(R.id.map_view_id);
       
       mapView.setBuiltInZoomControls(true);
       mapView.displayZoomControls(true);
       
       mc = mapView.getController();
       
       
       p = new GeoPoint(
       (int) (lat * 1E6),
       (int) (lon * 1E6));
      // mc.animateTo(p);
       
       mc.setCenter(p);
       mc.setZoom(zoom);
       
       
       List<Overlay> overlays = mapView.getOverlays();
       //DrawingLayer drawingLayer = new DrawingLayer(lat,lon,this);
       //overlays.add(drawingLayer);
       
       mapView.postInvalidate();
       
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
	
	
	
	
	
}
