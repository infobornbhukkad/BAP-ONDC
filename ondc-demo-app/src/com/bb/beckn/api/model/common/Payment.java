package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Payment {
	private String uri;
	private String tlMethod;
	private PaymentParams params;
	private String type;
	private String status;
	private Time time;
	private String collectedBy;

	@JsonProperty("@ondc/org/collected_by_status")
	private String collectedByStatus;

	@JsonProperty("@ondc/org/buyer_app_finder_fee_type")
	private String buyerAppFinderFeeType;

	@JsonProperty("@ondc/org/buyer_app_finder_fee_amount")
	private String buyerAppFinderFeeAmount;

	@JsonProperty("@ondc/org/withholding_amount")
	private String withholdingAmount;

	@JsonProperty("@ondc/org/withholding_amount_status")
	private String withholdingAmountStatus;

	@JsonProperty("@ondc/org/return_window")
	private String returnWindow;

	@JsonProperty("@ondc/org/return_window_status")
	private String returnWindowStatus;

	@JsonProperty("@ondc/org/settlement_basis")
	private String settlementBasis;

	@JsonProperty("@ondc/org/settlement_basis_status")
	private String settlementBasisStatus;

	@JsonProperty("@ondc/org/settlement_window")
	private String settlementWindow;

	@JsonProperty("@ondc/org/settlement_window_status")
	private String settlementWindowStatus;

	@JsonProperty("@ondc/org/settlement_details")
	private List<SettlementDetails> settlementDetails;
}
