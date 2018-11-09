package com.yuma.app.to;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.yuma.app.document.Ingredients;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealTo {

	protected List<Ingredients> ingredients;
	private UUID mealId;
	private String description;
	private boolean isAvailable;

	@Override
	public String toString() {
		return "Meal{" +
			"mealId=" + mealId +
			", description='" + description + '\'' +
			", isAvailable=" + isAvailable +
			", ingredients=" + ingredients +
			'}';
	}

}