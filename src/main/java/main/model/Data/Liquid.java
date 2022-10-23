package main.model.Data;

import javax.persistence.*;

@Entity
@Table(name = "liquid")
public class Liquid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String price;

    private String url1;
    private String url2;
    private String url3;
    private String url4;
    private String url5;
    private String url6;
    private String url7;
    private String url8;
    private String url9;
    private String url10;
    private String url11;
    private String url12;
    private String url13;
    private String url14;
    private String url15;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public Double getPriceDouble() {
        String stringPrice = getPrice() + ".0";
        return Double.parseDouble(stringPrice);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public String getUrl4() {
        return url4;
    }

    public String getUrl5() {
        return url5;
    }

    public String getUrl6() {
        return url6;
    }

    public String getUrl7() {
        return url7;
    }

    public String getUrl8() {
        return url8;
    }

    public String getUrl9() {
        return url9;
    }

    public String getUrl10() {
        return url10;
    }

    public String getUrl11() {
        return url11;
    }

    public String getUrl12() {
        return url12;
    }

    public String getUrl13() {
        return url13;
    }

    public String getUrl14() {
        return url14;
    }

    public String getUrl15() {
        return url15;
    }
}
