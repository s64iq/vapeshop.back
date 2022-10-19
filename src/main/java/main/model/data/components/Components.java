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

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getUrl4() {
        return url4;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public String getUrl5() {
        return url5;
    }

    public void setUrl5(String url5) {
        this.url5 = url5;
    }

    public String getUrl6() {
        return url6;
    }

    public void setUrl6(String url6) {
        this.url6 = url6;
    }

    public String getUrl7() {
        return url7;
    }

    public void setUrl7(String url7) {
        this.url7 = url7;
    }

    public String getUrl8() {
        return url8;
    }

    public void setUrl8(String url8) {
        this.url8 = url8;
    }

    public String getUrl9() {
        return url9;
    }

    public void setUrl9(String url9) {
        this.url9 = url9;
    }

    public String getUrl10() {
        return url10;
    }

    public void setUrl10(String url10) {
        this.url10 = url10;
    }

    public String getUrl11() {
        return url11;
    }

    public void setUrl11(String url11) {
        this.url11 = url11;
    }

    public String getUrl12() {
        return url12;
    }

    public void setUrl12(String url12) {
        this.url12 = url12;
    }

    public String getUrl13() {
        return url13;
    }

    public void setUrl13(String url13) {
        this.url13 = url13;
    }

    public String getUrl14() {
        return url14;
    }

    public void setUrl14(String url14) {
        this.url14 = url14;
    }

    public String getUrl15() {
        return url15;
    }

    public void setUrl15(String url15) {
        this.url15 = url15;
    }

    public String getUrl16() {
        return url16;
    }

    public void setUrl16(String url16) {
        this.url16 = url16;
    }

    public String getUrl17() {
        return url17;
    }

    public void setUrl17(String url17) {
        this.url17 = url17;
    }

    public String getUrl18() {
        return url18;
    }

    public void setUrl18(String url18) {
        this.url18 = url18;
    }

    public String getUrl19() {
        return url19;
    }

    public void setUrl19(String url19) {
        this.url19 = url19;
    }

    public String getUrl20() {
        return url20;
    }

    public void setUrl20(String url20) {
        this.url20 = url20;
    }

    public String getUrl21() {
        return url21;
    }

    public void setUrl21(String url21) {
        this.url21 = url21;
    }

    public String getUrl22() {
        return url22;
    }

    public void setUrl22(String url22) {
        this.url22 = url22;
    }

    public String getUrl23() {
        return url23;
    }

    public void setUrl23(String url23) {
        this.url23 = url23;
    }

    public String getUrl24() {
        return url24;
    }

    public void setUrl24(String url24) {
        this.url24 = url24;
    }

    public String getUrl25() {
        return url25;
    }

    public void setUrl25(String url25) {
        this.url25 = url25;
    }

    public String getUrl26() {
        return url26;
    }

    public void setUrl26(String url26) {
        this.url26 = url26;
    }

    public String getUrl27() {
        return url27;
    }

    public void setUrl27(String url27) {
        this.url27 = url27;
    }

    public String getUrl28() {
        return url28;
    }

    public void setUrl28(String url28) {
        this.url28 = url28;
    }

    public String getUrl29() {
        return url29;
    }

    public void setUrl29(String url29) {
        this.url29 = url29;
    }

    public String getXark1() {
        return xark1;
    }

    public void setXark1(String xark1) {
        this.xark1 = xark1;
    }

    public String getXark2() {
        return xark2;
    }

    public void setXark2(String xark2) {
        this.xark2 = xark2;
    }

    public String getXark3() {
        return xark3;
    }

    public void setXark3(String xark3) {
        this.xark3 = xark3;
    }

    public String getXark4() {
        return xark4;
    }

    public void setXark4(String xark4) {
        this.xark4 = xark4;
    }

    public String getComp1() {
        return comp1;
    }

    public void setComp1(String comp1) {
        this.comp1 = comp1;
    }

    public String getComp2() {
        return comp2;
    }

    public void setComp2(String comp2) {
        this.comp2 = comp2;
    }

    public String getComp3() {
        return comp3;
    }

    public void setComp3(String comp3) {
        this.comp3 = comp3;
    }

    public String getComp4() {
        return comp4;
    }

    public void setComp4(String comp4) {
        this.comp4 = comp4;
    }

    public String getComp5() {
        return comp5;
    }

    public void setComp5(String comp5) {
        this.comp5 = comp5;
    }
}
