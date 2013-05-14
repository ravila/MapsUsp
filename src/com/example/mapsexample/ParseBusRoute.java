package com.example.mapsexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public class ParseBusRoute {
	
	private Context context;
	
	public ParseBusRoute(Context context) {
		this.context = context;
	}

	public List<LatLng> getRouteList8012() throws IOException {
		InputStream is = context.getAssets().open("busp_8012.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		List<LatLng> list = new ArrayList<LatLng>();
		
		while((line = br.readLine()) != null) {
			String[] values = line.split(";");
			list.add(new LatLng(Double.parseDouble(values[1]), Double.parseDouble(values[0])));
		}
		return list;
	}

	public List<LatLng> getRouteList8022() throws IOException {
		InputStream is = context.getAssets().open("busp_8022.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		List<LatLng> list = new ArrayList<LatLng>();
		
		while((line = br.readLine()) != null) {
			String[] values = line.split(";");
			list.add(new LatLng(Double.parseDouble(values[1]), Double.parseDouble(values[0])));
		}
		return list;
	}
}
