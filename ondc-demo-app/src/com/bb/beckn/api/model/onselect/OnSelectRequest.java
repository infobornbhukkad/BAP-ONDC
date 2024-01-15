package com.bb.beckn.api.model.onselect;

import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.common.Scalar;

import lombok.Data;

@Data
public class OnSelectRequest {
	private Context context;
	private OnSelectMessage message;
	private Scalar error;
}