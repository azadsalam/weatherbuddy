package com.weather.buddy;

import java.util.LinkedList;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapDrawingLayer extends Overlay {
	
	Double lat,lon;
	ForecastMapActivity ma;
	public MapDrawingLayer(Double lat,Double lon,ForecastMapActivity ma)
	{	
		this.lat= lat;
		this.lon = lon;
		this.ma = ma;
	}
	
	public void populate(String division,LinkedList <Double> lat, LinkedList <Double> lon)
	{
		if(division.equals("dhaka"))
		{
			lat.add(new Double(24));
			lon.add(new Double(90));
			
			lat.add(new Double(24));
			lon.add(new Double(91));
			
			lat.add(new Double(25));
			lon.add(new Double(92));
			
			lat.add(new Double(23));
			lon.add(new Double(91));	
		}
		
		else if(division.equals("chittagong"))
		{
			lat.add(new Double(25));
			lon.add(new Double(91));
			
			lat.add(new Double(25));
			lon.add(new Double(88));
			
			lat.add(new Double(26));
			lon.add(new Double(88));
			
			lat.add(new Double(24));
			lon.add(new Double(89));	
		}
		
	}
	
	public Paint getPaint(String division)
	{
		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(5);  //2 pixel line width
		if(division.equals("dhaka"))
		{
			mPaint.setColor(0xAA097286); //tealish with transparency
		}
		else if (division.equals("chittagong"))
		{
			mPaint.setColor(0xAA720926); //tealish with transparency
		}
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE); //stroked, aka a line with no fill
		mPaint.setAntiAlias(true); 
		return mPaint;
	}
	public Path givePath(Projection projection,LinkedList <Double> lat, LinkedList <Double> lon)
	{
		Path path = new Path();
		int latitude,longitude,x=0,y=0;
		//  path.moveTo(myPoint.x, myPoint.y);  
			//  path.lineTo(myPoint.x, myPoint.y);
		
		for(int i=0; i<lat.size(); i++)
		{
			latitude = (int)(lat.get(i).doubleValue() * 1E6);
			longitude = (int)(lon.get(i).doubleValue()* 1E6 );
			
			GeoPoint gp = new GeoPoint(latitude, longitude);
			Point p = new Point();
			
			projection.toPixels(gp, p);
			
			if(i==0)
			{
				x= p.x;
				y=p.y;
				path.moveTo(p.x,p.y);
			}
			else
				path.lineTo(p.x, p.y);
			
			Log.d("CO : ", latitude + " " + longitude);
		}
		
		path.lineTo(x, y);
		//path.lineTo((float)lat.get(0).doubleValue(),(float) lon.get(0).doubleValue());
		return path;
		
	}
	public void draw(Canvas canvas,MapView mpv,boolean shadow ){
		Projection projection=mpv.getProjection();
		
		Paint mpaint;
		Path mpath;

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
				
				
				LinkedList<Double>  lat  = new LinkedList <Double> ()  ;
				LinkedList<Double> lon  = new LinkedList <Double> () ;
				
				populate("dhaka", lat, lon);
				mpath = givePath(projection,lat,lon);
				mpaint = getPaint("dhaka");
				canvas.drawPath(mpath,mpaint);
				
				populate("chittagong", lat, lon);
				mpath = givePath(projection,lat,lon);
				mpaint = getPaint("chittagong");
				canvas.drawPath(mpath,mpaint);
				
				canvas.drawOval(oval, paint);
				canvas.drawText("You!!", myPoint.x+rad+2, myPoint.y, paint);
			
		}
		
		else
		{
			
		}
	}
	

}




