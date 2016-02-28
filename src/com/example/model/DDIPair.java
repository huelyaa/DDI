package com.example.model;

import java.util.Date;

public class DDIPair {
	private String name;
	private String id;
	private Status status;
	private Boolean myDdi;
	private ObjectClass objectClass;
	private PercipitantClass percipitantClass;

	public DDIPair() {
		Date date = new Date();
		id = String.valueOf(date.getTime());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ObjectClass getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(ObjectClass objectClass) {
		this.objectClass = objectClass;
	}

	public PercipitantClass getPercipitantClass() {
		return percipitantClass;
	}

	public void setPercipitantClass(PercipitantClass percipitantClass) {
		this.percipitantClass = percipitantClass;
	}

	public Boolean getMyDdi() {
		return myDdi;
	}

	public void setMyDdi(Boolean myDdi) {
		this.myDdi = myDdi;
	}
}
