package com.bb.beckn.api.model.lookup;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SellerOnRecord implements Serializable {

	private static final long serialVersionUID = 1297233841123490044L;

	private String uniqueKeyId;
	private List<String> cityCode;
	private KeyPair keyPair;

}
