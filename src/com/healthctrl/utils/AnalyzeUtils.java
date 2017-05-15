package com.healthctrl.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import com.fitrax.nutritionix4j.client.NutritionixClient;
import com.fitrax.nutritionix4j.client.NutritionixClientBuilder;
import com.fitrax.nutritionix4j.client.dto.Item;
import com.fitrax.nutritionix4j.client.dto.SearchResults;
import com.healthctrl.objects.ComponentDetails;
import com.healthctrl.objects.ComponentResult;

public class AnalyzeUtils {
	private DataSource dataSource;
	private ComponentsUtils componentUtils;
	List<String> recommendationToeat = new ArrayList<>();
	List<String> recommendationNotToeat = new ArrayList<>();
	String sugar = "";
	double bmi=0;
	int age=0;
	static NutritionixClientBuilder builder = NutritionixClientBuilder.builder()
			.setBaseUrl("http://api.nutritionix.com/v1_1/").setAppId("2e36540e")
			.setAppKey("2f6d868084dbd1b9a2627aa3b6800370");

	public AnalyzeUtils(DataSource dataSource) {
		this.dataSource = dataSource;
		componentUtils = new ComponentsUtils(dataSource);
	}

	public String [] getRecommendation(String user_id,boolean AfterSport) throws Exception {
		/// get test components by ID
		List<ComponentResult> lastResults = componentUtils.getUserLastTestResult(user_id);
		// add test components to request
		// request.setAttribute("results", results);
		List<ComponentDetails> components = componentUtils.getComponentDetails();
		/// from list to array
		ComponentDetails[] comps = components.toArray(new ComponentDetails[components.size()]);
		ComponentResult[] res = lastResults.toArray(new ComponentResult[lastResults.size()]);
		// rate test result
		List<String> ubnormalComps = new ArrayList<>();

		for (int i = 0; i < res.length - 1; i++) {
			for (int j = 0; j < comps.length; j++) {
				if (res[i].getName().equals(comps[j].getType())) {
					comps[j].setValue(res[i].getValue());
					if (comps[j].getMax() * 0.9 < comps[j].getValue()
							|| comps[j].getMin() * 0.1 > comps[j].getValue()) {
						ubnormalComps.add(comps[j].getType());
					}
					break;
				}
			}
		}
		///get all list of foods by user prefrence
		List<List<String>> categoriesOfFood = getListOfFood(user_id,AfterSport);
		List<String> foodRecommendation = getRecommendation(categoriesOfFood, ubnormalComps);

		String [] foodRecommendationArr = foodRecommendation.toArray(new String[foodRecommendation.size()]);
		///random before insert to DB
		foodRecommendationArr = insertRecommendationToDb(foodRecommendationArr,user_id);

		return foodRecommendationArr;
	}

