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

    private int productId;

    private int count;

    @OneToOne
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    private ProductType productType;

    public Order() {
    }

    public Order(User user, int productId, int count, ProductType productType) {
        this.user = user;
        this.productId = productId;
        this.count = count;
        this.productType = productType;
    }
}
