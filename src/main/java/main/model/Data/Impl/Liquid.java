package main.model.Data.Impl;

import lombok.*;
import main.model.Product;

import javax.persistence.*;

@Getter
@Setter
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

    public Liquid() {
    }
}
