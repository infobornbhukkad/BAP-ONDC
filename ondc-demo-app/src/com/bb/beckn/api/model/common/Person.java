package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Person {
	private String name;
	private String image;
	private String dob;
	private String gender;
	private String cred;
	private Tags tags;
}
