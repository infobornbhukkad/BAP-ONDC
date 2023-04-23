package com.bb.beckn.api.model.cancel;

import com.bb.beckn.api.model.common.Descriptor;
import com.bb.beckn.api.model.common.Option;
import com.bb.beckn.api.model.common.Order;

import lombok.Data;

@Data
public class CancelMessage {
	//private Order orderId;
	private String cancellationReasonId;
	private Descriptor descriptor;
	private String orderId;
}