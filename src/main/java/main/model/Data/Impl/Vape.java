package main.model.Data.Impl;

import lombok.*;
import main.model.Data.ProductTypes.ProductWithFeatures;
import main.model.ProductType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "vapes")
public class Vape extends ProductWithFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int price;

    @Column(length = 7000)
    private String url;

    @Column(length = 1500)
    private String xark;

    @Column(length = 1500)
    private String comp;

    @OneToOne
    @JoinColumn(name = "product_type", referencedColumnName = "id")
    private ProductType type;

    public Vape() {
    }
}
