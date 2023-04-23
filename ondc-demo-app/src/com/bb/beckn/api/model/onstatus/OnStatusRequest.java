package com.bb.beckn.api.model.onstatus;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnStatusRequest {
	private Context context;
	private OnStatusMessage message;
}