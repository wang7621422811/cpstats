package com.sms.sms.pojo;

/**
 * @Auther: weibin
 * @Date: 2019/11/25 14:46
 * @Description:
 * @Version: 1.0
 */
public class ProvinceLimit {

    private String province;
    private Integer one;
    private Integer two;
    private Integer twoO;
    private Integer twoT;
    private Integer three;
    private Integer five;
    private Integer type;

    public Integer getTwoO() {
        return twoO;
    }

    public void setTwoO(Integer twoO) {
        this.twoO = twoO;
    }

    public Integer getTwoT() {
        return twoT;
    }

    public void setTwoT(Integer twoT) {
        this.twoT = twoT;
    }

    public Integer getFive() {
        return five;
    }

    public void setFive(Integer five) {
        this.five = five;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProvinceLimit{" +
                "province='" + province + '\'' +
                ", one=" + one +
                ", two=" + two +
                ", twoO=" + twoO +
                ", twoT=" + twoT +
                ", three=" + three +
                ", five=" + five +
                ", type=" + type +
                '}';
    }
}
