package com.bb.beckn.api.model.ontrack;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnTrackRequest {
	private Context context;
	private OnTrackMessage message;
}