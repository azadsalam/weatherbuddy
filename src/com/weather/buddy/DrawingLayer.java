package com.weather.buddy;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class DrawingLayer extends Overlay {
	
	
	Double lat,lon;
	ForecastActivity ma;
	public DrawingLayer(Double lat,Double lon,ForecastActivity ma)
	{	
		this.lat= lat;
		this.lon = lon;
		this.ma = ma;
	}
	public void draw(Canvas canvas,MapView mpv,boolean shadow ){
		Projection projection=mpv.getProjection();

		GeoPoint gp=new GeoPoint((int)(lat*1E6),(int)(lon*1E6));
		if(!shadow){
			
				Point myPoint = new Point();
				projection.toPixels(gp, myPoint);
				// Create and setup your paint brush
				Paint paint = new Paint();
				
				paint.setARGB(250, 20,20, 0);
				paint.setAntiAlias(true);
				paint.setFakeBoldText(true);
				// Create the circle
				int rad = 8;
				
				RectF oval = new RectF(myPoint.x-rad, myPoint.y-rad,myPoint.x+rad, myPoint.y+rad);
				// Draw on the canvas
				//canvas.drawBitmap(, matrix, paint)
				
				canvas.drawOval(oval, paint);
				
				canvas.drawText("You are here!!", myPoint.x+rad+2, myPoint.y, paint);
			
		}
		else{
			
		}
	}
	
	public boolean onTap(GeoPoint gp,MapView mv){
		
		Intent intent = new Intent ();
		//mc.setCenter(gp);
		ma.tapHandler(gp);
			return true;
		}
}
