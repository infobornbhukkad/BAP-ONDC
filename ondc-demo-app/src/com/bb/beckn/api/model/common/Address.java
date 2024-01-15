package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Address {
	private String door;
	private String name;
	private String building;
	private String street;
	private String locality;
	private String ward;
	private String city;
	private String state;
	private String country;
	private String areaCode;

}
