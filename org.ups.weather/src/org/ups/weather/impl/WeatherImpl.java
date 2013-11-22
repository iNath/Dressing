package org.ups.weather.impl;

import java.util.ArrayList;


import org.ups.location.ILocation;
import org.ups.location.ILocationListener;
import org.ups.weather.IWeather;
import org.ups.weather.IWeatherListener;
import org.ups.weather.WeatherType;

public class WeatherImpl implements IWeather {
	
	ArrayList<IWeatherListener> listeners;
	ILocation locationService;
	

	public WeatherImpl(ILocation locationService) {
		listeners = new ArrayList<IWeatherListener>();
		this.locationService = locationService;
		
		
		this.locationService.addListener(new ILocationListener(){

			public void locationChanged(float lan, float lon) {
				
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
	
	public void locationChangedHandler(){
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).weatherChanged(getCurrentWeather());
		}
	}

}
