package com.healthctrl.objects;

import java.sql.Date;

public class UserDetails {
	private String birthday;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private String user_id;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAge() {
		return birthday;
	}

	public void setAge(String age) {
		this.birthday = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDetails(String name,String lastName,String email,String pass, String gender,String age) {
		this.birthday = age;
		this.email = email;
		this.lastName = lastName;
		this.name = name;
		this.password = pass;
		this.gender = gender;
	 }

	public UserDetails(String first_name, String last_name, String email, String user_id) {
		this.email = email;
		this.lastName = last_name;
		this.name = first_name;
		this.user_id = user_id;
	}
}
