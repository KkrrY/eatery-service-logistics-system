package service;

import entity.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient findIngredientById (String id);
    List<Ingredient> findAllIngredients();
}
