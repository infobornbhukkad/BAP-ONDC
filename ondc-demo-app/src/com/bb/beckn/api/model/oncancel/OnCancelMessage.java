package com.bb.beckn.api.model.oncancel;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class OnCancelMessage {
	private Order order;
}
