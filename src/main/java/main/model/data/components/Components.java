package main.model.data.components;

import javax.persistence.*;

@Entity
@Table(name = "components")
public class Components {

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
    private String url16;
    private String url17;
    private String url18;
    private String url19;
    private String url20;
    private String url21;
    private String url22;
    private String url23;
    private String url24;
    private String url25;
    private String url26;
    private String url27;
    private String url28;
    private String url29;

    private String xark1;
    private String xark2;
    private String xark3;
    private String xark4;

    private String comp1;
    private String comp2;
    private String comp3;
    private String comp4;
    private String comp5;

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
        String stringPrice = getPrice().replaceAll(" ", "").replaceAll(",",".");
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
    public String getUrl16() {
        return url16;
    }
    public String getUrl17() {
        return url17;
    }
    public String getUrl18() {
        return url18;
    }
    public String getUrl19() {
        return url19;
    }
    public String getUrl20() {
        return url20;
    }
    public String getUrl21() {
        return url21;
    }
    public String getUrl22() {
        return url22;
    }
    public String getUrl23() {
        return url23;
    }
    public String getUrl24() {
        return url24;
    }
    public String getUrl25() {
        return url25;
    }
    public String getUrl26() {
        return url26;
    }
    public String getUrl27() {
        return url27;
    }
    public String getUrl28() {
        return url28;
    }
    public String getUrl29() {
        return url29;
    }


    public String getXark1() {
        return xark1;
    }
    public String getXark2() {
        return xark2;
    }
    public String getXark3() {
        return xark3;
    }
    public String getXark4() {
        return xark4;
    }
    public String getComp1() {
        return comp1;
    }
    public String getComp2() {
        return comp2;
    }
    public String getComp3() {
        return comp3;
    }
    public String getComp4() {
        return comp4;
    }
    public String getComp5() {
        return comp5;
    }
}
