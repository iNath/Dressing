package org.ups.location.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.ups.location.ILocation;
import org.ups.location.ILocationListener;

public class LocationImpl implements ILocation, Runnable{

	ArrayList<ILocationListener> listeners;
	float latitude;
	float longitude;
	
	public LocationImpl(){
		listeners = new ArrayList<ILocationListener>();
		new Thread(this).start();
	}
	
	public float getLatitude() {
		return this.latitude;
	}

	public float getLongitude() {
		return this.longitude;
	}

	public void addListener(ILocationListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ILocationListener listener) {
		for(int i=0;i<listeners.size();i++){
			if(listener.equals(listeners.get(i))){
				listeners.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Va chercher la position
	 */
	private void retreiveNewLocation(){
		this.log("Api freegeoip is going called");
		
		URL url = null;
		try {
			url = new URL("http://freegeoip.net/json/");
		
			InputStream is = url.openStream();
			JsonReader rdr = Json.createReader(is);
					
			JsonObject obj = rdr.readObject();
			float newLatitude = obj.getJsonNumber("latitude").longValue();
			float newLongitude = obj.getJsonNumber("longitude").longValue();
			
			if(this.latitude != newLatitude && this.longitude != newLongitude){
				this.latitude = newLatitude;
				this.longitude = newLongitude;
				// Si la position a changé on déclenche l'event
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).locationChanged(this.latitude, this.longitude);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			this.log("Erreur à l'appel de l'api");
		}
	}
	
	private void log(String str){
		System.out.println("[Location] " + str);
	}

	public void run() {
		
		while(true){
			
			this.retreiveNewLocation();
			
			// On laisse un délais pour checker l'api
			try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
				
			}
		}
		
	}

}
