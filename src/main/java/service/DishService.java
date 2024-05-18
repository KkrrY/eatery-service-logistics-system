package service;

import entity.Dishes;

import java.util.List;
import java.util.Optional;

public interface DishService {
    Dishes findById(String id);
    List<Dishes> findAllDishes ();
}
