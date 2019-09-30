package com.sms.util;

import java.io.*;
import java.util.*;

/**
 * <p>
 * Title: Config
 * </p>
 * <p>
 * Description: 配置管理器。加载所有配。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * @author zgl
 * @version 1.0
 */

public class ReadConfig extends Thread {
	// 配置文件的键
	public static final String KEY_GwID = "GwID";
	public static final String KEY_CorpID = "CorpID";
	public static final String KEY_Passwd = "Passwd";
	public static final String KEY_LOG_PATH = "log";//LogPath

	private static ReadConfig apcConfig;
	public static String ConfPath = "config.cfg"; // 全局变量：配置路径
	private Properties propConf = new Properties();// 存储基本配置文件

	/**
	 * 初始化
	 * 
	 * @param configPath
	 *            配置文件的存放路径
	 */
	private ReadConfig(String configPath) {
		try {
			propConf.load(new FileInputStream(configPath));// 加载基本配置文件
			start();// 启动线程扫描开关控制文件
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			System.out.println(ioEx);
		}
	}

	/**
	 * Singleton
	 * 
	 * @param confPath
	 * @return
	 */
	public static ReadConfig getInstance(String confPath) {
		if (apcConfig == null) {
			apcConfig = new ReadConfig(confPath);
		}

		return apcConfig;
	}

	/**
	 * 根据指定的key值获取对应的配置信息
	 * 
	 * @param key
	 *            要查询的配置
	 * @return 对应的配置信息
	 */
	public String getConfig(String key) {
		String value = null;

		value = propConf.getProperty(key);

		return value;
	}

	/**
	 * 输出所有属性值
	 * 
	 * @return
	 */
	public String toString() {
		StringBuffer info = new StringBuffer();
		Enumeration enums = null;
		String key = null;
		String value = null;
		enums = propConf.keys();
		info.append("\n");
		while (enums.hasMoreElements()) {
			key = (String) enums.nextElement();
			value = (String) propConf.get(key);
			info.append(key).append(" : ").append(value).append("\n");
		}

		return info.toString();
	}
}
