package com.bb.beckn.api.model.status;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class StatusRequest {
	private Context context;
	private StatusMessage message;
}
