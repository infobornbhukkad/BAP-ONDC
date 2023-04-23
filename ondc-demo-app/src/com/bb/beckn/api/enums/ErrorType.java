package com.bb.beckn.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum ErrorType {

	GATEWAY("Gateway"),
	CONTEXT_ERROR("CONTEXT-ERROR"),
	CORE_ERROR("CORE-ERROR"),
	DOMAIN_ERROR("DOMAIN-ERROR"),
	POLICY_ERROR("POLICY-ERROR"),
	JSON_SCHEMA_ERROR("JSON-SCHEMA-ERROR");

	private final String value;

	private static final Map<String, ErrorType> CONSTANTS = new HashMap<>();

	static {
		for (ErrorType c : values()) {
			CONSTANTS.put(c.value, c);
		}
	}

	ErrorType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public String value() {
		return this.value;
	}

	public static ErrorType fromValue(String value) {
		ErrorType type = CONSTANTS.get(value);
		if (type == null) {
			throw new IllegalArgumentException(value);
		}
		return type;
	}

}