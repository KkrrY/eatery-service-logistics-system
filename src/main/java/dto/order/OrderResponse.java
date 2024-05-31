package dto.order;

import constants.OrderStatus;
import dto.burger.BurgerRequest;
import dto.burger.BurgerResponse;
import dto.dish.DishResponse;
import dto.user.UserResponse;
import entity.Burger;
import entity.Dishes;
import entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long id;

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

    private UserResponse user;

    private List<BurgerResponse> burgers = new ArrayList<>();

    private List<DishResponse> dishes = new ArrayList<>();
}
