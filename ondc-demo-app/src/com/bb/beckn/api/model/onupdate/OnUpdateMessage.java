package com.bb.beckn.api.model.onupdate;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class OnUpdateMessage {
	private Order order;
}
