package com.bb.beckn.api.enums;

public enum AckStatus {

	ACK("ACK"),
	NACK("NACK");

	private final String value;

	AckStatus(String value) {
		this.value = value;
	}

}