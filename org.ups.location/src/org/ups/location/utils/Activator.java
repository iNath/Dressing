package org.ups.location.utils;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.ups.location.ILocation;
import org.ups.location.impl.LocationImpl;

public class Activator implements BundleActivator {

	public Activator() {
	}

	public void start(final BundleContext context) throws Exception {
		System.out.println("Location start()");

		ILocation service = new LocationImpl();		
		
		context.registerService(ILocation.class.getName(), service, null);
	}

	public void stop(final BundleContext context) throws Exception {
		System.out.println("Location stop()");
	}

}