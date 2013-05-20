package com.example.mapsexample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;

public class TimeCalculatorBUSP {
	
	private SaidaBusParser saidaBus;
	
	public TimeCalculatorBUSP(Context context) {
		saidaBus = new SaidaBusParser(context);
	}
	
	//Dado os pontos de onibus ordenados. - pointList
	//Dado o numero total de pontos de onibus - pointListLength
	//Dado o tempo de uma viagem completa - totalTime
	//Dado lista de horarios de saida dos onibus de uma dada linha - busList - busLine
	//Dado o ponto de onibus do usuario - int userPoint
	//Dado a hora atual - int minutes;
	
	public int time(int busLine) {
		int totalTime = getTotalTime();
		int intervaloPontos = totalTime/10;//pointListLength;
		int tempoTrajeto = intervaloPontos*4;//userPoint;
		int minutes = getTimeInMinutes();
		
		List<Integer> busList = getBusList(totalTime, busLine);
		
		for (Integer time : busList) {
			int hora = time + tempoTrajeto;
			if(hora > minutes)
				return hora-minutes;
		}
		
		return -1;
	}
	
	private List<Integer> getBusList(int totalTime, int busLine) {
		if(busLine == 1) {
			if(totalTime == 50)
				return saidaBus.getListWeekDay_8012();
			if(totalTime == 40)
				return saidaBus.getListSaturday_8012();
			
			return saidaBus.getListSunday_8012();
		}
		
		if(totalTime == 50)
			return saidaBus.getListWeekDay_8022();
		if(totalTime == 40)
			return saidaBus.getListSaturday_8022();
		
		return saidaBus.getListSunday_8022();
	}

	public int getTimeInMinutes() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		String hour = sdf.format(d);
		String[] values = hour.split(":");
		
		return Integer.parseInt(values[0])*60 + Integer.parseInt(values[1]);
	}
	
	public int getTotalTime() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		
		System.out.println("DIA: " + day);
		
		if(day == Calendar.SUNDAY)
			return 35;
		if(day == Calendar.SATURDAY)
			return 40;
		
		return 50;
	}

}
