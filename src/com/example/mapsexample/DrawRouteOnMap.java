package com.example.mapsexample;

import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class DrawRouteOnMap {
	
	private PolylineOptions route;
	private Polyline line;
	
	public DrawRouteOnMap() {
		route = new PolylineOptions();
	}
	
	public void drawRoute(List<LatLng> routeList, GoogleMap googleMap, int color) {
		cleanRoutes();
		route.addAll(routeList).width(2).color(color).geodesic(true);
		line = googleMap.addPolyline(route);
	}

	public void cleanRoutes() {
		if(line != null)
			line.remove();
	}

}
