package com.bb.beckn.api.model.response;

import lombok.Data;

@Data
public class ResponseAck {
	private Object context;
	private ResponseMessage message;
	private Object error;
}
