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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "product_name")
    private String productName;

    private int price;

    private int count;

    private String url;

    public Order() {
    }

    public Order(User user, String productName, int price, int count, String url) {
        this.user = user;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.url = url;
    }
}
