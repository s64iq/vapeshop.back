package main.controllers;

import main.pojo.OrderRequest;
import main.pojo.OrderResponse;
import main.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/")
    public OrderResponse getData(@RequestHeader("Authorization") String token) {
        return orderService.getData(token);
    }

    @PostMapping("/order/")
    public boolean addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }

    @PutMapping("/order/")
    public void changeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.changeOrder(orderRequest);
    }

    @DeleteMapping("/order/")
    public void deleteOrder(@RequestBody OrderRequest orderRequest) {
        orderService.deleteOrder(orderRequest);
    }
}
