package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Item {
	private String id;
	private ItemQuantity quantity;
	private String parentItemId;
	private Descriptor descriptor;
	private Price price;
	// private Category categoryId;
	private String categoryId;
	// private Fulfillment fulfillmentId;
	private String fulfillmentId;
	// private Rating rating;
	private Float rating;
	// private Location locationId;
	private String locationId;
	private Time time;
	private Boolean rateable;
	private Boolean matched;
	private Boolean related;
	private Boolean recommended;

	@JsonProperty("@ondc/org/returnable")
	private Boolean returnable;

	@JsonProperty("@ondc/org/seller_pickup_return")
	private Boolean sellerPickupReturn;

	@JsonProperty("@ondc/org/return_window")
	private String returnWindow;

	@JsonProperty("@ondc/org/cancellable")
	private Boolean cancellable;

	@JsonProperty("@ondc/org/time_to_ship")
	private String timeToShip;

	@JsonProperty("@ondc/org/available_on_cod")
	private Boolean availableOnCod;

	@JsonProperty("@ondc/org/contact_details_consumer_care")
	private String contactDetailsConsumerCare;

	@JsonProperty("@ondc/org/statutory_reqs_packaged_commodities")
	private OndcStatutoryReqsPackagedCommodities statutoryReqsPackagedCommodities;

	@JsonProperty("@ondc/org/statutory_reqs_prepackaged_food")
	private OndcStatutoryReqsPrepackagedFood statutoryReqsPrepackagedFood;

	@JsonProperty("@ondc/org/mandatory_reqs_veggies_fruits")
	private ItemVegFruit mandatoryReqsVeggiesFruits;

	private Tags tags;

}
