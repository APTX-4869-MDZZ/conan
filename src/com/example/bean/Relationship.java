package com.example.bean;

import java.io.Serializable;

public class Relationship implements Serializable{
	String image = null;
	String relation = null;
	public Relationship(String image, String relation) {
		super();
		this.image = image;
		this.relation = relation;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	@Override
	public String toString() {
		return "Relationship [image=" + image + ", relation=" + relation + "]";
	}
	
}
