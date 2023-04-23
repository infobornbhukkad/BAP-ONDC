package com.bb.beckn.api.model.update;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class UpdateMessage {
	private String updateTarget;
	private Order order;
}
