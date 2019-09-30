package com.sms.log;
import java.io.*;
import java.text.*;
import java.util.*;

import com.sms.util.ReadConfig;
public class LogManager {
  static {
  }
  public static final String DEFAULT_LOG_PATH = ReadConfig.KEY_LOG_PATH;//缺省记录日志的目录
  public static final String LOG_EXT_NAME = ".txt";
  private String logRootPath=ReadConfig.KEY_LOG_PATH;//所有日志文件存放位置的根目录
  private static LogManager logger;
  private RandomAccessFile raf;
  private String lineSeparator;
  /**
   * Singleton模式
   * @param logRootPath 存放所有日志的根目录
   */
  private LogManager(String logRootPath)
  {
    if(logRootPath.endsWith("/") || logRootPath.endsWith("\\"))
    {
      this.logRootPath = logRootPath;
    }
    else
    {
      this.logRootPath = logRootPath+"/";
    }
    lineSeparator = System.getProperties().getProperty("line.separator");
  }

  /**
   * 获取LogManager的唯一实例
   * @param logRootPath 存放所有日志的根目录
   * @return LogManager的唯一实例
   */
  public static LogManager getInstance(String logRootPath)
  {
    if(logger==null)
    {
      if(logRootPath!=null)
        logger = new LogManager(logRootPath);
      else
        logger = new LogManager(DEFAULT_LOG_PATH);
    }

    return logger;
  }

  /**
   * 获取当前的日志文件名（以当前日期命名）
   * @return
   */
  private String getCurLogFileName()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    StringBuffer curLogFileName = new StringBuffer();

    curLogFileName.append(sdf.format(new Date(System.currentTimeMillis())));
    curLogFileName.append(LOG_EXT_NAME);

    return curLogFileName.toString();
  }

  /**
   * 把要记录的日志信息写入日志文件
   * @param info 要记录的信息对象，可以是字符串或者Exception类
   */
  public synchronized void log(Object info)
  {
    SimpleDateFormat sdf = null;
    StringBuffer newLog = new StringBuffer();//要写入的日志信息
    String logHeader = null;//每行日志的头部信息（日期时间）
    ByteArrayOutputStream baos = null;
    PrintStream ps = null;
    String curLogFileName = null;
    String curLogFilePath = null;
    File curLogFile = null;

    try
    {
      if(info==null)
        return;

      //STEP 1 : 组装要记录的日志信息
      sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
      logHeader = sdf.format(new Date(System.currentTimeMillis()));
      newLog.append(logHeader);
      if(!(info instanceof Exception))//如果是Exception对象则记录它的错误堆栈，否则记录对象的toString方法返回的内容
      {
        info = info.toString();
      }

      if(info instanceof String)
      {
        newLog.append(info);
      }
      else if(info instanceof Exception)
      {
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        ((Exception)info).printStackTrace(ps);
        newLog.append(lineSeparator);
        newLog.append(new String(baos.toByteArray()));
      }
      newLog.append(lineSeparator);

      //STEP 2 : 判断是否需要建立新的当天日志文件
      curLogFileName = getCurLogFileName();
      curLogFilePath = logRootPath+curLogFileName;
      curLogFile = new File(curLogFilePath);
      if(!curLogFile.exists())//当前应该写入的日志文件不存在，则创建新的日志文件
      {
        try
        {
          if(!curLogFile.createNewFile())//不能创建新的日志文件
          {
            System.out.print(logHeader+"The LogManager can not create new log file "+curLogFileName+".");
            return;
          }
          raf = new RandomAccessFile(curLogFile, "rw");
        }
        catch (IOException ioEx)
        {
          ioEx.printStackTrace();
          System.out.print(logHeader+"The LogManager can not create new log file "+curLogFileName+".");
        }
      }
      else//当前应该写入的日志文件已存在，则把RandomAccessFile指向该文件
      {
        try
        {
          raf = new RandomAccessFile(curLogFile, "rw");
        }
        catch (FileNotFoundException fnfEx)
        {
          fnfEx.printStackTrace();
          System.out.print(logHeader+"The LogManager can not create RandomAccessFile object.");
        }
      }

      //STEP 3 : 把日志信息写入到日志文件
      try
      {
        raf.seek(raf.length());//定位到日志文件的最后
        raf.write(newLog.toString().getBytes());
      }
      catch (IOException ioEx)
      {
        ioEx.printStackTrace();
        System.out.println(logHeader+"The LogManager can not write a new log.");
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      try
      {
        if(baos!=null)
          baos.close();
        if(ps!=null)
          ps.close();
        if(raf!=null)
          raf.close();
        curLogFile = null;
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
        System.out.println(logHeader+"The LogManager can not do the cleaning job!!!");
      }
    }
  }
}