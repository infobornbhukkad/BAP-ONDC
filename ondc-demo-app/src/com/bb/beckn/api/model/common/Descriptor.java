package com.bb.beckn.api.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Descriptor {
	private String name;
	private String code;
	// private List<String> symbol;
	private Object symbol;
	private String shortDesc;
	private String longDesc;
    private List<String> images;
	//private Object images;
	private String audio;

	@JsonProperty("3d_render")
	private String render3D;
}
