package client.controller;

import dto.order.OrderResponse;
import entity.User;
import entity.Orders;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import service.DiscountService;

import static constants.PathConstants.*;
import static client.constants.PathConstants.*;
import static client.constants.UtilConstants.RESOURCE_SERVER_URL;

@Controller
@RequestMapping(ORDER_PROCESSING)
@SessionAttributes("order")
public class OrdersController {

    private RestTemplate clientRestTemplate;
    private DiscountService discountService;

    public OrdersController(DiscountService discountService, RestTemplate clientRestTemplate) {
        this.discountService = discountService;
        this.clientRestTemplate = clientRestTemplate;
    }

    @ModelAttribute(name = "discount")
    public String showDiscount () {
        return String.valueOf( Math.round( (1 - discountService.calculateDiscount() ) * 100 ) );
    }

    @GetMapping(CURRENT_ORDER)
    public String orderForm(@SessionAttribute User user,
                            @SessionAttribute Orders order) {

        if (user != null) {
            order.setUserName(user.getUsername());
            if (order.getCustomerFirstName() == null) {
                order.setCustomerFirstName(user.getFirstName());
            }
            if (order.getCustomerLastName() == null) {
                order.setCustomerLastName(user.getLastName());
            }
            if (order.getDeliveryStreet() == null) {
                order.setDeliveryStreet(user.getStreet());
            }
            if (order.getDeliveryCity() == null) {
                order.setDeliveryCity(user.getCity());
            }
            if (order.getDeliveryState() == null) {
                order.setDeliveryState(user.getCountry());
            }
            if (order.getDeliveryZip() == null) {
                order.setDeliveryZip(user.getZip());
            }
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("order") Orders order, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        //can put here any object with equal by namings and types fields (if they're convertable with mapper)
        clientRestTemplate.postForObject(RESOURCE_SERVER_URL + API_V1_ORDER, order, OrderResponse.class);
        sessionStatus.setComplete();

        return "redirect:" + HOME;
    }

}