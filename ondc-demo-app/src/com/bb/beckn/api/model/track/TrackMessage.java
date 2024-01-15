package com.bb.beckn.api.model.track;

import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class TrackMessage {
	private Order orderId;
	private String callbackUrl;
}