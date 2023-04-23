package com.bb.beckn.api.model.status;

import com.bb.beckn.api.model.common.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatusMessage {
	//private Order orderId;
	
	@JsonProperty("order_id")
	private String orderId;
}
