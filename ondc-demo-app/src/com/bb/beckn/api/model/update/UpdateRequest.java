package com.bb.beckn.api.model.update;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class UpdateRequest {
	private Context context;
	private UpdateMessage message;
}