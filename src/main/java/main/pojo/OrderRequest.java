package main.pojo;

import lombok.Getter;
import lombok.Setter;
import main.model.EProduct;
import main.model.ProductType;
import main.model.User;

@Getter
@Setter
public class OrderRequest {
    private User user;

    private int productId;

    private int count;

    private ProductType productType;
}
