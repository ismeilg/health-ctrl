package com.healthctrl.utils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.healthctrl.objects.ComponentResult;
import com.healthctrl.objects.UserDetails;

public class DbUtils {
	private DataSource dataSource;

	public DbUtils(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ComponentResult> getBloodTestDetails(String user_id) throws Exception{
		List <ComponentResult> componentsResult = new ArrayList<>();
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;

		String sql = String.format("select * from bloodResult where id=%id", user_id);   
		try{
			myStt = myConn.createStatement();                    

			//execute sql query                                  
			myRs = myStt.executeQuery(sql);                      
			//process the result set  
			float glucose  =  (float)myRs.getInt("glucose");
			ComponentResult componentResult = new ComponentResult(glucose, "glucose");
			componentsResult.add(componentResult);
			float sodium  =  (float)myRs.getInt("sodium");
			componentResult = new ComponentResult(sodium, "sodium");
			componentsResult.add(componentResult);
			//all rest of components	
		} 
	finally {
		close(myConn,myStt, myRs);
	}
	return componentsResult;
}
public List<UserDetails> getUserDetails(String user_id) throws Exception{
	List <UserDetails> userDetails = new ArrayList<>();
	Connection myConn = null;
	Statement myStt = null; 
	ResultSet myRs = null;
	try {                                                    
		myConn = dataSource.getConnection();                 
		//create sql statement                               
		String sql = String.format("select first_name,last_name, from userDetail where id =%s order by user_id", user_id);
		myStt = myConn.createStatement();                    
		//execute sql query                                  
		myRs = myStt.executeQuery(sql);                      
		//process the result set  
		String firstName = myRs.getString("first_name");
		String lastName = myRs.getString("last_name");
		String email = myRs.getString("email");

		UserDetails temp = new UserDetails(firstName, lastName, email,user_id);
		userDetails.add(temp);
	}  
	finally {
		close(myConn,myStt, myRs);
	}
	return userDetails;
}
public String findUserByMail (String email) throws SQLException{
	String user_id=""; //what if it returns nothing
	Connection myConn = null;
	Statement myStt = null; 
	ResultSet myRs = null;

	try {                                                    
		myConn = dataSource.getConnection();                 
		//create sql statement                               
		String sql = String.format("select user_id, from userRegistration where email =%s", email);
		myStt = myConn.createStatement();                    
		//execute sql query                                  
		myRs = myStt.executeQuery(sql);                      
		//process the result set  
		while (myRs.next()){ 
			user_id=myRs.getString(email);
		}
	}
	catch (Exception e){
		e.getMessage();
	}
	finally {
		close(myConn,myStt, myRs);
	}
	return user_id;
}
public static void close(Connection myConn, Statement myStt, ResultSet myRs) {
	try {
		if(myConn!=null){
			myConn.close();
		}
		if (myRs!=null){
			myRs.close();
		}
		if (myStt!=null){
			myStt.close();
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
}
}
