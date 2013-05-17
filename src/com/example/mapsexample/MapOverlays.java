package com.example.mapsexample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapOverlays {

	public static LatLng USP_CENTER = new LatLng(-23.560289,-46.727471);
	private Context context;
	private List<Instituto> institutosList;
	private InstitutoDatabaseHandler instDB;
	private List<Marker> markerList;
	
	public MapOverlays(Context context) {
		this.context = context;
		initializeIntitutosList();
	}
	
	private void initializeIntitutosList() {
		instDB = new InstitutoDatabaseHandler(context);
		institutosList = instDB.getAllInstitutos();
		markerList = new ArrayList<Marker>();
	}

	public void showAllInstitutos(GoogleMap googleMap) {
		clearMap(googleMap);
		for (Instituto instituto : institutosList) {
			Marker marker = addMarkerToMaps(instituto, googleMap);
			markerList.add(marker);
		}
	}
	
	public void showInstitutoByCategory(GoogleMap googleMap, ECategory category) {
		if(category == ECategory.ALL) {
			showAllInstitutos(googleMap);
			return;
		}
		clearMap(googleMap);
		for (Instituto instituto : institutosList) {
			if(instituto.getCategory() == category) {
				Marker marker = addMarkerToMaps(instituto, googleMap);
				markerList.add(marker);
			}
		}
	}
	
	public List<Instituto> getInstitutosBySearch(String search) {
		List<Instituto> institutos = new ArrayList<Instituto>();
		
		
		return institutos;
	}
	
	private Marker addMarkerToMaps(Instituto i, GoogleMap googleMap) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(i.getLatitude(), i.getLongitude())).title(i.getTitle()).snippet(i.getDescription()).icon(BitmapDescriptorFactory.fromResource(i.getIcon()));
		return googleMap.addMarker(marker);
	}
	
	private void clearMap(GoogleMap googleMap) {
		if(markerList != null) {
			for (Marker marker : markerList) {
				marker.remove();
			}
			markerList.clear();
		}
	}
}
