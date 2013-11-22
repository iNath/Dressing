package org.ups.location.utils;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.ups.location.ILocation;
import org.ups.location.impl.LocationImpl;

public class Activator implements BundleActivator {

	private ILocation service = null;

	public Activator() {
		service = new LocationImpl();
	}

	public void start(final BundleContext context) throws Exception {
		System.out.println("Location start() yes IIIIII 2?");

		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("name", "org.ups.location");
		context.registerService(ILocation.class.getName(), service, properties);
	}

	public void stop(final BundleContext context) throws Exception {
		System.out.println("Location stop()");

		service = null;
	}

}