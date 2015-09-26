package com.workpoint.mwallet.client.ui.dashboard.model;

public class DataModel {
	private int id;
	private String name;
	private double data;
	private String title;
	private String color;

	public DataModel(int id, String name, String title, double data,
			String color) {
		this.id = id;
		this.name = name;
		this.data = data;
		this.title = title;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public String getColor() {
		return color;
	}
}
