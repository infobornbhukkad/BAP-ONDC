package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class ItemQuantityOld {
	// private Integer count;
	private Allocated allocated;
	private Available available;
	private Maximum maximum;
	private Minimum minimum;
	private Selected selected;
}
