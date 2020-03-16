package com.sms.sms.pojo;

/**
 * @Auther: weibin
 * @Date: 2019/11/25 14:33
 * @Description:
 * @Version: 1.0
 */
public class SetProvinceLimit {

    //`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    //  `province` varchar(255) DEFAULT NULL COMMENT '省份',
    //  `p_one` int(255) DEFAULT NULL COMMENT '1元',
    //  `p_two` int(255) DEFAULT NULL COMMENT '2元',
    //  `p_three` int(255) DEFAULT NULL COMMENT '3元',
    //  `type` int(255) DEFAULT NULL COMMENT '1代表只限量同步更新后台,2代表计费和同步同时限量',
    //  `is_shell` int(255) DEFAULT NULL COMMENT '是否壳子日限 1,是 2,不是',
    //  `createtime` datetime DEFAULT NULL COMMENT '修改时间',
    //  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',

    private int id;
    private String province;
    private Integer pOne;
    private Integer pTwo;
    private Integer pTwoO;
    private Integer pTwoT;
    private Integer pThree;
    private Integer pFive;
    private Integer type;
    private Integer isShell;
    private String createtime;
    private String userName;

    public Integer getpTwoO() {
        return pTwoO;
    }

    public void setpTwoO(Integer pTwoO) {
        this.pTwoO = pTwoO;
    }

    public Integer getpTwoT() {
        return pTwoT;
    }

    public void setpTwoT(Integer pTwoT) {
        this.pTwoT = pTwoT;
    }

    public Integer getpFive() {
        return pFive;
    }

    public void setpFive(Integer pFive) {
        this.pFive = pFive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getpOne() {
        return pOne;
    }

    public void setpOne(Integer pOne) {
        this.pOne = pOne;
    }

    public Integer getpTwo() {
        return pTwo;
    }

    public void setpTwo(Integer pTwo) {
        this.pTwo = pTwo;
    }

    public Integer getpThree() {
        return pThree;
    }

    public void setpThree(Integer pThree) {
        this.pThree = pThree;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsShell() {
        return isShell;
    }

    public void setIsShell(Integer isShell) {
        this.isShell = isShell;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "SetProvinceLimit{" +
                "id=" + id +
                ", province='" + province + '\'' +
                ", pOne=" + pOne +
                ", pTwo=" + pTwo +
                ", pTwoO=" + pTwoO +
                ", pTwoT=" + pTwoT +
                ", pThree=" + pThree +
                ", pFive=" + pFive +
                ", type=" + type +
                ", isShell=" + isShell +
                ", createtime='" + createtime + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
