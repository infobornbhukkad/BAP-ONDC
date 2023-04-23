package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Category {
	private String id;
	private String parentCategoryId;
	private Descriptor descriptor;
	private Time time;
	private Tags tags;
}
