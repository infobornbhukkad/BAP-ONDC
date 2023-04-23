package com.bb.beckn.api.model.support;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class SupportRequest {
	private Context context;
	private SupportMessage message;
}
