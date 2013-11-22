package org.ups.location.impl;

import java.util.ArrayList;

import org.ups.location.ILocation;
import org.ups.location.ILocationListener;

public class LocationImpl implements ILocation, Runnable{

	ArrayList<ILocationListener> listeners;
	
	public LocationImpl(){
		listeners = new ArrayList<ILocationListener>();
		new Thread(this).start();
	}
	
	public float getLatitude() {
		// TODO Auto-generated method stub
		System.out.println("Location -> getLatitude call");
		return (float) 0.14;
	}

	public float getLongitude() {
		// TODO Auto-generated method stub
		System.out.println("Location -> getLongitude call");
		return (float) 0.10;
	}

	public void addListener(ILocationListener listener) {
		// TODO Auto-generated method stub
		System.out.println("Location -> addListener call");
		listeners.add(listener);
	}

	public void removeListener(ILocationListener listener) {
		// TODO Auto-generated method stub
		System.out.println("Location -> removeListener call");
		
		for(int i=0;i<listeners.size();i++){
			if(listener.equals(listeners.get(i))){
				listeners.remove(i);
				break;
			}
		}
	}

	public void run() {
		
		while(true){
			
			// On va déclencher les événements
			for(int i=0;i<listeners.size();i++){
				listeners.get(i).locationChanged(this.getLatitude(), this.getLongitude());
			}
			
			try {
				Thread.sleep(Math.round(Math.random() * 4000));
			} catch (InterruptedException e) {
				
			}
		}
		
	}

}
