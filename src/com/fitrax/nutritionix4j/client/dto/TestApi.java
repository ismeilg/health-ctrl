package com.fitrax.nutritionix4j.client.dto;


import org.codehaus.jackson.map.ObjectMapper;

import com.fitrax.nutritionix4j.client.NutritionixClient;
import com.fitrax.nutritionix4j.client.NutritionixClientBuilder;
public class TestApi {


	private static ObjectMapper objMapper = new ObjectMapper();

	static NutritionixClientBuilder builder = NutritionixClientBuilder
			.builder().setBaseUrl("http://api.nutritionix.com/v1_1/")
			.setAppId("2e36540e")
			.setAppKey("2f6d868084dbd1b9a2627aa3b6800370");

	public void test(String searchItem){
		try {
			NutritionixClient proxy = builder.build();
			
			//		SearchResults response = proxy.search("cookies",null,50,200,"0:10","*");
			//		String s = response.getHits()[0].getFields().getItemId();
			//		System.out.println(response.getHits()[1].getFields().getItemId());
			//		System.out.println(" Search results max " + response.getTotal_hits());
			//		System.out.println(" Search results length "
			//				+ response.getHits().length);
			//		
			//		Item response2 = proxy.item("513fc9cb673c4fbc2600536a", null, "*");
			//
			//		System.out.println(" Response is " + response2.getItemName());
		
			SearchResults response = proxy.search(searchItem,null,200,50,"0:10","*");

			System.out.println("Search results max " + response.getTotal_hits());
				for (int i=0; i<10; i++){
			System.out.println("Item name " + response.getHits()[i].getFields().getItemName());
			Item response2 = proxy.item(response.getHits()[i].getFields().getItemId(), null, "*");
			System.out.println(" brand "+response2.getBrandName()+ " " + response2.getNfCalories() + " calories "+response2.getNfCaloriesFromFat() + " fat" );

				}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

}
