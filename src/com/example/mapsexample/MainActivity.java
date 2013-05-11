package com.example.mapsexample;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	private GoogleMap googleMap;
	private SupportMapFragment fm;
	private List<Instituto> institutosList;
	private InstitutoDatabaseHandler instDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instDB = new InstitutoDatabaseHandler(getApplicationContext());
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
		if(status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
			dialog.show();
		}
		else {
			fm = new SupportMapFragment() {
				@Override
				public void onActivityCreated(Bundle b) {
					super.onActivityCreated(b);
					
					initializeGoogleMaps();
					AdicionaInstitutos.addAll(getApplicationContext());
					institutosList = instDB.getAllInstitutos();
					
					for (Instituto instituto : institutosList) {
						addMarkerToMaps(instituto);
					}
					
					googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(institutosList.get(0).getLatitude(), institutosList.get(0).getLongitude())));
					googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
				}
			};
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, fm).commit();
		}
	}
	
	protected void initializeGoogleMaps() {
		googleMap = fm.getMap();
		
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
				marker.showInfoWindow();
				return false;
			}
		});
	}

	protected void addMarkerToMaps(Instituto i) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(i.getLatitude(), i.getLongitude())).title(i.getTitle()).snippet(i.getDescription()).icon(BitmapDescriptorFactory.fromResource(i.getIcon()));
		googleMap.addMarker(marker);
	}
}