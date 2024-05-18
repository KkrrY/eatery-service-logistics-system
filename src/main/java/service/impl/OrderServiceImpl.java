package service.impl;

import entity.Orders;
import entity.User;
import exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BurgerRepository;
import repository.DishesRepository;
import repository.OrderRepository;
import service.OrderService;

import java.util.Optional;
import static constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BurgerRepository burgerRepository;
    private final DishesRepository dishRepository;

    @Override
    public Orders findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Orders saveOrder(Orders order ) {
        System.out.println(order.toString());
        order.getBurgers().forEach(i -> burgerRepository.save(i)); //Did to avoid org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: entity.Burger
        order.getDishes().forEach(i -> dishRepository.save(i));

        //order.setUser(user);
        order.setOrderName(order.getOrderName());
        orderRepository.save(order);
        order.getBurgers().forEach(i -> i.setOrder(order) ); //update
        burgerRepository.saveAll(order.getBurgers());
        dishRepository.saveAll(order.getDishes());

        return order;
    }
}
