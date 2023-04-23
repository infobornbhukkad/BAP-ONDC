package com.bb.beckn.api.model.search;

import com.bb.beckn.api.model.common.Context;

import lombok.Data;

@Data
public class SearchRequest {
	private Context context;
	private SearchMessage message;
}
