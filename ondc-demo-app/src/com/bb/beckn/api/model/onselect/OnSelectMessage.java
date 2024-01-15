package com.bb.beckn.api.model.onselect;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class OnSelectMessage {
	private Order order;
}
