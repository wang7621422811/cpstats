package com.sms.sms;

import com.sms.sms.pojo.ProvinceLimit;
import com.sms.sms.service.SdkService;
import com.sms.util.JdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: weibin
 * @Date: 2019/11/20 17:51
 * @Description: 新的渠道限量
 * @Version: 1.0
 */
//@WebServlet(urlPatterns = "sdk_residue_new")
public class SdkResidueNew  extends HttpServlet{

    private SdkService service = new SdkService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String startTime = req.getParameter("starttime");
        String endTime = req.getParameter("endtime");
        if (null == startTime) {
            startTime = nowDate;
        }
        if (null == endTime) {
            endTime = nowDate;
        }
        List<ProvinceLimit> provinceLimits = service.queryAll(startTime, endTime);
        System.out.println(provinceLimits);
        req.setAttribute("provinceLimits",provinceLimits);
        req.setAttribute("date",nowDate);
        req.getRequestDispatcher("/sdkallowance_new.jsp").forward(req,res);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
