package com.bb.beckn.api.model.subscribe;

import lombok.Data;

@Data
public class SubscribeRequest {
	private String subscriberId;
	private String country;
	private String city;
	private String domain;
	private String signingPublicKey;
	private String encrPublicKey;
	private String validFrom;
	private String validUntil;
	private String nonce;
}
