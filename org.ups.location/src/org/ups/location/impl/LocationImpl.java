package org.ups.location.impl;

import java.util.ArrayList;

import org.ups.location.ILocation;
import org.ups.location.ILocationListener;

public class LocationImpl implements ILocation{

	ArrayList<ILocationListener> listeners;
	
	public LocationImpl(){
		listeners = new ArrayList<ILocationListener>();
	}
	
	public float getLatitude() {
		// TODO Auto-generated method stub
		System.out.println("Location -> getLatitude call");
		return 10;
	}

	public float getLongitude() {
		// TODO Auto-generated method stub
		System.out.println("Location -> getLongitude call");
		return 0;
	}

	public void addListener(ILocationListener listener) {
		// TODO Auto-generated method stub
		System.out.println("Location -> addListener call");
	}

	public void removeListener(ILocationListener listener) {
		// TODO Auto-generated method stub
		System.out.println("Location -> removeListener call");
	}

}
