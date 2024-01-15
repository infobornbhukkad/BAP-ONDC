package com.bb.beckn.api.model.lookup;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class NetworkParticipant implements Serializable {

	private static final long serialVersionUID = 6624153553629620347L;

	private String subscriberUrl;
	private String domain;
	private String callbackUrl;
	private String type;
	private Boolean msn;
	private List<String> cityCode;
	private List<SellerOnRecord> sellerOnRecord;
}
