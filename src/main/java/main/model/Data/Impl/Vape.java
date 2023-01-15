package main.model.Data.Impl;

import main.model.Data.ProductTypes.ProductWithFeatures;

import javax.persistence.*;

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

    public String getXark() {
        return xark;
    }

    public String getComp() {
        return comp;
    }

    /*public Double getPriceDouble() {
        String stringPrice = getPrice().replaceAll(" ", "").replaceAll(",",".");
        return Double.parseDouble(stringPrice);
    }*/
}
