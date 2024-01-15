package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Context {
	private String domain;
	private String country;
	private String city;
	// private ContextAction action;
	private String action;
	private String coreVersion;
	private String bapId;
	private String bapUri;
	private String bppId;
	private String bppUri;
	private String transactionId;
	private String messageId;
	private String timestamp;
	private String key;
	private String ttl;
}