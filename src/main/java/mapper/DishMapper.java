package mapper;

import dto.dish.DishResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import service.DishService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishMapper {
    private final CommonMapper commonMapper;
    private final DishService dishService;

    public DishResponse findDishById (String id) {
        return commonMapper.convertToResponse(dishService.findById(id), DishResponse.class);
    }

    public List<DishResponse> findAllDishes() {
        return commonMapper.convertToResponseList(dishService.findAllDishes(), DishResponse.class);
    }
}
