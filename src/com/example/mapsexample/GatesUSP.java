package com.example.mapsexample;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GatesUSP {

	private Gate[] gates_usp = null; 
	private List<Marker> markerList;
	
	public GatesUSP() {
		fillGatesUsp();
	}
	
	private void fillGatesUsp() {
		if(gates_usp == null) {
			gates_usp = new Gate[3];
			gates_usp[0] = new Gate(-23.565128, -46.712408, "portao 1", "horarios: ", R.drawable.gate);
			gates_usp[1] = new Gate(-23.551064, -46.732149, "portao 2", "horarios: ", R.drawable.gate);
			gates_usp[2] = new Gate(-23.569258, -46.741118, "portao 3", "horarios: ", R.drawable.gate);
			
			markerList = new ArrayList<Marker>();
		}
	}

	public void showAllGatesOnMap(GoogleMap googleMap) {
		cleanGates();
		for (Gate gate : gates_usp) {
			Marker marker = addGateToMaps(gate, googleMap);
			markerList.add(marker);
		}
	}

	private Marker addGateToMaps(Gate i, GoogleMap googleMap) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(i.getLatitude(), i.getLongitude())).title(i.getTitle()).snippet(i.getSchedule()).icon(BitmapDescriptorFactory.fromResource(i.getIcon()));
		return googleMap.addMarker(marker);
	}
	
	public void cleanGates() {
		if(markerList != null && !markerList.isEmpty()) {
			for (Marker marker : markerList) {
				marker.remove();
			}
			markerList.clear();
		}
	}
}
