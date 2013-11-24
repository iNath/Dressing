package org.ups.dressingengine.utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ups.dressingengine.impl.DressingSuggestionImpl;
import org.ups.weather.IWeather;

public class Activator implements BundleActivator {

	public Activator() {
		
	}

	public void start(final BundleContext context) throws Exception {
		System.out.println("DressingSuggestion start()");

		IWeather weatherService = (IWeather) context.getService(context.getServiceReference(IWeather.class.getName()));
		new DressingSuggestionImpl(weatherService);
	}

	public void stop(final BundleContext context) throws Exception {
		System.out.println("DressingSuggestion stop()");
	}

}