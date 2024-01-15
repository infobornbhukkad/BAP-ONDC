package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class Vehicle {
	private String category;
	private Integer capacity;
	private String make;
	private String model;
	private String size;
	private String variant;
	private String color;
	private String energyType;
	private String registration;
}
