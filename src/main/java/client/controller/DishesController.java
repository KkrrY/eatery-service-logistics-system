package client.controller;

import entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import repository.DishesRepository;
import repository.UserRepository;
import service.DiscountService;
import service.impl.DiscountServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static client.constants.ResourceUrl.RESOURCE_SERVER_URL;
import static constants.PathConstants.*;

@Controller
@RequestMapping(DISHES)
@SessionAttributes("order")
@Slf4j
public class DishesController {

    private final RestTemplate restTemplate;
    private final DiscountService discountService;
    @Autowired
    public DishesController(
            RestTemplate restTemplate,
            DiscountService discountService) {
        this.restTemplate = restTemplate;
        this.discountService = discountService;
    }

    @ModelAttribute
    public void addDishesToModel(Model model) {
        List<Dishes> dishes = new ArrayList<>();
        List.of(
                        Objects.requireNonNull(
                                restTemplate.getForObject(RESOURCE_SERVER_URL + API_V1_DISH + DISHES, Dishes[].class)
                        )
                )
                .forEach(i -> dishes.add(i));

        model.addAttribute("dishes", dishes );
    }


    @ModelAttribute(name = "dish")
    public Dishes dish() {
        return new Dishes();
    }


    @GetMapping
    public String showMapping() {
        return "dishes";
    }

    @PostMapping
    public String processDish(
            @RequestParam String dishId,
            //@Valid Dishes dish, Errors errors,
            @SessionAttribute Orders order) {

//        if (errors.hasErrors()) {
//            return "dishes";
//        }
        Dishes dish = restTemplate.getForObject(RESOURCE_SERVER_URL + API_V1_DISH + "/" + dishId, Dishes.class);
        order.addDish(dish);
        order.setTotalPrice(
                (  order.getTotalPrice() == null ? 0 : order.getTotalPrice()   ) +
                        ( dish.getPrice() * discountService.calculateDiscount())
        );

        return "redirect:" + ORDERS_CURRENT;
    }

    

}
