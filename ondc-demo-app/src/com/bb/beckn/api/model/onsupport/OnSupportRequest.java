package com.bb.beckn.api.model.onsupport;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnSupportRequest {
	private Context context;
	private OnSupportMessage message;
}
