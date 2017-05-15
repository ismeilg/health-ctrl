package com.healthctrl.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;
import javax.sql.DataSource;

import com.healthctrl.objects.ComponentDetails;
import com.healthctrl.objects.ComponentResult;
import com.healthctrl.objects.TestComponents;
import com.healthctrl.objects.UserMoreDetails;
import com.sun.jmx.snmp.Timestamp;
import com.sun.jna.platform.win32.Sspi.TimeStamp;

import java.sql.PreparedStatement;

public class ComponentsUtils {
	private DataSource dataSource;
	public int resultsSize = 0;
	public ComponentsUtils(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List<ComponentDetails> getComponentDetails() throws Exception{
		List <ComponentDetails> Components = new ArrayList<>();
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;

		String sql = "select * from blood_component"; 
		try{
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();                    
			//execute sql query                                  
			myRs = myStt.executeQuery(sql);                      
			//process the result set  
			while (myRs.next()){ 
				String name = myRs.getString("type");
				Float min = (float)myRs.getFloat("min");
				Float max = (float)myRs.getFloat("max");
				Float amin = (float)myRs.getFloat("amin");
				Float amax = (float)myRs.getFloat("amax");
				ComponentDetails temp = new ComponentDetails(name, min, max,amin,amax);
				Components.add(temp);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			DbUtils.close(myConn,myStt, myRs);
		}
		return Components;
	}

	public List<ComponentResult> addTestResult(TestComponents result,String user_id, UserMoreDetails details)throws Exception {
		Connection myConn = null;
		PreparedStatement myStt = null; 
		List <ComponentResult> results = new ArrayList<>();

		try{
			//get connection
			try{
				//get connection
				myConn = dataSource.getConnection();
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			//create sql statement
			String sql = "insert into blood_test "
					+ "(albumin, glucose, potassium, calcium, triglycerides, hdl, ldl,"
					+ " cholesterol, sodium, b12, protein_total,user_id,bmi,weight) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
			myStt = myConn.prepareStatement(sql);
			//set the param values for test result
			myStt.setFloat(1, result.getAlbumin());
			myStt.setFloat(2, result.getGlucose());
			myStt.setFloat(3, result.getPotassium());
			myStt.setFloat(4, result.getCalcium());
			myStt.setFloat(5, result.getTriglycerides());
			myStt.setFloat(6, result.getHdl());
			myStt.setFloat(7, result.getLdl());
			myStt.setFloat(8, result.getCholestrol());
			myStt.setFloat(9, result.getSodium());
			myStt.setFloat(10, result.getB12());
			myStt.setFloat(11, result.getProtein());
			myStt.setString(12,user_id);
			myStt.setFloat(13,result.getBmi());
			myStt.setFloat(14,details.getWeight());
			//excute query
			myStt.execute();

			String sql2 = "insert into more_details "
					+ "(sugar,lactose,gluten,vegan,vegiterian,height,weight,user_id) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement myStt2 = null; 
			myStt2 = myConn.prepareStatement(sql2);
			myStt2.setString(1, details.getSugar());
			myStt2.setString(2, details.getLactose());
			myStt2.setString(3, details.getGluten());
			myStt2.setString(4, details.getVegan());
			myStt2.setString(5, details.getVegiterian());
			myStt2.setInt(6, details.getHeight());
			myStt2.setFloat(7,details.getWeight());
			myStt2.setString(8,user_id);
			myStt2.execute();
			
			ComponentResult res = null;
			
		
			res = new ComponentResult(result.getGlucose(), "glucose");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getPotassium(), "potassium");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getSodium(), "sodium");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getAlbumin(), "albumin");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getCalcium(), "calcium");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getTriglycerides(), "triglycerides");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult( result.getHdl(), "hdl");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getLdl(), "ldl");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getCholestrol(), "cholesterol");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getB12(), "b12");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getProtein(), "protein_total");
			if (res.getValue()!=0)
			results.add(res);

			res = new ComponentResult(result.getBmi(), "bmi");
			if (res.getValue()!=0)
			results.add(res);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return results;
	}
	public List<ComponentResult> getUserLastTestResult(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		TestComponents testComp = null;
		ComponentResult res = null;
		List <ComponentResult> results = new ArrayList<>();

		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from blood_test where blood_test.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from blood_test)", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {
				//proccess result set
				float glucose = myRs.getFloat("glucose");
				res = new ComponentResult(glucose, "glucose");
				results.add(res);

				float potassium = myRs.getFloat("potassium");
				res = new ComponentResult(potassium, "potassium");
				results.add(res);

				float sodium = myRs.getFloat("sodium");
				res = new ComponentResult(sodium, "sodium");
				results.add(res);

				float albumin = myRs.getFloat("albumin");
				res = new ComponentResult(albumin, "albumin");
				results.add(res);

				float calcium = myRs.getFloat("calcium");
				res = new ComponentResult(calcium, "calcium");
				results.add(res);

				float triglycerides = myRs.getFloat("triglycerides");
				res = new ComponentResult(triglycerides, "triglycerides");
				results.add(res);

				float hdl = myRs.getFloat("hdl");
				res = new ComponentResult(hdl, "hdl");
				results.add(res);

				float ldl = myRs.getFloat("ldl");
				res = new ComponentResult(ldl, "ldl");
				results.add(res);

				float cholestrol = myRs.getFloat("cholesterol");
				res = new ComponentResult(cholestrol, "cholesterol");
				results.add(res);

				float b12 = myRs.getFloat("b12");
				res = new ComponentResult(b12, "b12");
				results.add(res);

				float protein_total = myRs.getFloat("protein_total");
				res = new ComponentResult(protein_total, "protein_total");
				results.add(res);

				float bmi = myRs.getFloat("bmi");
				res = new ComponentResult(bmi, "bmi");
				results.add(res);	
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return results;
	}

	public List<List<ComponentResult>> getUserAllTestResults(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		ComponentResult result = null;
		List <ComponentResult> results = new ArrayList<>();
		List<List<ComponentResult>> allResults = new ArrayList<>();
		

		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from blood_test where blood_test.user_id LIKE '%s' ORDER BY result_date", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			while(myRs.next()) {
				results = new ArrayList<>();
				resultsSize++;
				//proccess result set	
				java.sql.Timestamp timeStamp = myRs.getTimestamp("result_date");
				String [] date = timeStamp.toString().split(" ");
				result = new ComponentResult(date[0],"date");
				results.add(result);
				
				float glucose = myRs.getFloat("glucose");
				result = new ComponentResult(glucose, "glucose");
				results.add(result);

				float potassium = myRs.getFloat("potassium");
				result = new ComponentResult(potassium, "potassium");
				results.add(result);

				float sodium = myRs.getFloat("sodium");
				result = new ComponentResult(sodium, "sodium");
				results.add(result);

				float albumin = myRs.getFloat("albumin");
				result = new ComponentResult(albumin, "albumin");
				results.add(result);

				float calcium = myRs.getFloat("calcium");
				result = new ComponentResult(calcium, "calcium");
				results.add(result);

				float triglycerides = myRs.getFloat("triglycerides");
				result = new ComponentResult(triglycerides, "triglycerides");
				results.add(result);

				float hdl = myRs.getFloat("hdl");
				result = new ComponentResult(hdl, "hdl");
				results.add(result);

				float ldl = myRs.getFloat("ldl");
				result = new ComponentResult(ldl, "ldl");
				results.add(result);

				float cholestrol = myRs.getFloat("cholesterol");
				result = new ComponentResult(cholestrol, "cholesterol");
				results.add(result);

				float b12 = myRs.getFloat("b12");
				result = new ComponentResult(b12, "b12");
				results.add(result);

				float protein_total = myRs.getFloat("protein_total");
				result = new ComponentResult(protein_total, "protein_total");
				results.add(result);	
				allResults.add(results);
			}

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			//clean up jdbc object	
			DbUtils.close(myConn,myStt, null);
		}
		return allResults;
	}
	public String getBmiImage(float bmi) {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		String img = "", category="";

		if (bmi<18.5){
			category = "underweight";
		}
		else if (bmi>18.5 && bmi<24.9){
			category = "normal";
		}
		else if (bmi>25 && bmi<29.9){
			category = "overweight";
		}
		else if (bmi>30){
			category ="obese";
		}		
		try{
			//get connection
			myConn = dataSource.getConnection();
			//create sql statement
			String sql = String.format("select * from bmi_static where bmi_static.category LIKE '%s'", category);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			while(myRs.next()) {
				img = myRs.getString("pic");
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return img;
	}
	public String []  getWeightBmi(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		String [] bmiAndWeight = new String[2];
		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from blood_test where blood_test.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from blood_test)", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {
				//proccess result set
				float bmi = myRs.getFloat("bmi");
				bmi = myRs.getFloat("bmi");
				DecimalFormat df = new DecimalFormat("#.0");
				String dif = df.format(bmi);
				bmi = Float.parseFloat(dif);
				bmiAndWeight[0] = String.valueOf(bmi);

				float weight = myRs.getFloat("weight");
				bmiAndWeight[1] = String.valueOf(weight);
			}
		}catch(Exception e){
			throw e;
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return bmiAndWeight;
	}


	//	public HashMap <String, Integer> getBloodTestDetails(List<ComponentDetails> componentsDetails,List<BloodTest> bloodTest ) throws Exception{
	//		//		int [] componenentRate = new int[numbberOfComponenets]; if rate in othe way use it instaed of hash map
	//		//		String [] componentName = new String[numbberOfComponenets];
	//		//		int  i =0;
	//		HashMap<String, Integer> rate = new HashMap<>();
	//		for (BloodTest temp : bloodTest) {
	//			rate.put(temp.getComponents().getName(), temp.getValue());
	//			}
	//		return rate;
	//	}
	//	public HashMap <String, Integer> getBloodTestDetails(List<Component> componentsDetails,List<BloodTest> bloodTest ) throws Exception{
	//		//		int [] componenentRate = new int[numbberOfComponenets]; if rate in othe way use it instaed of hash map
	//		//		String [] componentName = new String[numbberOfComponenets];
	//		//		int  i =0;
	//		HashMap<String, Integer> rate = new HashMap<>();
	//		for (BloodTest temp : bloodTest) {
	//			for (Component temp2 : componentsDetails)
	//			{
	//				if (temp.getValue() > temp2.getMax()) {
	//					rate.put(temp2.getName(),5); // how will be the scale
	//				}
	//				else  if (temp.getValue() < temp2.getMin()) {
	//					rate.put(temp2.getName(),0); // how will be the scale
	//				}
	//				else  if (temp.getValue() == temp2.getNormal()) {
	//					rate.put(temp2.getName(),3); // how will be the scale
	//				}
	//				else  if (temp.getValue() > temp2.getNormal()&& temp2.getNormal() <  temp2.getMax()) {
	//					rate.put(temp2.getName(),4); // how will be the scale
	//				}
	//				else  if (temp.getValue() < temp2.getNormal()&& temp2.getNormal() > temp2.getMin()) {
	//					rate.put(temp2.getName(),2); // how will be the scale
	//				}	 
	//			}
	//		}
	//		return rate;
	//	}
	public String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("dd MM yyyy");
	    return format.format(date);
	}
}

