package com.bb.beckn.api.model.onconfirm;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class OnConfirmMessage {
	private Order order;
}
