package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Price {
	private String currency;
	private String value;
	private String estimatedValue;
	private String computedValue;
	private String listedValue;
	private String offeredValue;
	private String minimumValue;
	private String maximumValue;

}
