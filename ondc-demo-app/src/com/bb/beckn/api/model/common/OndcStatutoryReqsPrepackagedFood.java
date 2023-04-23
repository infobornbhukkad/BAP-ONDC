package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OndcStatutoryReqsPrepackagedFood {
	private String ingredientsInfo;
	private String nutritionalInfo;
	private String additivesInfo;
	private String manufacturerOrPackerName;
	private String manufacturerOrPackerAddress;
	private String brandOwnerName;
	private String brandOwnerAddress;

	@JsonProperty("brand_owner_FSSAI_logo")
	private String brandOwnerFSSAILogo;

	@JsonProperty("brand_owner_FSSAI_license_no")
	private String brandOwnerFSSAILicenseNo;

	@JsonProperty("other_FSSAI_license_no")
	private String otherFSSAILicenseNo;

	private String netQuantity;
	private String importerName;
	private String importerAddress;

	@JsonProperty("importer_FSSAI_logo")
	private String importerFSSAILogo;

	@JsonProperty("importer_FSSAI_license_no")
	private String importerFSSAILicenseNo;

	private String importedProductCountryOfOrigin;
	private String otherImporterName;
	private String otherImporterAddress;
	private String otherPremises;
	private String otherImporterCountryOfOrigin;
	private String contactDetailsConsumerCare;
}
