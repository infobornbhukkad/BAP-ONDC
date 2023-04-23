package com.bb.beckn.api.model.lookup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookupRequest {
	private String subscriberId;
	private String country;
	private String city;
	private String domain;
	private String type;
}
