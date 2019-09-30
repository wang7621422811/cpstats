package com.sms.init;

import com.sms.log.LogManager;
import com.sms.util.ReadConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


public class initSdk extends HttpServlet
{
  private static DataSource dssms = null;
  private static LogManager logger;

  public initSdk()
  {
	  logger = LogManager.getInstance(ReadConfig.KEY_LOG_PATH);
  }

  @Override
  public void init()throws ServletException
  {
    try
    {
      creatsmsConnectionPool();
    } catch (Exception ex) {
//      logger.log(ex);
    }
  }

  private static void creatsmsConnectionPool()
  {
    try
    {
//      Context ctx = new InitialContext();

//      String sDBDriver = "com.mysql.jdbc.Driver";
//      String sConnStr = "jdbc:mysql://182.92.236.122:3306/wmmp?characterEncoding=utf8";
//      String username = "sp";
//      String password = "sp2015$#@!";
//      Properties p = new Properties();
//      p.setProperty("driverClassName", sDBDriver);
//      p.setProperty("url", sConnStr);
//      p.setProperty("password", password);
//      p.setProperty("username", username);
//      p.setProperty("maxActive", "0");
//      p.setProperty("maxIdle", "10");
//      p.setProperty("maxWait", "1000");
//      p.setProperty("removeAbandoned", "false");
//      p.setProperty("removeAbandonedTimeout", "120");
//      p.setProperty("testOnBorrow", "true");
//      p.setProperty("logAbandoned", "true");
      
//      String sDBDriver = "com.mysql.jdbc.Driver";
//      String sConnStr = "jdbc:mysql://182.92.236.122:3306/wmmp?useUnicode=true&characterEncoding=utf8";
//      String username = "sp";
//      String password = "sp2015$#@!";
//      Properties p = new Properties();
//      p.setProperty("driverClassName", sDBDriver);
//      p.setProperty("url", sConnStr);
//      p.setProperty("password", password);
//      p.setProperty("username", username);
//      p.setProperty("maxActive", "0");
//      p.setProperty("maxIdle", "10");
//      p.setProperty("maxWait", "1000");
//      p.setProperty("removeAbandoned", "false");
//      p.setProperty("removeAbandonedTimeout", "120");
//      p.setProperty("testOnBorrow", "true");
//      p.setProperty("logAbandoned", "true");
      
      String sDBDriver = "com.mysql.jdbc.Driver";
      String sConnStr = "jdbc:mysql://101.200.120.159:3306/wmmp?useUnicode=true&characterEncoding=utf8";
      String username = "root";
      String password = "sp2019$#@!";
      Properties p = new Properties();
      p.setProperty("driverClassName", sDBDriver);
      p.setProperty("url", sConnStr);
      p.setProperty("password", password);
      p.setProperty("username", username);
      p.setProperty("maxActive", "100");
      p.setProperty("maxIdle", "100");
      p.setProperty("maxWait", "-1");
      p.setProperty("removeAbandoned", "false");
      p.setProperty("removeAbandonedTimeout", "120");
      p.setProperty("testOnBorrow", "true");
      p.setProperty("logAbandoned", "true");
      
      dssms = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
//      if (dssms != null){
//       /*logger.log("DB dssms pool created successfully!");*/
//    	  
//      }else{
////        logger.log("DB dssms pool created failed!");
//      }
    }
    catch (Exception nameEx) {
//      logger.log(nameEx);
    	System.out.println(nameEx);
    }
  }

  public static synchronized Connection getsmsConnection()
  {
    Connection conn = null;
    try
    {
      creatsmsConnectionPool();
      conn = dssms.getConnection();
    } catch (SQLException sqlEx) {
//      logger.log(sqlEx);
//      logger.log("Can not get a connection from dssms DB. Recreating connection pool ...");
    	System.out.println(sqlEx);
      creatsmsConnectionPool();
      try {
        conn = dssms.getConnection();
//        logger
//          .log("Retry getting connections from dssms DB successfully!");
      } catch (SQLException anotherSQLEx) {
//        logger.log(anotherSQLEx);
//        logger
//          .log("Retry getting connections from dssms DB failed!");
      }
    }

    return conn;
  }
  
  public static synchronized Connection getConnection()
	        throws SQLException
	{
	    if(dssms == null)
	    	creatsmsConnectionPool();
	    return dssms.getConnection();
	}

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
  }

  public void destroy()
  {
    dssms = null;
  }
}