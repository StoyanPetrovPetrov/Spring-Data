package com.softuni.gamestore.repositories;

import com.softuni.gamestore.models.entities.Order;
import com.softuni.gamestore.models.entities.OrderStatus;
import com.softuni.gamestore.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findOneByBuyerAndOrderStatus(User user, OrderStatus status);
}

