package org.ups.dressingengine.impl;

import java.util.ArrayList;

import org.ups.dressingengine.IDressingSuggestion;
import org.ups.dressingengine.IDressingSuggestionListener;
import org.ups.weather.IWeather;
import org.ups.weather.IWeatherListener;
import org.ups.weather.WeatherType;

public class DressingSuggestionImpl implements IDressingSuggestion {

	ArrayList<IDressingSuggestionListener> listeners;
	IWeather weatherService;
	boolean sunGlassesNeeded, umbrellaNeeded, coatNeeded;
	
	public DressingSuggestionImpl(IWeather weatherService){
		listeners = new ArrayList<IDressingSuggestionListener>();
		this.weatherService = weatherService;
		
		
		this.weatherService.addListener(new IWeatherListener(){
			public void weatherChanged(WeatherType newWeather) {
				weatherChangedHandler();
			}
		});
	}
	
	public void addListener(IDressingSuggestionListener listener) {
		
		listeners.add(listener);
		
	}

	public void removeListener(IDressingSuggestionListener listener) {
		
		for(int i=0;i<listeners.size();i++){
			if(listener.equals(listeners.get(i))){
				listeners.remove(i);
				break;
			}
		}
	}

	public boolean sunGlassesNeeded() {
		if(this.weatherService.getCurrentWeather() == WeatherType.SHINY){
			return true;
		}
		return false;
	}

	public boolean umbrellaNeeded() {
		switch(this.weatherService.getCurrentWeather()){
			case RAINY:
			case SHOWERS: return true;
			default: break;
		}
		return false;
	}

	public boolean coatNeeded() {
		switch(this.weatherService.getCurrentWeather()){
			case CLOUDY:
			case RAINY:
			case SHOWERS:
			case SNOW: return true;
			default: break;
		}
		return false;
	}
	
	/**
	 * Permet de savoir si les suggestions ont changé
	 * @return boolean
	 */
	private boolean isSuggestionChanged(){
		return this.umbrellaNeeded != this.umbrellaNeeded()
				|| this.sunGlassesNeeded != this.sunGlassesNeeded()
				|| this.coatNeeded != this.coatNeeded();
	}
	
	private void weatherChangedHandler(){
		this.log("Weather change detected");
		
		if(this.isSuggestionChanged()){
			this.coatNeeded = this.coatNeeded();
			this.sunGlassesNeeded = this.sunGlassesNeeded();
			this.umbrellaNeeded = this.umbrellaNeeded();
			// On notifie les listeners
			for(int i=0;i<listeners.size();i++){
				listeners.get(i).dressingSuggestionChanged(this);
			}
			
			this.log("Coat: " + this.coatNeeded + " SunGlasses: " + this.sunGlassesNeeded() + " Umbrella: " + this.umbrellaNeeded());
		}
	}

	
	private void log(String str){
		System.out.println("[Dressing] " + str);
	}
	
}
