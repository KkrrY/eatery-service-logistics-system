package dto.burger;

import dto.order.OrderResponse;
import entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BurgerResponse {

    private Long id;

    private Date createdAt = new Date();

    private String name;

    private List<Ingredient> ingredients;

    private OrderResponse order;
}
