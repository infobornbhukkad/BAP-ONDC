package com.bb.beckn.api.model.common;

import java.util.List;

import lombok.Data;

@Data
public class Offer {
	private String id;
	private Descriptor descriptor;
	private List<Location> locationIds;
	private List<Category> categoryIds;
	private List<Item> itemIds;
	private Time time;
}
