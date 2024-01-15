package com.bb.beckn.api.model.lookup;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeyPair implements Serializable {

	private static final long serialVersionUID = -6203807377786017215L;

	private String signingPublicKey;
	private String encryptionPublicKey;
	private String validFrom;
	private String validUntil;

}
