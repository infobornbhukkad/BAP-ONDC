package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class ItemQuantity {
	private ItemQuantities allocated;
	private ItemQuantities available;
	private ItemQuantities maximum;
	private ItemQuantities minimum;
	private ItemQuantities selected;

	private Integer count; // this is not correct as per schema
	private Scalar measure;
}
