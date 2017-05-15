package com.healthctrl.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import com.healthctrl.objects.UserDetails;

public class UserDetailsUtils {
	private DataSource dataSource;

	public UserDetailsUtils(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean addUser(UserDetails user) throws Exception {
		Connection myConn = null;
		PreparedStatement myStt = null; 
		boolean success = true;
		try{
			//get connection
			myConn = dataSource.getConnection();
			if (!isExist(user.getEmail())){
				//create sql statement
				String sql = "insert into registration "
						+ "(email, password, first_name, last_name, gender, age) "
						+ "values (?, ?, ?, ?, ?, ?)";
				myStt = myConn.prepareStatement(sql);
				//set the param values for test result
				myStt.setString(1, user.getEmail());
				myStt.setString(2, user.getPassword());
				myStt.setString(3, user.getName());
				myStt.setString(4, user.getLastName());
				myStt.setString(5, user.getGender());
				myStt.setString(6, "5");
				//excute query
				myStt.execute();
			}
			else {
				success = false;
			}
		}
		catch (SQLException e){
			success = false;
			System.out.println(e.getMessage());
		}
		finally{
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return success;
	}
//	public boolean addFbUser(UserDetails user) throws Exception {
//		Connection myConn = null;
//		PreparedStatement myStt = null; 
//		boolean success = false;
//		try{
//			//get connection
//			//	Connection myco = DriverManager.getConnection("jdbc:mysql://mtapanel.mtacloud.co.il:2083/shirra_healthCtrl","shirra","BS[KRr6dPGRe");
//			myConn = dataSource.getConnection();
//			if (!isExist(user.getEmail())){
//				//create sql statement
//				String sql = "insert into registration "
//						+ "(email,first_name,last_name,gender,age) "
//						+ "values (?, ?, ?, ?, ?)";
//				myStt = myConn.prepareStatement(sql);
//				//set the param values for test result
//				myStt.setString(1, user.getEmail());
//				myStt.setString(2, user.getName());
//				myStt.setString(3, user.getLastName());
//				myStt.setString(4, user.getGender());
//				myStt.setString(5, user.getAge());
//				//excute query
//				myStt.execute();
//				success = true;
//			}
//		}
//		catch (SQLException e){
//			System.out.println(e.getMessage());
//		}
//		finally{
//			//clean up jdbc object	
//			DbUtils.close(myConn,myStt, null);
//		}
//		return success;
//	}
	public UserDetails getUserDetails(String i_email) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		UserDetails user = null;

		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from registration where registration.email LIKE '%s'", i_email);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {
				//proccess result set
				String first_name = myRs.getString("first_name");
				String last_name = myRs.getString("last_name");
				String user_id = myRs.getString("user_id");
				user = new UserDetails(first_name, last_name, i_email,user_id);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return user;
	}

	public boolean authenticateUser(String i_email, String i_password) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		boolean authenicated = false;

		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from registration where registration.email LIKE '%s'", i_email);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			//proccess result set 
			if(myRs.next()) {
				String password = myRs.getString("password");
				if (password.equals(i_password)){	
					authenicated = true;
				}
				else {
					authenicated = false;
				}
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			authenicated = false;
		}
		finally {
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return authenicated;
	}
	public boolean isExist (String email){
		ResultSet myRs = null;
		Statement myStt = null;
		Connection myConn = null;
		boolean isExist = true;

		try{
			myConn = dataSource.getConnection();
			String sql =  String.format("select * from registration where registration.email LIKE '%s'", email);
			myStt = myConn.createStatement();
			//excute query
			myRs = myStt.executeQuery(sql);
			//proccess result set
			email = myRs.getString("email");
		}
		catch (Exception e){
			isExist = false;	
		}
		if (!isExist || email.equals("")){
			isExist = false;
		}
		return isExist;
	}
}
