package com.yuma.app.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuma.app.service.MealService;
import com.yuma.app.to.MealTO;

@Slf4j
@RestController
@RequestMapping("api/meals")
public class MealController {
	
	private MealService mealService;

	@Autowired
	public MealController(MealService mealService) {
		this.mealService = mealService;
	}

	@GetMapping("/all")
	public List<MealTO> getAll() {
		log.info("retrieving meals list from DB");

		return this.mealService.list();
	}

	@GetMapping("/availablemeals")
	public List<MealTO> getAvailableMeals() {
		log.info("retrieving available meals list from DB");

		return this.mealService.availableMeals();
	}

	@GetMapping("/{description}")
	public MealTO getByDescription(@PathVariable String description) {
		log.info("retrieving meals list from DB by description {}", description);

		return this.mealService.findByDescription(description);
	}

	@PutMapping("/update")
	public MealTO update(@RequestBody MealTO mealTO) {
		log.info("updating meal into the database");

		return this.mealService.update(mealTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public MealTO create(@RequestBody MealTO mealTO) {
		log.info("creating meal into the database");
		return this.mealService.create(mealTO);
	}

	@DeleteMapping("/{mealId}")
	public void deleteMeal(@PathVariable String mealId) {
		log.info("deleting meal with mealId {}", mealId);

		this.mealService.deleteMeal(mealId);
	}
	
	@PutMapping("/availability/{mealId}")
	public void updateMealAvailability(@PathVariable String mealId){
		log.info("updating availability of {}", mealId);
		
		this.mealService.updateAvailability(mealId);
	}
}
