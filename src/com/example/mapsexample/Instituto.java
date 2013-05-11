package com.example.mapsexample;

public class Instituto {

	private Integer id;
	private String name;
	private double latitude;
	private double longitude;
	private String title;
	private String description;
	private ECategory category;
	private int icon;
	
	public Instituto() {
		
	}
	
	public Instituto(String name, double latitude, double longitude, String title, String description, ECategory category, int icon) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.title = title;
		this.description = description;
		this.category = category;
		this.icon = icon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ECategory getCategory() {
		return category;
	}

	public void setCategory(ECategory category) {
		this.category = category;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
