package controller;

import dto.dish.DishResponse;
import lombok.RequiredArgsConstructor;
import mapper.DishMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static constants.PathConstants.*;

@RestController
@RequestMapping(API_V1_DISH)
@RequiredArgsConstructor
public class DishController {
    private final DishMapper dishMapper;

    @GetMapping(DISH_ID)
    public ResponseEntity<DishResponse> findDishById (@PathVariable String dishId) {
        return ResponseEntity.ok(dishMapper.findDishById(dishId));
    }

    @GetMapping(DISHES)
    public ResponseEntity<List<DishResponse>> findAllDishes () {
        return ResponseEntity.ok(dishMapper.findAllDishes() );
    }
}

