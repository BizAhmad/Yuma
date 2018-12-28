package com.yuma.app.service;
import com.querydsl.core.types.Predicate;
import com.yuma.app.document.Meal;
import com.yuma.app.document.QMeal;
import com.yuma.app.repository.MealRepository;
import com.yuma.app.to.MealTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MealServiceTest {

	@MockBean
	private MealRepository mealRepository;

	@Mock
	private ConversionService conversionService;

	@Mock
	private QMeal qMeal;

	@Autowired
	private MealService mealService;

	private Meal meal1;
	private Meal meal2;
	private Meal meal3;
	private MealTO mealTO;

	@Before
	public void setUp() throws Exception {

		meal1 = new Meal(new ArrayList<>(), UUID.randomUUID(), "chicken","chicken", true,new HashSet<>());
		meal2 = new Meal(new ArrayList<>(), UUID.randomUUID(),"chicken", "chicken", false,new HashSet<>());
		meal3 = new Meal(new ArrayList<>(), UUID.randomUUID(), "mutton", "mutton", true,new HashSet<>());
		mealTO = new MealTO(new ArrayList<>(),meal1.getMealId(), "chicken and veg", "chicken and veg",false,new HashSet<>());

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void MealServiceListTest() {
		List<Meal> actualMeals = new ArrayList<>();
		actualMeals.add(meal1);
		actualMeals.add(meal2);
		actualMeals.add(meal3);
		Mockito.when(mealRepository.findAll()).thenReturn(actualMeals);
		List<MealTO> expectedMeals = mealService.list();

		Assert.assertEquals(expectedMeals.size(), actualMeals.size());
	}

	@Test
	public void MealServiceListByPredicateTest(){

		QMeal qMeal = new QMeal("meal");
		Predicate predicate = qMeal.description.eq("chicken");

		List<Meal> mealsWithPredicate = new ArrayList<>();

		mealsWithPredicate.add(meal1);
		mealsWithPredicate.add(meal2);

		Mockito.when(mealRepository.findAll(predicate)).thenReturn(mealsWithPredicate);

		List<MealTO> expectedMeals = mealService.listByPredicate(predicate);

		Assert.assertEquals(expectedMeals.size(), mealsWithPredicate.size());


	}

	@Test 
	public void MealServiceUpdateTest(){

		UUID uuid = mealTO.getMealId();

		Optional<Meal> meal = Optional.of(meal1);

		Mockito.when(mealRepository.findByMealId(uuid)).thenReturn(meal);
		Mockito.when(mealRepository.save(meal1)).thenReturn(meal1);

		mealTO = mealService.update(mealTO);

		Assert.assertEquals(mealTO.getDescription(), meal1.getDescription());
		Assert.assertEquals(mealTO.isAvailable(), meal1.isAvailable());
	}

	@Test
	public void MealServiceCreateTest(){

		Mockito.when(conversionService.convert(mealTO, Meal.class)).thenReturn(meal1);
		Mockito.when(mealRepository.save(meal1)).thenReturn(meal1);
		Mockito.when(conversionService.convert(meal1, MealTO.class)).thenReturn(mealTO);

		mealService.create(mealTO);

		Assert.assertEquals(mealTO.getDescription(), "chicken and veg");
		Assert.assertEquals(mealTO.isAvailable(), false);
	}

}
