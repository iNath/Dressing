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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean umbrellaNeeded() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean coatNeeded() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void weatherChangedHandler(){
		System.out.println("Weather changed detected ;) youpiiii");
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).dressingSuggestionChanged(this);
		}
	}

}
