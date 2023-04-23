package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Authorization {
	private String type;
	private String token;
	private String validFrom;
	private String validTo;
	private String status;

}
