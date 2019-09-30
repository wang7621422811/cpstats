package com.sms.sms;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: weibin
 * @Date: 2019/7/30 17:11
 * @Description: 重写sdk查询余量模块
 * @Version: 1.0
 */
public class SdkResidue_Test {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 	SDK余量查询
     * @param starttime  查询开始时间
     * @param endtime	 查询截止时间
     * @return		     返回查询结果的集合
     */
    public List<String> getallowanceNew(String starttime, String endtime){
        List<String> list = new ArrayList<String>();
        Date currentTime = new Date();
        //当前时间
        String times = formatter.format(currentTime).substring(0, 10);

        Connection connByAll = null;
        Connection ConnBySplit = null;











        return null;
    }

}
