package main.model;

import org.springframework.stereotype.Component;

@Component
public interface Product {
    int getId();

    String getName();

    int getPrice();

    String getUrl();
}