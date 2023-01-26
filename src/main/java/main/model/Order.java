package main.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ordertable")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String productname;

    private int price;

    private String photo;

    private String urlname;

    private int count;

    public Order() {
    }
}
