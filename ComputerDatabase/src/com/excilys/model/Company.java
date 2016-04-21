package com.excilys.model;

public class Company {

	long id;

	String name = null;

	public Company(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "(" + id + ", " + name + ")";
	}

}
