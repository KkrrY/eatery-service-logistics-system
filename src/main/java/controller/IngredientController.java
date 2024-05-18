package controller;

import dto.ingredient.IngredientResponse;
import lombok.RequiredArgsConstructor;
import mapper.IngredientMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_INGREDIENT)
public class IngredientController {
    private final IngredientMapper ingredientMapper;

    @GetMapping(INGREDIENTS)
    public ResponseEntity<List<IngredientResponse>> getAllIngredients () {
        return ResponseEntity.ok(ingredientMapper.findAllIngredients());
    }
}
