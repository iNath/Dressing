package org.ups.weather.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.ups.location.ILocation;
import org.ups.location.ILocationListener;
import org.ups.weather.IWeather;
import org.ups.weather.IWeatherListener;
import org.ups.weather.WeatherType;

public class WeatherImpl implements IWeather, Runnable {
	
	ArrayList<IWeatherListener> listeners;
	ILocation locationService;
	WeatherType weather;

	public WeatherImpl(ILocation locationService) {
		listeners = new ArrayList<IWeatherListener>();
		this.locationService = locationService;
		this.weather = WeatherType.UNKNOWN;
		
		// On lance le thread
		new Thread(this).start();
		
		// On écoute l'evenement pour savoir si on a changé de lieu
		this.locationService.addListener(new ILocationListener(){

			public void locationChanged(float lan, float lon) {
				locationChangedHandler();
			}
		});
	}

	public void addListener(IWeatherListener listener) {
		listeners.add(listener);

	}

	public void removeListener(IWeatherListener listener) {
		for(int i=0;i<listeners.size();i++){
			if(listener.equals(listeners.get(i))){
				listeners.remove(i);
				break;
			}
		}
	}

	public WeatherType getCurrentWeather() {
		
		WeatherType weather;
		switch((int)Math.floor(Math.random()*6)){
			case 0:
				 weather=WeatherType.CLOUDY;
				 break;
			case 1:
				 weather=WeatherType.RAINY;
				 break;
			case 2:
				 weather=WeatherType.SHINY;
				 break;
			case 3:
				 weather=WeatherType.SHOWERS;
				 break;
			case 4:
				 weather=WeatherType.SNOW;
				 break;
			default:
				 weather=WeatherType.UNKNOWN;
		}
		
		return weather;
	}

	public WeatherType getWeather(int nbHoursFromNow) {
		return null;
	}
	
	
	private void locationChangedHandler(){
		// On va recalculer le temps qui a potentiellement changé
		this.retreiveNewWeather();
	}
	
	/**
	 * Va chercher le temps
	 */
	private void retreiveNewWeather(){
		this.log("Api OpenWeatherMap is going called");
		
		URL url = null;
		try {
			url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+locationService.getLatitude()+"&lon="+locationService.getLongitude());
		
			InputStream is = url.openStream();
			JsonReader rdr = Json.createReader(is);
					
			JsonObject obj = rdr.readObject();
			int previsionId = obj.getJsonArray("weather").getValuesAs(JsonObject.class).get(0).getJsonNumber("id").intValue();
			
			if(!this.weather.equals(this.convertWeatherFromApi(previsionId))){
				this.weather = this.convertWeatherFromApi(previsionId);
				// Si le temps a changé on déclenche l'event
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).weatherChanged(this.getCurrentWeather());
				}
			}
			
		} catch (IOException e) {
			this.log("Erreur à l'appel de l'api");
		}
	}
	
	/**
	 * Convertisseur du retour de l'api vers le type utilisé en interne
	 * @see http://bugs.openweathermap.org/projects/api/wiki/Weather_Data
	 * @param id l'id du temps retourné par l'api
	 * @return le type reconnu
	 */
	private WeatherType convertWeatherFromApi(int id){
		
		WeatherType toReturn;
		
		int globalId = (int) Math.floor(id / 100);
		
		switch(globalId){
			case 5: toReturn = WeatherType.SHINY; break;
			
			case 6: toReturn = WeatherType.RAINY; break;
			
			case 8: toReturn = WeatherType.CLOUDY; break;
			
			default: toReturn = WeatherType.UNKNOWN; break;
		}
		
		// Cas particuliers
		switch(id){
			case 800: toReturn = WeatherType.SHINY; break;	
		}
		return toReturn;
		
	}
	
	private void log(String str){
		System.out.println("[Weather] " + str);
	}
	
	
	public void run() {
		
		
		
		while(true){
			
			this.retreiveNewWeather();
			
			// On impose un délais entre chaque appels
			try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
				
			}
		}
		
	}

}
