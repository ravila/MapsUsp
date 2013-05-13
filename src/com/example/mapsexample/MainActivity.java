package com.example.mapsexample;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends FragmentActivity {

	@Override
	public boolean onSearchRequested() {
		Toast.makeText(getApplicationContext(), "oiii", Toast.LENGTH_SHORT).show();
		return super.onSearchRequested();
	}

	private GoogleMap googleMap;
	private SupportMapFragment fm;
	private MapOverlays mapOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
	    }

		mapOverlay = new MapOverlays(getApplicationContext());
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
					
					mapOverlay.showInstitutoByCategory(googleMap, ECategory.EXATAS);
					
					googleMap.moveCamera(CameraUpdateFactory.newLatLng(MapOverlays.USP_CENTER));
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
	
	private void enableMyLocation(boolean enabled) {
		googleMap.setMyLocationEnabled(enabled);
	}

}