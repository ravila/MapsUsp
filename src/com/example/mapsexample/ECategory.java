package com.example.mapsexample;


public enum ECategory {

	EXATAS, HUMANAS, BIOLOGICAS, ALL, NONE;
	
	public static ECategory getById(int id) {
		return ECategory.values()[id];
	}
	
	public static int getByName(ECategory category) {
		return category.ordinal();
	}
	
	public static String getStringName(ECategory category) {
		if(category == EXATAS)
			return "Exatas";
		if(category == HUMANAS)
			return "Humanas";
		if(category == BIOLOGICAS)
			return "Biologicas";
		return "";
	}
}
