package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectClass {
	private String name;
	private String id;
	private List<ObjectItem> objectItems = new ArrayList<ObjectItem>();

	public ObjectClass() {
		Date date = new Date();
		id = String.valueOf(date.getTime());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ObjectItem> getObjectItems() {
		return objectItems;
	}

	public void setObjectItems(List<ObjectItem> objectItems) {
		this.objectItems = objectItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
