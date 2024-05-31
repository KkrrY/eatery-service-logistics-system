package client.controller;

import entity.Burger;
import entity.Ingredient;
import entity.Orders;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import entity.Ingredient.Type;
import service.DiscountService;
import static client.constants.UtilConstants.RESOURCE_SERVER_URL;
import static constants.PathConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static client.constants.PathConstants.*;

@Controller
@RequestMapping(BURGER_BUILDER_MAPPING)
@SessionAttributes("order")
@Slf4j
public class DesignController {

    private final RestTemplate clientRestTemplate;

    private DiscountService discountService;

    @Autowired
    public DesignController(
            RestTemplate clientRestTemplate,
            DiscountService discountService) {
        this.clientRestTemplate = clientRestTemplate;
        this.discountService = discountService;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        List.of(
                Objects.requireNonNull(
                        clientRestTemplate.getForObject(RESOURCE_SERVER_URL + API_V1_INGREDIENT + INGREDIENTS, Ingredient[].class)
                                        )
                )
                .forEach(i -> ingredients.add(i));

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }


    @ModelAttribute(name = "burger")
    public Burger burger() {
        return new Burger();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processDish(
            @Valid Burger burger, Errors errors,
            @SessionAttribute Orders order) {


        if (errors.hasErrors()) {
            return "design";
        }

        order.addTaco(burger);
        order.setTotalPrice(
                (  order.getTotalPrice() == null ? 0 : order.getTotalPrice()   ) +
                (  burger.getIngredients().stream()
                .mapToDouble(i -> i.getPrice())
                .sum()  * discountService.calculateDiscount() )
        );

        return "redirect:" + ORDERS_CURRENT;
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}

