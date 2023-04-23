package com.bb.beckn.api.model.rating;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class OnRatingRequest {
	private Context context;
	private OnRatingMessage message;
}
