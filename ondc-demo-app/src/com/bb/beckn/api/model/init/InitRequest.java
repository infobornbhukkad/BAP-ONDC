package com.bb.beckn.api.model.init;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class InitRequest {
	private Context context;
	private InitMessage message;
}
