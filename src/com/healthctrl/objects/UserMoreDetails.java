package com.healthctrl.objects;

public class UserMoreDetails {
	private String gluten;
	private String sugar;
	private String lactose;
	private String vegiterian;
	private String vegan;
	private float weight;
	private int height;
	
	public UserMoreDetails(String gluten, String sugar, String lactose, String vegiterian, String vegan, float weight, int height) {
		super();
		this.gluten = gluten;
		this.sugar = sugar;
		this.lactose = lactose;
		this.vegiterian = vegiterian;
		this.vegan = vegan;
		this.weight = weight;
		this.height = height;
	}
	public String getVegiterian() {
		return vegiterian;
	}
	public void setVegiterian(String vegiterian) {
		this.vegiterian = vegiterian;
	}
	public String getVegan() {
		return vegan;
	}
	public void setVegan(String vegan) {
		this.vegan = vegan;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getGluten() {
		return gluten;
	}
	public void setGluten(String gluten) {
		this.gluten = gluten;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public UserMoreDetails(String gluten, String sugar, String lactose) {
		super();
		this.gluten = gluten;
		this.sugar = sugar;
		this.lactose = lactose;
	}
	public String getLactose() {
		return lactose;
	}
	public void setLactose(String lactose) {
		this.lactose = lactose;
	}

}
