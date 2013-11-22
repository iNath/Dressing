package org.ups.weather.utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.location.ILocation;
import org.ups.weather.IWeather;
import org.ups.weather.impl.WeatherImpl;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("Weather start()");
		
		ILocation locationService = (ILocation) context.getService(context.getServiceReference(ILocation.class.getName()));
		IWeather service = new WeatherImpl(locationService);

		context.registerService(IWeather.class.getName(), service, null);

	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
