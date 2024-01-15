package com.bb.beckn.api.model.response;

import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.common.Error;

import lombok.Data;

@Data
public class Response {
	private Context context;
	private ResponseMessage message;
	private Error error;
}
