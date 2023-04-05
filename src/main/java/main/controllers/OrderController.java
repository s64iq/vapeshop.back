package main.controllers;

import main.payload.OrderRequest;
import main.payload.OrderResponse;
import main.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
