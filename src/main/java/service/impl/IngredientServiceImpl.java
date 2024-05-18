package service.impl;

import entity.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.IngredientRepository;
import service.IngredientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient findIngredientById(String id) {
        return null;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return (List<Ingredient>) ingredientRepository.findAll();
    }
}
