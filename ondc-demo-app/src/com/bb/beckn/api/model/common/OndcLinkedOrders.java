package com.bb.beckn.api.model.common;

import java.util.List;

import lombok.Data;

@Data
public class OndcLinkedOrders {
	private String id;
	
    private List<Item> items;
	
	private Order order;

}
