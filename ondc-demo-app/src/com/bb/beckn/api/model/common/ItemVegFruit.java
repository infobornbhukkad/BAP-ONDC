package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemVegFruit {
	
	@JsonProperty("net_quantity")
	private String additionalProp1;
	
	@JsonProperty("value")
	private String additionalProp2;
	
	/*
	 * private String location; private String category; private String type;
	 * private String val; private String unit;
	 * 
	 * //private String additionalProp1; //private String additionalProp2; private
	 * String additionalProp3; private String additionalProp4; private String
	 * additionalProp5;
	 */
}
