package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class AddOn {
	private String id;
	private Descriptor descriptor;
	private Price price;
}
