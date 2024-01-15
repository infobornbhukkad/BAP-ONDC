package com.bb.beckn.api.model.common;

import java.util.List;

import lombok.Data;

@Data
public class Cancellation {
	private String type;
	private String refId;
	private List<Policy> policies;
	private String time;
	private String cancelledBy;
	private Option reasons;
	private Option selectedReason;
	private Descriptor additionalDescription;

}
