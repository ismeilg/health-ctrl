package com.healthctrl.objects;

public class ComponentDetails {
	private String type;
	private float min;
	private float max;
	private float amax;
	private float amin;
	private float value;
	private float avalue;
	
	public float getAvalue() {
		return avalue;
	}
	public void setAvalue(float avalue) {
		this.avalue = avalue;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getAmax() {
		return amax;
	}
	public void setAmax(float amax) {
		this.amax = amax;
	}
	public float getAmin() {
		return amin;
	}
	public void setAmin(float amin) {
		this.amin = amin;
	}
	public ComponentDetails (String type, float min, float max,float amin, float amax){
		this.type = type;
		this.min = min;
		this.max = max;
		this.amax = amax;
		this.amin = amin;
		}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
}
