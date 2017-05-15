package com.healthctrl.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComponentResult {
	private float value;
	private String name;
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ComponentResult(String date, String name) {
		super();
		this.date = date;
		this.name = name;
	}
	public ComponentResult(float value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	public ComponentResult(float value) {
		super();
		this.value = value;
	}

	public ComponentResult() {
		// TODO Auto-generated constructor stub
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
