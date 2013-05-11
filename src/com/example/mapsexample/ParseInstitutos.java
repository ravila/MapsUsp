package com.example.mapsexample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ParseInstitutos {
	
	Context myContext;
	
	public ParseInstitutos(Context myContext) {
		this.myContext = myContext;
	}
	
	public List<Instituto> getAllInstitutos() {
		List<Instituto> institutoList = new ArrayList<Instituto>();
		BufferedReader reader;
		try {
			InputStream myInput = myContext.getAssets().open("institutos.txt");
			reader = new BufferedReader(new InputStreamReader(myInput));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(";");
				//Instituto i = new Instituto(values[0], Double.parseDouble(values[1]), Double.parseDouble(values[2]), values[3], values[4], R.drawable.ic_launcher);
				//institutoList.add(i);
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return institutoList;
	}

}
