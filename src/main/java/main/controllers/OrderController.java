package main.controllers;

import main.configs.jwt.JwtUtils;
import main.model.Order;
import main.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String userNameFromToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        return orderRepository.findByUsername(userNameFromToken)
                .orElseThrow(() -> new RuntimeException("Error, Order's not found"));
    }

    @GetMapping("/order/total/")
    public int getTotalPrice(@RequestHeader("Authorization") String token) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        int total = 0;
        for (Order order : orderRepository.findByUsernameIgnoreCase(userNameFromJwtToken)) {
            total += order.getPrice() * order.getCount();
        }
        return total;
    }

    @PostMapping("/order/")
    public boolean addOrder(Order order) {
        if(orderRepository.existsByUsernameContainsAndProductnameContains(order.getUsername(), order.getProductname())) {
            return false;
        } else {
            orderRepository.save(order);
            return true;
        }
    }

    @PutMapping("/order/")
    public void changeOrder(Order order) {
        if(orderRepository.existsByUsernameContainsAndProductnameContains(order.getUsername(), order.getProductname())) {
            orderRepository.deleteById(orderRepository.findByUsernameContainsAndProductnameContains(order.getUsername(),order.getProductname()).get().getId());
            orderRepository.save(order);
        }
    }

    @DeleteMapping("/order/")
    public void deleteOrder(@RequestHeader("Authorization") String token, String productName) {
        String userNameFromToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        if(orderRepository.existsByUsernameContainsAndProductnameContains(userNameFromToken, productName)) {
            orderRepository.deleteById(orderRepository.findByUsernameContainsAndProductnameContains(userNameFromToken, productName).get().getId());
        }
    }
}
