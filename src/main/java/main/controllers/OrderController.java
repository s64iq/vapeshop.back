package main.controllers;

import main.configs.jwt.JwtUtils;
import main.model.Order;
import main.pojo.OrderRequest;
import main.repository.OrderRepository;
import main.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/order/")
    public List<Order> getData(@RequestHeader("Authorization") String token) {
        String userNameFromToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);

        return orderRepository.findByUser(userRepository.findByUsername(userNameFromToken).get())
                .orElseThrow(() -> new RuntimeException("Error, Order's not found"));
    }

    @GetMapping("/order/total/")
    public int getTotalPrice(@RequestHeader("Authorization") String token) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        int total = 0;
        for (Order order : orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromJwtToken)).get()) {
            total += order.getPrice() * order.getCount();
        }
        return total;
    }

    @PostMapping("/order/")
    public boolean addOrder(@RequestBody OrderRequest orderRequest) {
        if(orderRepository.existsByUserAndProductName(orderRequest.getUser(), orderRequest.getProductName())) {
            return false;
        } else {
            orderRepository.save(new Order(
                    orderRequest.getUser(),
                    orderRequest.getProductName(),
                    orderRequest.getPrice(),
                    orderRequest.getCount(),
                    orderRequest.getUrl()));
            return true;
        }
    }

    @PutMapping("/order/")
    public void changeOrder(@RequestBody OrderRequest orderRequest) {
        if(orderRepository.existsByUserAndProductName(orderRequest.getUser(), orderRequest.getProductName())) {
            orderRepository.findByUserAndProductName(orderRequest.getUser(), orderRequest.getProductName())
                    .setCount(orderRequest.getCount());
            orderRepository.save(orderRepository.findByUserAndProductName(orderRequest.getUser(), orderRequest.getProductName()));
        }
    }

    @DeleteMapping("/order/")
    public void deleteOrder(@RequestHeader("Authorization") String token, @RequestBody String productName) {
        String userNameFromToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);

        if(orderRepository.existsByUserAndProductName(userRepository.findByUsername(userNameFromToken).get(), productName)) {
            orderRepository.deleteById(
                    orderRepository.findByUserAndProductName(
                            userRepository.findByUsername(userNameFromToken).get(), productName).getId());
        }
    }
}
