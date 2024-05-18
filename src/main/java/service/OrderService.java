package service;

import entity.Orders;
import entity.User;

import java.util.Optional;

public interface OrderService {
    Orders findById(Long id);
    Orders saveOrder (Orders order);
}
