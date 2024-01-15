package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Start {
	private Location location;
	private Time time;
	private Descriptor instructions;
	private Contact contact;
	private Person person;
	private Authorization authorization;
}
