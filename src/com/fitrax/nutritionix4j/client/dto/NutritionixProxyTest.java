package com.fitrax.nutritionix4j.client.dto;

import static org.junit.Assert.assertNotNull;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fitrax.nutritionix4j.client.NutritionixClient;
import com.fitrax.nutritionix4j.client.NutritionixClientBuilder;
import com.github.tomakehurst.wiremock.WireMockServer;

public class NutritionixProxyTest
{

	private static NutritionixClient proxy;
	private static WireMockServer wireMockServer;

	private static ObjectMapper objMapper = new ObjectMapper();

	@BeforeClass
	public static void beforeClass()
	{

	 NutritionixClientBuilder builder = NutritionixClientBuilder
				.builder().setBaseUrl("http://api.nutritionix.com/v1_1/")
				.setAppId("2e36540e")
				.setAppKey("2f6d868084dbd1b9a2627aa3b6800370");
		proxy = builder.build();

	}

	@AfterClass
	public static void afterClass()
	{
		if (proxy != null)
			proxy.shutdown();

		proxy = null;

		if (wireMockServer != null)
			wireMockServer.stop();
		wireMockServer = null;

	}

	// @BeforeClass
	// public static void initializeMockUrls() throws JsonProcessingException
	// {
	// Item item = new Item();
	// item.setItemId("test_item");
	//
	// stubFor(get(urlMatching("/v1_1/item.*")).willReturn(
	// aResponse().withStatus(200)
	// .withHeader("Content-Type", "application/json")
	// .withBody(objMapper.writeValueAsString(item))));
	//
	// Brand brand = new Brand();
	// brand.setBrandId("test_brand");
	//
	// stubFor(get(urlMatching("/v1_1/brand/.*")).willReturn(
	// aResponse().withStatus(200)
	// .withHeader("Content-Type", "application/json")
	// .withBody(objMapper.writeValueAsString(brand))));
	//
	// SearchResults results = new SearchResults();
	// results.setTotal_hits(100);
	// brand.setBrandId("test_search");
	//
	// stubFor(get(urlMatching("/v1_1/search/.*")).willReturn(
	// aResponse().withStatus(200)
	// .withHeader("Content-Type", "application/json")
	// .withBody(objMapper.writeValueAsString(results))));
	//
	// }

	@Test
	public void testSearch() throws JsonProcessingException
	{
		NutritionixClientBuilder builder = NutritionixClientBuilder
				.builder().setBaseUrl("http://api.nutritionix.com/v1_1/")
				.setAppId("2e36540e")
				.setAppKey("2f6d868084dbd1b9a2627aa3b6800370");
		proxy = builder.build();
		SearchResults response = proxy.search("yogurt");

		System.out.println(" Search results max " + response.getTotal_hits());
		System.out.println(" Search results length "
				+ response.getHits().length);
		// System.out.println(" Response is " +
		// objMapper.writeValueAsString(response));
	}

	@Test
	public void testItem() throws JsonProcessingException
	{

		Item response = proxy.item("513fceb375b8dbbc2100013c", null, "*");

		assertNotNull(response);

		System.out.println(" Response is " + response);
	}

	@Test
	public void testBrand() throws Exception
	{
		Brand response = proxy.brand("513fbc1283aa2dc80c000053");

		assertNotNull(response);

		System.out.println(" Response is " + response);
	}

}
