package main.pojo;

import lombok.Getter;
import lombok.Setter;
import main.model.User;

@Getter
@Setter
public class OrderRequest {
    private User user;

    private String productName;

    private int price;

    private int count;

    private String url;
}
