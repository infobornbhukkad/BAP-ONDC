package com.bb.beckn.api.model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tracking {
	private String url;
	private String status;
}