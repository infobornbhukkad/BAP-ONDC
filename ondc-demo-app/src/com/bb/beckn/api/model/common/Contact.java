package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Contact {
	private String phone;
	private String email;
	private Tags tags;
}
