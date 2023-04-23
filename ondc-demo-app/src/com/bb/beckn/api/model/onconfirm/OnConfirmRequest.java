package com.bb.beckn.api.model.onconfirm;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnConfirmRequest {
	private Context context;
	private OnConfirmMessage message;
}
