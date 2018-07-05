package com.example.bean;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable{
	String name = null;
	String description = null;
	List<Relationship> relationship = null;
	String headImage = null;
	String bigImage = null;
	String fileName = null;
	public Person(String name, String headImage, String fileName) {
		super();
		this.name = name;
		this.headImage = headImage;
		this.fileName = fileName;
	}
	
	public Person(String name, String description, List<Relationship> relationship, String headImage, String bigImage) {
		super();
		this.name = name;
		this.description = description;
		this.relationship = relationship;
		this.headImage = headImage;
		this.bigImage = bigImage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Relationship> getRelationship() {
		return relationship;
	}
	public void setRelationship(List<Relationship> relationship) {
		this.relationship = relationship;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getBigImage() {
		return bigImage;
	}
	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", description=" + description + ", relationship=" + relationship.toString()
				+ ", headImage=" + headImage + ", bigImage=" + bigImage + ", fileName=" + fileName + "]";
	}
	
}
