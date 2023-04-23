package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class OndcStatutoryReqsPackagedCommodities {
	private String manufacturerOrPackerName;
	private String manufacturerOrPackerAddress;
	private String commonOrGenericNameOfCommodity;
	private String multipleProductsNameNumberOrQty;
	private String netQuantityOrMeasureOfCommodityInPkg;
	private String monthYearOfManufacturePackingImport;
	private String importedProductCountryOfOrigin;
	private String contactDetailsConsumerCare;
}
