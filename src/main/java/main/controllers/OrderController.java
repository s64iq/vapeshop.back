package main.controllers;

import main.configs.jwt.JwtUtils;
import main.model.Order;
import main.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order/")
    public List<Order> getData(@RequestHeader("Authorization") String token) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        Iterable<Order> orderIterable = orderRepository.findAll();

        ArrayList<Order> orderArray = new ArrayList<>();
        for(Order order : orderIterable) {
            if(order.getUsername().equals(userNameFromJwtToken)) {
                orderArray.add(order);
            }
        }
        return orderArray;
    }

    @PostMapping("/order/")
    public int addOrder(Order order) {
        Order order1 = orderRepository.save(order);
        return order1.getId();
    }

    @PutMapping("/order/")
    public void changeOrder(Order order) {
        Iterable<Order> orderIterable = orderRepository.findAll();
        for(Order order1 : orderIterable) {
            if(order.getProductname().equals(order1.getProductname())) {
                orderRepository.deleteById(order1.getId());
                orderRepository.save(order);
            }
        }
    }

    @DeleteMapping("/order/")
    public void deleteOrder(Order order) {
        Iterable<Order> orderIterable = orderRepository.findAll();
        for(Order order1 : orderIterable) {
            if(order.getProductname().equals(order1.getProductname())) {
                orderRepository.deleteById(order1.getId());
            }
        }
    }
}
