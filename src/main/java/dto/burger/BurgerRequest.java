package dto.burger;

import constants.OrderStatus;
import dto.dish.DishRequest;
import dto.order.OrderRequest;
import entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BurgerRequest {

    private Date createdAt = new Date();

    private String name;

    private List<Ingredient> ingredients;

    private OrderRequest order;
}
