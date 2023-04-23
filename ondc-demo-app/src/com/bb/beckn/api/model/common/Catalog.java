package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Catalog {
	@JsonProperty("bpp/descriptor")
	private Descriptor bppDescriptor;

	@JsonProperty("bpp/categories")
	private List<Category> bppCategories;

	@JsonProperty("bpp/fulfillments")
	private List<Fulfillment> bppFulfillments;

	@JsonProperty("bpp/payments")
	private List<Payment> bppPayments;

	@JsonProperty("bpp/offers")
	private List<Offer> bppOffers;

	@JsonProperty("bpp/providers")
	private List<Provider> bppProviders;

	private String exp;
}
