package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Provider {
	private String id;
	private Descriptor descriptor;
	private String categoryId;

	@JsonProperty("@ondc/org/fssai_license_no")
	private String fssaiLicenseNo;

	private Rating rating;
	private Time time;
	private List<Category> categories;
	private List<Fulfillment> fulfillments;
	private List<Payment> payments;
	private List<Location> locations;
	private List<Offer> offers;
	private List<Item> items;
	private String ttl;
	private String exp;
	private Boolean rateable;
	//private Tags tags;
	private List<Tags> tags;

}
