package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QuotationBreakUp {

	@JsonProperty("@ondc/org/item_id")
	private String itemId;

	@JsonProperty("@ondc/org/item_quantity")
	private ItemQuantity itemQuantity;

	@JsonProperty("@ondc/org/title_type")
	private String titleType;
	private Item item;
	private String title;
	private Price price;
}
