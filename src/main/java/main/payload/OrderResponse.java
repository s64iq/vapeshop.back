package main.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private List<OrderResponseModel> orderList;

    public OrderResponse(List<OrderResponseModel> orderList) {
        this.orderList = orderList;
    }
}
