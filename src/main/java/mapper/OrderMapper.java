package mapper;

import dto.order.OrderRequest;
import dto.order.OrderResponse;
import entity.Orders;
import entity.User;
import exception.InputFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import service.OrderService;
import service.UserService;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final CommonMapper commonMapper;
    private final OrderService orderService;
    private final UserService userService;

    public OrderResponse findOrderById (Long id) {
        return commonMapper.convertToResponse(orderService.findById(id), OrderResponse.class);
    }

    public OrderResponse saveOrder (OrderRequest orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Orders order = orderService.saveOrder(commonMapper.convertToResponse(orderRequest, Orders.class) );

        return commonMapper.convertToResponse(order, OrderResponse.class);
    }
}
