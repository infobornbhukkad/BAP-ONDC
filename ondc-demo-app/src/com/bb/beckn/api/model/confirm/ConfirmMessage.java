package com.bb.beckn.api.model.confirm;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class ConfirmMessage {
	private Order order;
}
