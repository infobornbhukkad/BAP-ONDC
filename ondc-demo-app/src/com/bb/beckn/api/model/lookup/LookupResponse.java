package com.bb.beckn.api.model.lookup;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LookupResponse implements Serializable {

	private static final long serialVersionUID = 8260095980978686681L;

	private String subscriberId;
	private String country;
	private String city;
	private String domain;
	private String signingPublicKey;
	private String encrPublicKey;
	private String validFrom;
	private String validUntil;
	private String created;
	private String updated;

	// below fields are from older version
	private String type;
	private String subscriberUrl;
	private String brId;
	private String status;
	@JsonProperty("ukId")
	private String uniqueKeyId;
}
