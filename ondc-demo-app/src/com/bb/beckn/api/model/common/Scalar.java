package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Scalar {
	private String type;
	private Float value;
	private Float estimatedValue;
	private Float computedValue;
	private Range range;
	private String unit;
	private String code;
}
