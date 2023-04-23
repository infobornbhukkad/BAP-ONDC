package com.bb.beckn.api.model.lookup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LookupRequest_new {
	private String senderSubscriberId;
	private String requestId;
	private String timestamp;
	private String signature;
	private LookupRequestParam searchParameters;
}
