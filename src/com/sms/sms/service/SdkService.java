package com.sms.sms.service;

import com.sms.sms.dao.SdkDao;
import com.sms.sms.pojo.ProvinceLimit;
import com.sms.sms.pojo.SetProvinceLimit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: weibin
 * @Date: 2019/11/25 14:28
 * @Description:    渠道后台余量显示service层
 * @Version: 1.0
 */
public class SdkService {

    private SdkDao dao = new SdkDao();


    /**
     *  查询出所有的余量数据之后装入到集合中返回
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ProvinceLimit> queryAll(String startTime,String endTime) {
        List<ProvinceLimit> all = new ArrayList<ProvinceLimit>();

        // 查询超过
        List<ProvinceLimit> provinceIsShells = queryByIsShell(startTime, endTime);
        List<ProvinceLimit> provinceNotShells = queryByNotIsShell(startTime, endTime);


        //查询出限量表所有数据
        List<SetProvinceLimit> setProvinceLimits = dao.queryProvinceLimitAll();
        for (SetProvinceLimit setProvinceLimit : setProvinceLimits) {
            if (setProvinceLimit.getIsShell() == 1) {
                //遍历壳子限量的数据之后计算出百分比
                for (ProvinceLimit provinceIsShell : provinceIsShells) {

                    if (provinceIsShell.getProvince().equals(setProvinceLimit.getProvince())) {
                        double sum = 0.0d;
                        //查询是否到移动限量 800039 800038
                        int count = dao.queryByMsg(1,provinceIsShell.getProvince(),"800039","800038");
                        if (count > 50) {
                            provinceIsShell.setOne(0);
                            continue;
                        }
                        double one = provinceIsShell.getOne();
                        if (setProvinceLimit.getpTwo() == -1) {
                            provinceIsShell.setTwo(-1);
                        }
                        if (setProvinceLimit.getpTwo() == -1) {
                            provinceIsShell.setTwo(-1);
                        }
                        if (setProvinceLimit.getpTwoO() == -1) {
                            provinceIsShell.setTwoO(-1);
                        }
                        if (setProvinceLimit.getpTwoT() == -1) {
                            provinceIsShell.setTwoT(-1);
                        }
                        if (setProvinceLimit.getpThree() == -1) {
                            provinceIsShell.setThree(-1);
                        }
                        if (setProvinceLimit.getpFive() == -1) {
                            provinceIsShell.setFive(-1);
                        }
                        double integer = setProvinceLimit.getpOne();
                        sum = (one / integer) * 100;
                        provinceIsShell.setOne((setProvinceLimit.getpOne() - provinceIsShell.getOne()) * 100 / setProvinceLimit.getpOne());
                    }
                }
            } else if (setProvinceLimit.getIsShell() == 2) {
                //遍历分省限量数据之后计算出百分比
                //遍历壳子限量的数据之后计算出百分比
                for (ProvinceLimit provinceNotShell : provinceNotShells) {

                    System.err.println(provinceNotShell.getProvince() +" : " + provinceNotShell);
                    System.err.println(setProvinceLimit);
                    if (provinceNotShell.getProvince().equals(setProvinceLimit.getProvince())) {
                        // 根据省份和金额去查询数据库有没有到达限量
                        int count = 0;
                        if (setProvinceLimit.getpOne() == 0) {
                            provinceNotShell.setOne(-1);
                        } else {
                            int percent = (setProvinceLimit.getpOne() - provinceNotShell.getOne()) * 100 / setProvinceLimit.getpOne();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(100,provinceNotShell.getProvince(),0);
                            if (count > 50) {
                                provinceNotShell.setOne(0);
                            } else {
                                provinceNotShell.setOne(percent);
                            }
                        }
                        if (setProvinceLimit.getpTwo() == 0) {
                            provinceNotShell.setTwo(-1);
                        } else {
                            int percent = (setProvinceLimit.getpTwo() - provinceNotShell.getTwo()) * 100 / setProvinceLimit.getpTwo();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(200,provinceNotShell.getProvince(),0);
                            if (count > 50) {
                                provinceNotShell.setTwo(0);
                            } else {
                                provinceNotShell.setTwo(percent);
                            }
                        }
                        if (setProvinceLimit.getpTwoO() == 0) {
                            provinceNotShell.setTwoO(-1);
                        } else {
                            int percent = (setProvinceLimit.getpTwoO() - provinceNotShell.getTwoO()) * 100 / setProvinceLimit.getpTwoO();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(200,provinceNotShell.getProvince(),1);
                            if (count > 50) {
                                provinceNotShell.setTwoO(0);
                            } else {
                                provinceNotShell.setTwoO(percent);
                            }
                        }
                        if (setProvinceLimit.getpTwoT() == 0) {
                            provinceNotShell.setTwoT(-1);
                        } else {
                            int percent = (setProvinceLimit.getpTwoT() - provinceNotShell.getTwoT()) * 100 / setProvinceLimit.getpTwoT();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(200,provinceNotShell.getProvince(),2);
                            if (count > 50) {
                                provinceNotShell.setTwoT(0);
                            } else {
                                provinceNotShell.setTwoT(percent);
                            }
                        }
                        if (setProvinceLimit.getpThree() == 0) {
                            provinceNotShell.setThree(-1);
                        } else {
                            int percent = (setProvinceLimit.getpThree() - provinceNotShell.getThree()) * 100 / setProvinceLimit.getpThree();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(300,provinceNotShell.getProvince(),0);
                            if (count > 50) {
                                provinceNotShell.setThree(0);
                            } else {
                                provinceNotShell.setThree(percent);
                            }
                        }
                        if (setProvinceLimit.getpFive() == 0) {
                            provinceNotShell.setFive(-1);
                        } else {
                            int percent = (setProvinceLimit.getpFive() - provinceNotShell.getFive()) * 100 / setProvinceLimit.getpFive();
                            if (percent <= 0) percent=0;
                            count = dao.queryByMsgIsNotShell(500,provinceNotShell.getProvince(),0);
                            if (count > 50) {
                                provinceNotShell.setFive(0);
                            } else {
                                provinceNotShell.setFive(percent);
                            }
                        }
                    }
                }
            }
        }

        all.addAll(provinceIsShells);
        all.addAll(provinceNotShells);
        return all;
    }


    /**
     * 查询壳子限量的数据
     * @param startTime
     * @param endTime
     */
    public List<ProvinceLimit> queryByIsShell(String startTime, String endTime) {
        //查询壳子限量
        StringBuffer sb = new StringBuffer();
        List<ProvinceLimit> provinceLimits = new ArrayList<ProvinceLimit>();
        List<SetProvinceLimit> setProvinceLimits = dao.queryProvinceLimit(1);
        if (setProvinceLimits != null && setProvinceLimits.size() > 0) {
            for (int i = 0; i < setProvinceLimits.size(); i++) {
                //2  0  1
                if (i + 1 != setProvinceLimits.size()) {
                    sb.append("'" + setProvinceLimits.get(i).getProvince()).append("',");
                } else {
                    sb.append("'" + setProvinceLimits.get(i).getProvince() + "'");
                }
            }
            //System.out.println(sb.toString());
            //查询出当日计费的数据
            provinceLimits = dao.queryIsShellProvince(startTime, endTime, sb.toString());
        }
        return provinceLimits;
    }


    /**
     * 查询分省限量数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ProvinceLimit> queryByNotIsShell(String startTime,String endTime) {
        //查询壳子限量
        StringBuffer sb = new StringBuffer();
        List<ProvinceLimit> provinceLimits = new ArrayList<ProvinceLimit>();
        List<SetProvinceLimit> setProvinceLimits = dao.queryProvinceLimit(2);
        if (setProvinceLimits != null && setProvinceLimits.size() > 0) {
            for (int i = 0; i < setProvinceLimits.size(); i++) {
                //2  0  1
                if (i + 1 != setProvinceLimits.size()) {
                    sb.append("'" + setProvinceLimits.get(i).getProvince()).append("',");
                } else {
                    sb.append("'" + setProvinceLimits.get(i).getProvince() + "'");
                }
            }
            //System.out.println(sb.toString());
            //查询出当日计费的数据
            provinceLimits = dao.queryNotShellProvince(startTime, endTime, sb.toString());
        }
        return provinceLimits;
    }









    @Test
    public void test1() {
        List<ProvinceLimit> query = queryAll("2019-11-25", "2019-11-25");
        for (ProvinceLimit provinceLimit : query) {
            System.out.println(provinceLimit.toString());
        }
    }

}
