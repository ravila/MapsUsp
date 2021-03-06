package com.example.mapsexample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
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
	private DrawRouteOnMap route8012;
	private DrawRouteOnMap route8022;
	private ParseBusRoute busRoute;
	private GatesUSP gatesUsp;
	private ArrayList<Integer> institutosShowing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AdicionaInstitutos.addAll(getApplicationContext());
		mapOverlay = new MapOverlays(this);
		route8012 = new DrawRouteOnMap();
		route8022 = new DrawRouteOnMap();
		busRoute = new ParseBusRoute(this);
		gatesUsp = new GatesUSP();
		institutosShowing = new ArrayList<Integer>();
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
					
					//mapOverlay.showInstitutoByCategory(googleMap, ECategory.EXATAS);
					
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
	
	public void enableMyLocation(boolean enabled) {
		googleMap.setMyLocationEnabled(enabled);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuItem bus_8012 = menu.add(0, Menu.NONE, Menu.NONE, "Rota 8012");
    	bus_8012.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				try {
					route8022.cleanRoutes();
					route8012.drawRoute(busRoute.getRouteList8012(), googleMap, Color.RED);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
    	MenuItem time = menu.add(0, Menu.NONE, Menu.NONE, "Tempo");
    	time.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Intent i = new Intent(MainActivity.this, TimeActivity.class);
				startActivity(i);
				return true;
			}
		});
    	MenuItem inst = menu.add(0, Menu.NONE, Menu.NONE, "Lista");
    	inst.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Intent i = new Intent(MainActivity.this, InstitutoListSelectorActivity.class);
				i.putIntegerArrayListExtra("list", institutosShowing);
				startActivityForResult(i, 1);
				return true;
			}
		});
    	MenuItem bus_8022 = menu.add(0, Menu.NONE, Menu.NONE, "Rota 8022");
    	bus_8022.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				try {
					route8012.cleanRoutes();
					route8022.drawRoute(busRoute.getRouteList8022(), googleMap, Color.BLUE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
    	MenuItem both_bus = menu.add(0, Menu.NONE, Menu.NONE, "8022 e 8012");
    	both_bus.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				try {
					route8012.drawRoute(busRoute.getRouteList8012(), googleMap, Color.RED);
					route8022.drawRoute(busRoute.getRouteList8022(), googleMap, Color.BLUE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
    	MenuItem cleanRoutes = menu.add(0, Menu.NONE, Menu.NONE, "Limpar Rotas");
    	cleanRoutes.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				route8012.cleanRoutes();
				route8022.cleanRoutes();
				return true;
			}
		});
    	MenuItem showGates = menu.add(0, Menu.NONE, Menu.NONE, "Mostrar Portoes");
    	showGates.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				gatesUsp.showAllGatesOnMap(googleMap);
				return true;
			}
		});
    	MenuItem removeGates = menu.add(0, Menu.NONE, Menu.NONE, "Remover Portoes");
    	removeGates.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				gatesUsp.cleanGates();
				return true;
			}
		});
    	MenuItem enableLocation = menu.add(0, Menu.NONE, Menu.NONE, "Mostrar/Ocultar GPS");
    	enableLocation.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				enableMyLocation(!googleMap.isMyLocationEnabled());
				return true;
			}
		});
    	MenuItem chooseCategory = menu.add(0, Menu.NONE, Menu.NONE, "Filtrar por categoria");
    	chooseCategory.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Escolha uma categoria");
				CharSequence[] items = {"Humanas", "Exatas", "Biologicas", "Todas", "Nenhuma"};
				builder.setSingleChoiceItems(items, -1, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ECategory category;
						switch (which) {
						case 0:
							category = ECategory.HUMANAS;
							break;
						case 1:
							category = ECategory.EXATAS;
							break;
						case 2:
							category = ECategory.BIOLOGICAS;
							break;
						case 3:
							category = ECategory.ALL;
							break;
						default:
							category = ECategory.NONE;
							break;
						}
						mapOverlay.showInstitutoByCategory(googleMap, category);
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			}
		});
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(getApplicationContext(), "oi", Toast.LENGTH_SHORT).show();
		institutosShowing = data.getIntegerArrayListExtra("result");
		InstitutoDatabaseHandler db = new InstitutoDatabaseHandler(getApplicationContext());
		List<Instituto> inst = db.getAllInstitutos();
		List<Instituto> list = new ArrayList<Instituto>();

		System.out.println("ALOOOOO");
		for (Integer i : institutosShowing) {
			System.out.println("CONTEM - " + i);
		}
		for(int i = 0; i < inst.size(); i++) {
			if(institutosShowing.contains(i)) {
				list.add(inst.get(i));
			}
		}
		
		mapOverlay.showListIntitutos(googleMap, list);
	}

}