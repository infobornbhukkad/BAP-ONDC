package com.bb.beckn;


import static com.bb.beckn.GlobalProperties.mySqlPwd;
import static com.bb.beckn.GlobalProperties.mySqlUrl;
import static com.bb.beckn.GlobalProperties.mySqlUserName;
import static com.bb.beckn.GlobalProperties.lifetime;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {

	private static HikariDataSource mDataSource;
	private static boolean isPoolInitialized = false;
	private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

	public static void initConnection() {
		HikariConfig mConfig = new HikariConfig();
		mConfig.setPoolName("BB_MYSQL_POOL");
		mConfig.setJdbcUrl(mySqlUrl);
		mConfig.setUsername(mySqlUserName);
		mConfig.setPassword(mySqlPwd);
		mConfig.setMaximumPoolSize(9);
		mConfig.setMaxLifetime(Long.parseLong(lifetime));
		mDataSource = new HikariDataSource(mConfig);
		isPoolInitialized = true;
	}

	public static void main(String[] args) throws SQLException {
		logger.info("Initialization ---------"+ getConnection().toString());
	}

	public static Connection getConnection() {
		Connection myRetrivedConn = null;
		try {
			if (isPoolInitialized) {
				myRetrivedConn = mDataSource.getConnection();
			} else {
				initConnection();
				myRetrivedConn = mDataSource.getConnection();
			}
		} catch (Exception e) {
			logger.error("getConnection : Unable to establish the connection to MySql : {{}}", e.getLocalizedMessage());
		}
		return myRetrivedConn;
	}

}
