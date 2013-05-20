package com.example.mapsexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class SaidaBusParser {

	private Context context;

	List<Integer> listWeekDay_8012;
	List<Integer> listSunday_8012;
	List<Integer> listSaturday_8012;
	List<Integer> listWeekDay_8022;
	List<Integer> listSunday_8022;
	List<Integer> listSaturday_8022;

	public SaidaBusParser(Context context) {
		this.context = context;
		getSaidaList8012();
		getSaidaList8022();
	}

	private void getSaidaList8012() {
		try {
			InputStream is = context.getAssets().open("saida_8012.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			listWeekDay_8012 = new ArrayList<Integer>();
			listSunday_8012 = new ArrayList<Integer>();
			listSaturday_8012 = new ArrayList<Integer>();

			while((line = br.readLine()) != null) {
				String[] values = line.split(";");
				listWeekDay_8012.add(getHourInMinutes(values[0]));
				if(values.length > 1 && values[1] != null && !values[1].equals(""))
					listSaturday_8012.add(getHourInMinutes(values[1]));
				if(values.length > 2 && values[2] != null && !values[2].equals(""))
					listSunday_8012.add(getHourInMinutes(values[2]));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void getSaidaList8022() {
		try {
			InputStream is = context.getAssets().open("saida_8022.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			listWeekDay_8022 = new ArrayList<Integer>();
			listSunday_8022 = new ArrayList<Integer>();
			listSaturday_8022 = new ArrayList<Integer>();

			while((line = br.readLine()) != null) {
				String[] values = line.split(";");
				listWeekDay_8022.add(getHourInMinutes(values[0]));
				if(values.length > 1 && values[1] != null && !values[1].equals(""))
					listSaturday_8022.add(getHourInMinutes(values[1]));
				if(values.length > 2 && values[2] != null && !values[2].equals(""))
					listSunday_8022.add(getHourInMinutes(values[2]));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private int getHourInMinutes(String hour) {
		String[] values = hour.split(":");
		
		return Integer.parseInt(values[0])*60 + Integer.parseInt(values[1]);
	}

	public List<Integer> getListWeekDay_8012() {
		return listWeekDay_8012;
	}

	public List<Integer> getListSunday_8012() {
		return listSunday_8012;
	}

	public List<Integer> getListSaturday_8012() {
		return listSaturday_8012;
	}

	public List<Integer> getListWeekDay_8022() {
		return listWeekDay_8022;
	}

	public List<Integer> getListSunday_8022() {
		return listSunday_8022;
	}

	public List<Integer> getListSaturday_8022() {
		return listSaturday_8022;
	}
}
