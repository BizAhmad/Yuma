package com.yuma.app.to;

import java.util.HashSet;
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
public class MealTO {

	protected List<Ingredients> ingredients;
	private UUID mealId;
	private String name;
	private String description;
	private boolean isAvailable;
	private HashSet<String> flags;
	private int mealScore;

	@Override
	public String toString() {
		return "Meal{" +
			"mealId=" + mealId +
			"name=" + name +
			", description='" + description + '\'' +
			", isAvailable=" + isAvailable +
			", ingredients=" + ingredients +
			", flags=" + flags +
			", mealScore=" + mealScore +
			'}';
	}
}
