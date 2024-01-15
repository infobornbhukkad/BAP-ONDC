package com.bb.beckn.api.model.oninit;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnInitRequest {
	private Context context;
	private OnInitMessage message;
}