package com.bb.beckn.api.model.cancel;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class CancelRequest {
	private Context context;
	private CancelMessage message;
}
