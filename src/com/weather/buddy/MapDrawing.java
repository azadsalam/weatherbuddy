package com.weather.buddy;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapDrawing extends Overlay {
	
	
	Double lat,lon;
	ForecastActivity ma;
	public MapDrawing(Double lat,Double lon,ForecastActivity ma)
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
				
				/*
				Path path = new Path();

				for (int j = 0; j < 4; j++) {

				 if (j == 0)
				  path.moveTo(myPoint.x, myPoint.y);  
				 else if(j == 1)
				  path.lineTo(myPoint.x+100, myPoint.y+100);
				 else if(j == 2)
					  path.lineTo(myPoint.x, myPoint.y+100);
				 else if(j == 3)
					  path.lineTo(myPoint.x, myPoint.y);

				}
				
				
				Paint mPaint = new Paint();
				mPaint.setStrokeWidth(2);  //2 pixel line width
				mPaint.setColor(0xAA097286); //tealish with no transparency
				mPaint.setStyle(Paint.Style.FILL_AND_STROKE); //stroked, aka a line with no fill
				mPaint.setAntiAlias(true); 
				
				canvas.drawPath(path,mPaint);
				*/
				// = new 
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
