package com.bb.beckn.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Location {
	private String id;
	private Descriptor descriptor;
	private String gps;
	private Address address;
	private String stationCode;
	private City city;
	private Country country;
	private Circle circle;
	private String polygon;

	@JsonProperty("3dspace")
	private String space3D;

	private Time time;
	private Boolean rateable;
}
