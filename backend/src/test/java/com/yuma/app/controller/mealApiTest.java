package com.yuma.app.controller;

import com.yuma.app.document.Ingredients;
import com.yuma.app.to.MealTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class mealApiTest {
	
	@Inject
	WebApplicationContext context;

	protected List<Ingredients> ingredients = new ArrayList<>();
	private UUID mealId1 = UUID.randomUUID();
	private UUID mealId2 = UUID.randomUUID();

	private String name1 = "butter chicken lasagna";
	private String description1 = "creamy and buttery";
	private String name2 = "broccoli";
	private String description2 = "broccoli";
	private boolean isAvailable = true;
	private HashSet<String> flags = new HashSet<>();
	private MealTO mealTO1, mealTO2;

	@Before
	public void setup() {
		mealTO1 = new MealTO(ingredients, mealId1, name1, description1, isAvailable, flags);
		mealTO2 = new MealTO(ingredients, mealId2, name2, description2, isAvailable, flags);

		RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context).build());

		RestAssuredMockMvc.
			given().
			contentType("application/json").
			body(mealTO1).
			when().
			post("/api/meals").
			then().
			statusCode(200);

		RestAssuredMockMvc.
			given().
			contentType("application/json").
			body(mealTO2).
			when().
			post("/api/meals").
			then().
			statusCode(200);
	}
	
	@Test
	public void getMealbyDesciptionTest(){
		RestAssuredMockMvc
			.given()
			.when()
			.get("/api/meals/{description}", description1)
			.then()
			.statusCode(200);
	}
	
	@Test
	public void getAllMealsTest(){
		RestAssuredMockMvc
			.given()
			.when()
			.get("/api/meals/all")
			.then()
			.statusCode(200);
		
	}
}
