package com.example.mapsexample;

public enum ECategory {

	EXATAS, HUMANAS, BIOLOGICAS, ALL, NONE;
	
	public static ECategory getById(int id) {
		return ECategory.values()[id];
	}
	
	public static int getByName(ECategory category) {
		return category.ordinal();
	}
}
