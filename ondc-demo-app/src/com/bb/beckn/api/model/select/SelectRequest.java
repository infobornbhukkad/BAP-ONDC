package com.bb.beckn.api.model.select;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class SelectRequest {
	private Context context;
	private SelectMessage message;
}