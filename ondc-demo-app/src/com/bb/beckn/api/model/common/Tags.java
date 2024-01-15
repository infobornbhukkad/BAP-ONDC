package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Tags {
	@JsonProperty("code")
	private String additionalProp1;

	@JsonProperty("veg")
	private String additionalProp2;

	@JsonProperty("non_veg")
	private String additionalProp3;
	
	@JsonProperty("@ondc/org/order_ready_to_ship")
	private String readytoship;
	
	//private list list;
	private List<list> list;
	private String cancellationReasonId;
	private String status;
}
