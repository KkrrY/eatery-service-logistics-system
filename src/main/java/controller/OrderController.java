package controller;

import dto.order.OrderRequest;
import dto.order.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mapper.OrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import static constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ORDER)
public class OrderController {
    private final OrderMapper orderMapper;

    @GetMapping(ORDER_ID) //with @PathVariable would look like as `/api/v1/order?orderId=123`
    public ResponseEntity<OrderResponse> getOrderById (@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.findOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder (@Valid @RequestBody OrderRequest orderRequest,
                                                    BindingResult bindingResult) {
        return ResponseEntity.ok(orderMapper.saveOrder(orderRequest, bindingResult));
    }

}
