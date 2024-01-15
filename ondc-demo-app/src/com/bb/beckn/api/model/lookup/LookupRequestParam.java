package com.bb.beckn.api.model.lookup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LookupRequestParam {
	private String country;
	private String domain;
	private String type;
	private String city;
	private String subscriberId;
}
