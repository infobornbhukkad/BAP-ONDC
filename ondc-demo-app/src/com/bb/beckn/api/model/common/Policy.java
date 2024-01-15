package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Policy {
	private String id;
	private Descriptor descriptor;
	private String parentPolicyId;
	private Time time;
}
