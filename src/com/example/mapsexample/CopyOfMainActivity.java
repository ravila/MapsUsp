package com.example.mapsexample;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class CopyOfMainActivity extends FragmentActivity implements LocationListener {

	private GoogleMap googleMap;
	private SupportMapFragment fm;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BatteryReceiver receiver = new BatteryReceiver(); 
		IntentFilter inf = new IntentFilter();
		inf.addAction("android.intent.action.BATTERY_LOW");
		registerReceiver(receiver, inf);

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
					startGPS();
					
					addMarkerToMaps(-23.523149, -46.174228, "IME", "Renato", R.drawable.ic_launcher);
					addMarkerToMaps(-23.526730, -46.175623, "FEA", "Julianna", R.drawable.ic_launcher);
				}
			};
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, fm).commit();
		}
	}
	
	protected void initializeGoogleMaps() {
		googleMap = fm.getMap();
		googleMap.setMyLocationEnabled(true);
		
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
				marker.showInfoWindow();
				return false;
			}
		});
	}

	private void startGPS() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		if(location != null) {
			onLocationChanged(location);
		}
		locationManager.requestLocationUpdates(provider, 20000, 0, CopyOfMainActivity.this);
	}
	
	public void stopGPS() {
		if(locationManager != null) {
			locationManager.removeUpdates(CopyOfMainActivity.this);
		}
	}

	protected void addMarkerToMaps(double latitude, double longitude, String title,	String description, int icon) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(latitude, longitude)).title(title).snippet(description).icon(BitmapDescriptorFactory.fromResource(icon));
		googleMap.addMarker(marker);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);

		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	public class BatteryReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			stopGPS();
		}
		
	}
}