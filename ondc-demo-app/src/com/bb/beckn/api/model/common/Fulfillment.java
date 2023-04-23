package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Fulfillment {
	private String id;
	private String type;

	@JsonProperty("@ondc/org/category")
	private String category;

	@JsonProperty("@ondc/org/TAT")
	private String tat;
    
	@JsonProperty("@ondc/org/awb_no")
	private String awbnumber;
	private String providerId;
	private Rating rating;
	private State state;
	private Boolean tracking;
	private Customer customer;
	private Agent agent;
	private Person person;
	private Contact contact;
	private Vehicle vehicle;
	private Start start;
	private End end;
	private Boolean rateable;
	private Tags tags;
	@JsonProperty("@ondc/org/provider_name")
	private String providerName;
}
