package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Billing {
	private String name;
	private Organization organization;
	private Address address;
	private String email;
	private String phone;
	private Time time;
	private String taxNumber;
	private String createdAt;
	private String updatedAt;
}