	private String [] insertRecommendationToDb(String[] foodRecommendationArr, String user_id) {
		HashSet<String> used = new HashSet<String>();
		String [] foodRandom = null;
		String randomFood = "";
		try{
			if (foodRecommendationArr.length < 6){
				foodRandom = new String[foodRecommendationArr.length];
				for (int i=0; i<foodRecommendationArr.length; i++){
					foodRandom[i] = foodRecommendationArr[i];
					randomFood += foodRecommendationArr[i];
				}

			}
			else if (foodRecommendationArr.length > 6){
				foodRandom = new String[8];

				for (int i = 0; i < 8; i++) {
					String add = foodRecommendationArr[(int) (Math.random() *foodRecommendationArr.length)]; //this is the int we are adding
					while (used.contains(add)) { //while we have already used the number
						add = foodRecommendationArr[(int) (Math.random() * foodRecommendationArr.length)]; //generate a new one because it's already used
					}
					//by this time, add will be unique
					used.add(add);
					foodRandom[i] = add;
					randomFood += add;
					randomFood += "&";
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		/////////insert recommendation to DB
		Connection myConn = null;
		PreparedStatement myStt = null;

		// get connection
		try {
			// get connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "insert into recommendation (recommendation,user_id)"
					+ "values (?, ?)";
			myStt = myConn.prepareStatement(sql);
			// set the param values for test result
			myStt.setString(1, randomFood);
			myStt.setString(2, user_id);
			myStt.execute();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, null);
		}
		return foodRandom;
	}


	private List<String> getRecommendation(List<List<String>> categoriesOfFood, List<String> ubnormalComps) throws Exception {
		List<String> foodRecommended = new ArrayList<>();
		List<String> foodNotRecommended = new ArrayList<>();
		String foodProp="";
		HashMap<String, Double> normalValues = getNormalValue(categoriesOfFood);
		for (List<String> allCategories : categoriesOfFood) {
			List<String> listOfFood = allCategories;
			for (String item : listOfFood) {
				HashMap<String, Object> itemProperties = getNutritionValues(item);
				for (Map.Entry<String, Object> foodProperties : itemProperties.entrySet()) {
					///check if propertie is abnormal
					if (foodProperties.getKey().equals("sugar") || foodProperties.getKey().equals("calories")){
						foodProp = "glucose";
					}
					else if (foodProperties.getKey().equals("fat")){
						foodProp = "fat";
					}
					else {
						foodProp = foodProperties.getKey();
					}
					if (checkIfPropertieIsAbnormal(foodProp, ubnormalComps)) {
						if (foodProperties.getValue().toString().contains(".")) {
							double value = (double) foodProperties.getValue();
							if (value < searchNoramlValue(foodProperties.getKey(), normalValues)) {
								foodRecommended.add(item);
							}
						} else {
							int value = (int) foodProperties.getValue();
							if (value < searchNoramlValue(foodProperties.getKey(), normalValues)) {
								foodRecommended.add(item);
							} else {
								foodNotRecommended.add(item);
							}
						}
					} 
				}
			}
		}
		return foodRecommended;
	}

	private double searchNoramlValue(String key, HashMap<String, Double> normalValue) {
		for (Map.Entry<String, Double> prop : normalValue.entrySet()) {
			if (prop.getKey().contains(key)) {
				return prop.getValue();
			}
		}
		return 0;
	}

	private HashMap<String, Double> getNormalValue(List<List<String>> categoriesOfFood) {
		HashMap<String, Double> normalValue = new HashMap<>();
		normalValue.put("calories", 239.60714285714286d);
		normalValue.put("sodium", 174.1614285714286d);
		normalValue.put("cholesterol", 48.035714285714285d);
		normalValue.put("sugar", 2.4778571428571428d);
		normalValue.put("calcium", 7.004642857142857d);
		normalValue.put("fat", 11.289642857142857d);	
		return normalValue;
	}

	public String [] getLastRecommendation(String user_id){
		Connection myConn = null;
		Statement myStt = null;
		ResultSet myRs = null;
		String recommendationStr="";
		String sql = String.format("select * from recommendation where recommendation.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from recommendation)", user_id);

		try {
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql);
			// process the result set
			if (myRs.next()) {
				recommendationStr = myRs.getString("recommendation");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		String [] lastRecommendation = recommendationStr.split("&");

		return lastRecommendation;
	}
	public String getLastSportRecommendation(String user_id){
		Connection myConn = null;
		Statement myStt = null;
		ResultSet myRs = null;
		String recommendationStr="";
		String sql = String.format("select * from sport_recommendation where sport_recommendation.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from sport_recommendation)", user_id);

		try {
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql);
			// process the result set
			if (myRs.next()) {
				recommendationStr = myRs.getString("recommendation");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return recommendationStr;
	}
	private boolean checkIfPropertieIsAbnormal(String propertie, List<String> ubnormalComps) {
		boolean isExist = false;

		if (propertie.equals("fat") || propertie.equals("calories")){
			if (bmi>25){
				isExist = true;
			}
		}
		else {
			for (String ubnormal : ubnormalComps) {
				if (ubnormal.equals(propertie)) {
					isExist = true;
					break;
				}
			}
		}
		return isExist;
	}

	private List<List<String>> getListOfFood(String user_id, boolean afterSport) {
		List<List<String>> groupOfFood = new ArrayList<>();
		Connection myConn = null;
		Statement myStt = null;
		ResultSet myRs = null;
		String  vegan1 = "", vegiterian = "", lactose = "";
		String sql = String.format("select * from more_details where more_details.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from more_details)", user_id);

		try {
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql);
			// process the result set
			if (myRs.next()) {
				vegan1 = myRs.getString("vegan");
				vegiterian = myRs.getString("vegiterian");
				lactose = myRs.getString("lactose");
				sugar = myRs.getString("sugar");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<String> carbs = new ArrayList<>();
		List<String> meat = new ArrayList<>();
		List<String> protein = new ArrayList<>();
		List<String> fruits = new ArrayList<>();
		List<String> legumes = new ArrayList<>();
		List<String> vegan = new ArrayList<>();
		List<String> fat = new ArrayList<>();
		String sql2 = "select * from nutrition";
		try {
			// myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql2);
			// process the result set
			while (myRs.next()) {
				carbs.add(myRs.getString("carbs"));
				meat.add(myRs.getString("meat"));
				protein.add(myRs.getString("protein"));
				fruits.add(myRs.getString("fruits"));
				legumes.add(myRs.getString("legumes"));
				fat.add(myRs.getString("fat"));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		if (vegan1.equals("0") && !vegiterian.equals("0")) {
			groupOfFood.add(meat);
		}
		if (vegan1.equals("0") && !lactose.equals("0")) {
			groupOfFood.add(protein);
		}
		if (sugar.equals("0") && !afterSport) {
			groupOfFood.add(fruits);
		}

		groupOfFood.add(fat);
		groupOfFood.add(carbs);
		groupOfFood.add(legumes);
		groupOfFood.add(vegan);

		return groupOfFood;
	}
	
	public List<String> getUnrecommendedListOfFood(String user_id) {
		List<List<String>> unrecommendedGroupOfFood = new ArrayList<>();
		Connection myConn = null;
		Statement myStt = null;
		ResultSet myRs = null;
		String  vegan1 = "", vegiterian = "", lactose = "";
		String sql = String.format("select * from more_details where more_details.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from more_details)", user_id);

		try {
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql);
			// process the result set
			if (myRs.next()) {
				vegan1 = myRs.getString("vegan");
				vegiterian = myRs.getString("vegiterian");
				lactose = myRs.getString("lactose");
				sugar = myRs.getString("sugar");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<String> carbs = new ArrayList<>();
		List<String> meat = new ArrayList<>();
		List<String> protein = new ArrayList<>();
		List<String> fat = new ArrayList<>();
		String sql2 = "select * from not_recommended";
		try {
			// myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql2);
			// process the result set
			while (myRs.next()) {
				carbs.add(myRs.getString("carbs"));
				meat.add(myRs.getString("meat"));
				protein.add(myRs.getString("protein"));
				fat.add(myRs.getString("fat"));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		if (vegan1.equals("0") && !vegiterian.equals("0")) {
			unrecommendedGroupOfFood.add(meat);
		}
		if (vegan1.equals("0") && !lactose.equals("0")) {
			unrecommendedGroupOfFood.add(protein);
		}

		unrecommendedGroupOfFood.add(fat);
		unrecommendedGroupOfFood.add(carbs);
		
		List <String> allUnrecommendedGroupOfFood = new ArrayList<>();
		for (List<String> temp: unrecommendedGroupOfFood){
			for (String tmp: temp){
				allUnrecommendedGroupOfFood.add(tmp);
			}
		}
		return allUnrecommendedGroupOfFood;
	}

	private HashMap<String, Object> getNutritionValues(String food_name) throws Exception {
		HashMap<String, Object> properties = new HashMap<String, Object>();

		Connection myConn = null;
		Statement myStt = null;
		ResultSet myRs = null;

		if (food_name.contains(" ")){
			food_name = food_name.replace(" ", "_");
		}
		String sql = String.format("select * from food_values where food_values.food_name LIKE '%s'", food_name);

		try {
			myConn = dataSource.getConnection();
			myStt = myConn.createStatement();
			// execute sql query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {
				properties.put("calories", myRs.getDouble("calories"));
				properties.put("calcium", myRs.getDouble("calcium"));
				properties.put("cholesterol", myRs.getDouble("cholesterol"));

				properties.put("sodium", myRs.getDouble("sodium"));
				properties.put("sugar", myRs.getDouble("sugar"));
				properties.put("fat", myRs.getDouble("fat"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}


		return properties;

	}

	private HashMap<String, Object> searchItem(String searchItem) {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		try {
			NutritionixClient proxy = builder.build();
			SearchResults response = proxy.search(searchItem);
			String itemId = response.getHits()[0].getFields().getItemId();
			Item itemDetails = proxy.item(itemId, null, "*");

			properties.put("calories", itemDetails.getNfCalories());
			properties.put("calcium", itemDetails.getNfCalciumDv());
			properties.put("cholesterol", itemDetails.getNfCholesterol());

			properties.put("sodium", itemDetails.getNfSodium());
			properties.put("sugar", itemDetails.getNfSugars());
			properties.put("fat", itemDetails.getNfTotalFat());

			insertValues(searchItem, itemDetails.getNfSugars(), itemDetails.getNfCalciumDv(),
					itemDetails.getNfCholesterol(), itemDetails.getNfCalories(), itemDetails.getNfTotalFat(),
					itemDetails.getNfSodium());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return properties;
	}

	private void insertValues(String name, double sugar, double calcium, int cholestrol, int calories, double fat,
			double sodium) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStt = null;
		if (name.contains(" ")){
			name = name.replace(" ", "_");
		}
		// get connection
		try {
			// get connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "insert into food_values (food_name, sugar, calcium, cholesterol, calories, fat, sodium)"
					+ "values (?, ?, ?, ?, ?, ?,?)";
			myStt = myConn.prepareStatement(sql);
			// set the param values for test result
			myStt.setString(1, name);
			myStt.setDouble(2, sugar);
			myStt.setDouble(3, calcium);
			myStt.setDouble(4, cholestrol);
			myStt.setDouble(5, calories);
			myStt.setDouble(6, fat);
			myStt.setDouble(7, sodium);

			myStt.execute();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, null);
		}
	}
	public String getSportRecommendation(String user_id) throws Exception{
		String sportRecommendation = "";
		String level="";
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		bmi = getUserBmi(user_id);
		age = getUserAge(user_id);

		if (bmi<25 && age < 40 && age > 30){
			level = "medium_level";
		}
		else if (bmi>25 && age < 40 && age > 30){
			level = "low_level";
		}
		else if (bmi<25 && age < 30){
			level = "high_level";
		}
		else {
			level = "low_level";
		}
		Random rn = new Random();
		String serial_num = Integer.toString(rn.nextInt(2 - 1 + 1) + 1);

		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from sport_static where serial_num LIKE '%s'",serial_num);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {

				sportRecommendation = myRs.getString(level);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		///insert recom to DB
		try{
			PreparedStatement myStt2 = null;
			String sql = "insert into sport_recommendation (recommendation,user_id)"
					+ "values (?, ?)";
			myStt2 = myConn.prepareStatement(sql);
			// set the param values for test result
			myStt2.setString(1, sportRecommendation);
			myStt2.setString(2, user_id);
			myStt2.execute();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}

		return sportRecommendation;
	}
	private double getUserBmi(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;

		float bmi=0;
		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from blood_test where blood_test.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from blood_test)", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {

				bmi = myRs.getFloat("bmi");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return bmi;
	}
	public int getrecommendedHeartRate(String user_id) throws Exception{
		bmi = getUserBmi(user_id);
		age = getUserAge(user_id);

		int heartRate=0;

		if (bmi<14){
			heartRate = (int) ((215-age)*0.6);
		}
		else if (bmi>18.5 && bmi<24.9){
			heartRate = (int) ((215-age)*0.65);
		}
		else if (bmi>25 && bmi<29.9){
			heartRate = (int) ((215-age)*0.75);
		}
		else if (bmi>30){
			heartRate = (int) ((215-age)*0.75);
		}			
		return heartRate;	
	}

	public String [] getUnrecommendedFood() throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		String unRecommendationStr="";

		try {
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = "select * from static_avoid";
			myStt = myConn.createStatement();
			myRs = myStt.executeQuery(sql);

			if (myRs.next()) {
				unRecommendationStr = myRs.getString("avoid");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		String [] unRecommendation = unRecommendationStr.split("&");

		return unRecommendation;
	}
	public String [] getNotBeforeSport() throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		String unRecommendationStr="";

		try {
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = "select * from sport_food";
			myStt = myConn.createStatement();
			myRs = myStt.executeQuery(sql);

			if (myRs.next()) {
				unRecommendationStr = myRs.getString("not_before");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		String [] unRecommendation = unRecommendationStr.split("&");

		return unRecommendation;
	}
	public String [] getBeforeSport(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		String recommendationStr="";

		try {
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = "select * from sport_food";
			myStt = myConn.createStatement();
			myRs = myStt.executeQuery(sql);

			if (myRs.next()) {
				if (sugar.equals("1") || getGlucoseRes(user_id)>100){
					recommendationStr = myRs.getString("without_sugar");
				}
				else {
					recommendationStr = myRs.getString("before");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		String [] recommendation = recommendationStr.split("&");

		return recommendation;
	}
	private int getUserAge(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		int age = 0;
		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from registration where registration.user_id LIKE '%s'", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {

				age =Integer.parseInt(myRs.getString("age"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return age;
	}
	private float getGlucoseRes(String user_id) throws Exception {
		Connection myConn = null;
		Statement myStt = null; 
		ResultSet myRs = null;
		float glucose = 0;
		try{
			//get connection
			myConn = dataSource.getConnection();

			//create sql statement
			String sql = String.format("select * from blood_test where blood_test.user_id LIKE '%s' AND serial_num=(SELECT max(serial_num) from blood_test)", user_id);
			myStt = myConn.createStatement();

			//excute query
			myRs = myStt.executeQuery(sql);
			if(myRs.next()) {

				glucose =myRs.getFloat("glucose");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			// clean up jdbc object
			DbUtils.close(myConn, myStt, myRs);
		}
		return glucose;
	}
}
