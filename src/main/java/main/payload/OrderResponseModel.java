package main.payload;

import lombok.Getter;
import lombok.Setter;
import main.model.Product;

@Getter
@Setter
public class OrderResponseModel {
    private Product product;

    private int count;

    public OrderResponseModel(Product product, int count) {
        this.product = product;
        this.count = count;
    }
}
