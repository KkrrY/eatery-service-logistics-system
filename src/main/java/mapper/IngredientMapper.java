package mapper;

import dto.ingredient.IngredientResponse;
import dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.IngredientService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngredientMapper {
    private final CommonMapper commonMapper;
    private final IngredientService ingredientService;
    public List<IngredientResponse> findAllIngredients () {
        return commonMapper.convertToResponseList(ingredientService.findAllIngredients(), IngredientResponse.class);
    }
}
