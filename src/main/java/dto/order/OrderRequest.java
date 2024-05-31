package dto.order;

import constants.OrderStatus;
import dto.burger.BurgerRequest;
import dto.dish.DishRequest;
import dto.user.UserRequest;
import entity.Burger;
import entity.Dishes;
import entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private LocalDateTime placedAt;

    private Double totalPrice;

    private String orderName;

    private String customerFirstName;

    private String customerLastName;

    private String userName;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String deliveryZip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;

    private OrderStatus status;

    private UserRequest user;

    private List<BurgerRequest> burgers = new ArrayList<>();

    private List<DishRequest> dishes = new ArrayList<>();
}
