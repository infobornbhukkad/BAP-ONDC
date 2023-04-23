package com.bb.beckn.api.model.init;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class InitMessage {
	private Order order;
}
