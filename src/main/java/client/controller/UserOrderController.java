package client.controller;

import configuration.PageSizePropertyHolderMetaData;
import entity.Orders;
import entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.OrderRepository;
import repository.UserRepository;
import constants.OrderStatus;

import java.util.List;
import java.util.Optional;

import static constants.PathConstants.*;

@Controller
@RequestMapping(RECENT_ORDERS) // Set a distinct base URL for user-related actions
@SessionAttributes("orders")
public class UserOrderController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepository;
    private PageSizePropertyHolderMetaData props;

    public UserOrderController(OrderRepository orderRepo, UserRepository userRepository, PageSizePropertyHolderMetaData props) {
        this.orderRepo = orderRepo;
        this.userRepository = userRepository;
        this.props = props;
    }

    @GetMapping(ORDERS)
    public String ordersForUser(@SessionAttribute User user, Model model) {

        //TODO: fix user (Use authPrincipal obj) because it should be got from @AuthenticationPrincipal
//        user = userRepository.findByUsername(principal.getName())
//                .orElseThrow(() -> new ApiRequestException("EMAIL_NOT_FOUND", HttpStatus.NOT_FOUND));;

        Pageable pageable = PageRequest.of(0, props.getPageSize());
        List<Orders> ordersList = orderRepo.findByUserNameOrderByPlacedAtDesc(user.getUsername(), pageable);

        model.addAttribute("orders", ordersList );


        return "orderList";
    }


    @PostMapping(CANCEL_ORDER)
    public String cancelOrder(@PathVariable Long orderId, Model model) {

        Optional<Orders> optionalOrder = orderRepo.findById(orderId);
        System.out.println(optionalOrder.get().getId());
        
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            if (order.getStatus() == OrderStatus.OPENED ) {
                order.setStatus(OrderStatus.CANCELLED);
                orderRepo.save(order);

                // Update the orders list in the session attribute
                List<Orders> ordersList = (List<Orders>) model.getAttribute("orders");
                ordersList.replaceAll(o -> o.getId().equals(orderId) ? order : o);

                model.addAttribute("orders", ordersList);
            }
        }

        return REDIRECT_RECENT_ORDERS;
    }

}