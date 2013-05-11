package com.example.mapsexample;

public enum ECategory {

	EXATAS, HUMANAS, BIOLOGICAS;
	
	public static ECategory getById(int id) {
		return ECategory.values()[id];
	}
	
	public static int getByName(ECategory category) {
		return category.ordinal();
	}
}
