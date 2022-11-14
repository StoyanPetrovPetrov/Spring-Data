package com.softuni.gamestore.services.implementations;

import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.models.entities.Order;
import com.softuni.gamestore.models.entities.OrderStatus;
import com.softuni.gamestore.models.entities.User;
import com.softuni.gamestore.repositories.OrderRepository;
import com.softuni.gamestore.services.interfaces.OrderService;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public String addToOrder(Game game, User user) {
        ensureUserDontHaveGame(user, game);
        Order order = ensureOpenedOrder(user);
        addGameToOrder(game, order);
        this.repository.saveAndFlush(order);
        return String.format("%s added to cart.", game.getTitle());
    }

    @Override
    public String removeFromOrder(Game game, User user) {
        Order order = ensureOpenedOrder(user);
        removeGameToOrder(game, order);
        this.repository.saveAndFlush(order);
        return String.format("%s removed from cart.", game.getTitle());
    }

    @Override
    public Set<Game> buyItems(User user) {
        Order order = ensureOpenedOrder(user);
        finalizeOrder(order);
        this.repository.save(order);
        return order.getGames();
    }

    private void finalizeOrder(Order order) {
        order.setOrderStatus(OrderStatus.FULFILLED);
    }

    private void removeGameToOrder(Game game, Order order) {
        order.getGames().remove(game);
    }

    private void ensureUserDontHaveGame(User user, Game game) {
        if (user.getGames().contains(game)) {
            throw new ValidationException(
                    List.of(new ErrorMessage(String.format("You already have the game '%s'", game.getTitle()))));
        }
    }

    private Order ensureOpenedOrder(User user) {
        Optional<Order> order = getOrder(user);

        if (order.isEmpty()) {
            return new Order(user);
        }

        return order.get();
    }

    private Optional<Order> getOrder(User user) {
        return this.repository
                .findOneByBuyerAndOrderStatus(user, OrderStatus.OPEN);
    }

    private void addGameToOrder(Game game, Order order) {
        order.getGames().add(game);
    }

}
