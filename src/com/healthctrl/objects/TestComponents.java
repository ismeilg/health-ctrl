package com.healthctrl.objects;

public class TestComponents {
	private float glucose;
	private float potassium;
	private float sodium;
	private float albumin;
	private float calcium;
	private float triglycerides;
	private float hdl;
	private float ldl;
	private float cholestrol;
	private float b12;
	private float protein_total;
	private float bmi;
	public float getProtein_total() {
		return protein_total;
	}
	public void setProtein_total(float protein_total) {
		this.protein_total = protein_total;
	}
	public float getBmi() {
		return bmi;
	}
	public void setBmi(float bmi) {
		this.bmi = bmi;
	}
	public float getGlucose() {
		return glucose;
	}
	public void setGlucose(float glucose) {
		this.glucose = glucose;
	}
	public float getPotassium() {
		return potassium;
	}
	public void setPotassium(float potassium) {
		this.potassium = potassium;
	}
	public float getSodium() {
		return sodium;
	}
	public void setSodium(float sodium) {
		this.sodium = sodium;
	}
	public float getAlbumin() {
		return albumin;
	}
	public void setAlbumin(float albumin) {
		this.albumin = albumin;
	}
	public float getCalcium() {
		return calcium;
	}
	public void setCalcium(float calcium) {
		this.calcium = calcium;
	}
	public float getTriglycerides() {
		return triglycerides;
	}
	public void setTriglycerides(float triglycerides) {
		this.triglycerides = triglycerides;
	}
	public float getHdl() {
		return hdl;
	}
	public void setHdl(float hdl) {
		this.hdl = hdl;
	}
	public float getLdl() {
		return ldl;
	}
	public void setLdl(float ldl) {
		this.ldl = ldl;
	}
	public float getCholestrol() {
		return cholestrol;
	}
	public void setCholestrol(float cholestrol) {
		this.cholestrol = cholestrol;
	}
	public float getB12() {
		return b12;
	}
	public void setB12(float b12) {
		this.b12 = b12;
	}
	public float getProtein() {
		return protein_total;
	}
	public void setProtein(float protein) {
		this.protein_total = protein;
	}
	public TestComponents (float glucose,	float sodium,float potassium, float albumin,float calcium, float triglycerides,	float hdl, float ldl,float cholestrol,float b12,float protein_total, float bmi){
		this.glucose = glucose;
		this.sodium = sodium;
		this.potassium = potassium;
		this.albumin = albumin;
		this.calcium = calcium;
		this.triglycerides = triglycerides;
		this.hdl = hdl;
		this.ldl = ldl;
		this.cholestrol = cholestrol;
		this.b12 = b12;
		this.protein_total = protein_total;
		this.bmi = bmi;
	}
	public TestComponents(float parseInt, float parseInt2, float f, float parseInt3, float g, float parseInt4, float h,
			float parseInt5) {
		// TODO Auto-generated constructor stub
	}
}
