package main.model.Data.Impl;

import main.model.Product;

import javax.persistence.*;

@Entity
@Table(name = "liquids")
public class Liquid implements Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int price;

    @Column(length = 7000)
    private String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    /*public Double getPriceDouble() {
        String stringPrice = getPrice() + ".0";
        return Double.parseDouble(stringPrice);
    }*/
}
