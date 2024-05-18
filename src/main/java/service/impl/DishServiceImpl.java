package service.impl;

import entity.Dishes;
import exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repository.DishesRepository;
import service.DishService;

import java.util.List;
import java.util.Optional;
import static constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishesRepository dishesRepository;

    @Override
    public Dishes findById(String id) {
        return dishesRepository.findById(id)
                .orElseThrow( () -> new ApiRequestException(DISH_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Dishes> findAllDishes() {
        return (List<Dishes>) dishesRepository.findAll();
    }
}
