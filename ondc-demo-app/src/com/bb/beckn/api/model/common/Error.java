package com.bb.beckn.api.model.common;

// import com.nsdl.beckn.api.enums.ErrorType;

import lombok.Data;

@Data
public class Error {
	// private ErrorType type;
	private String type;
	private String code;
	private String path;
	private String message;
	private String desc;
}
