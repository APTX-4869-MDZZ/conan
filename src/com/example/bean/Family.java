package com.example.bean;

import java.util.List;

public class Family {
	String image = null;
	String name = null;
	List<Person> persons = null;
	public Family(String image, String name, List<Person> persons) {
		super();
		this.image = image;
		this.name = name;
		this.persons = persons;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
}
