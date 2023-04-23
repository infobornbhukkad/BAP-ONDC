package com.bb.beckn.api.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ContextAction {

	SEARCH("search"),
	SELECT("select"),
	INIT("init"),
	CONFIRM("confirm"),
	UPDATE("update"),
	STATUS("status"),
	TRACK("track"),
	CANCEL("cancel"),
	RATING("rating"),
	FEEDBACK("feedback"),
	SUPPORT("support"),
	ON_SEARCH("on_search"),
	ON_SELECT("on_select"),
	ON_INIT("on_init"),
	ON_CONFIRM("on_confirm"),
	ON_UPDATE("on_update"),
	ON_STATUS("on_status"),
	ON_TRACK("on_track"),
	ON_CANCEL("on_cancel"),
	ON_FEEDBACK("on_feedback"),
	ON_RATING("on_rating"),
	ON_SUPPORT("on_support"),
	ACK("ack");
	private final String value;
	private static final Map<String, ContextAction> CONSTANTS = new HashMap<>();

	static {
		for (ContextAction c : values()) {
			CONSTANTS.put(c.value, c);
		}
	}

	ContextAction(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@JsonValue
	public String value() {
		return this.value;
	}

	@JsonCreator
	public static ContextAction fromValue(String value) {
		ContextAction action = CONSTANTS.get(value);
		if (action == null) {
			throw new IllegalArgumentException(value);
		}
		return action;
	}

}