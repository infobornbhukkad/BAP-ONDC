package com.bb.beckn.api.model.onsubscribe;

import lombok.Data;

@Data
public class OnSubscribeRequest {
	private String subscriberId;
	private String challenge;
}
