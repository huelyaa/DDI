package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PercipitantClass {
	private String name;
	private String id;
	private List<PercipitantItem> percipitantItems = new ArrayList<PercipitantItem>();

	public PercipitantClass() {
		Date date = new Date();
		id = String.valueOf(date.getTime());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PercipitantItem> getPercipitantItems() {
		return percipitantItems;
	}

	public void setPercipitantItems(List<PercipitantItem> percipitantItems) {
		this.percipitantItems = percipitantItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
