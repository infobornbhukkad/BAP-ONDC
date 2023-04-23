package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Order {
	private String id;
	private String state;
	private Provider provider;
	private List<Item> items;
	private List<AddOn> addOns;
	private List<Offer> offers;
	private List<Document> documents;
	private Billing billing;
	//private Fulfillment fulfillment;
	private List<Fulfillment> fulfillments;
	private Quotation quote;
	private Payment payment;
	private String createdAt;
	private String updatedAt;

	@JsonProperty("@ondc/org/cancellation")
	private Cancellation cancellation;

	/*
	 * @JsonProperty("@ondc/org/linkedOrders") private List<OndcLinkedOrders>
	 * linkedOrders;
	 */
	
	@JsonProperty("@ondc/org/linkedOrders")
	private OndcLinkedOrders linkedOrders;

	private Location providerLocation;
	private Scalar weight;
	private Tags tags;
}
