package com.bb.beckn.api.model.onsearch;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnSearchRequest {
	private Context context;
	private OnSearchMessage message;
}
