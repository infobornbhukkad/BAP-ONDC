package com.bb.beckn;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalPropsLoader {

	@Value("${hikari.config.mysql.url}")
	private String mySqlUrl;

	@Value("${hikari.config.mysql.username}")
	private String mySqlUserName;

	@Value("${hikari.config.mysql.pwd}")
	private String mySqlPwd;
	
	@Value("${hikari.max-lifetime}")
	private String lifetime;

	@Value("${spring.profiles.active}")
	private String myEnviroment;

	@PostConstruct
	public void appPropsInitialize() {
		GlobalProperties.mySqlUrl = mySqlUrl;
		GlobalProperties.mySqlUserName = mySqlUserName;
		GlobalProperties.mySqlPwd = mySqlPwd;
		GlobalProperties.lifetime = lifetime;
		GlobalProperties.myEnviroment = myEnviroment;
	}

}
