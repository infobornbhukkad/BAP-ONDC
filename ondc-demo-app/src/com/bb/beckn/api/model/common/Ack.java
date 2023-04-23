package com.bb.beckn.api.model.common;

import com.bb.beckn.api.enums.AckStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ack {
	private AckStatus status;
}