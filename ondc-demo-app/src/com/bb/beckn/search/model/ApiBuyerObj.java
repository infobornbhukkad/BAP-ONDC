package com.bb.beckn.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "api_audit_buyer")
public class ApiBuyerObj {

	@Id
	private int id;
	
	@NotBlank
	private String message_id;

	@NotBlank
	private String transaction_id;

	@NotBlank
	private String action;

	@NotBlank
	private String domain;

	private String core_version;

	@NotBlank
	private String created_on;

	@NotBlank
	private String json;

	@NotBlank
	private String status;

	@NotBlank
	private String search_criteria;

	
	
	public ApiBuyerObj() {
		super();
	}

	public ApiBuyerObj(String message_id, String transaction_id, String action, String domain, String core_version, String created_on,
			String json, String status, String search_criteria) {
		this.message_id = message_id;
		this.transaction_id = transaction_id;
		this.action = action;
		this.domain = domain;
		this.core_version = core_version;
		this.created_on = created_on;
		this.json = json;
		this.status = status;
		this.search_criteria = search_criteria;
		
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCore_version() {
		return core_version;
	}

	public void setCore_version(String core_version) {
		this.core_version = core_version;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearch_criteria() {
		return search_criteria;
	}

	public void setSearch_criteria(String search_criteria) {
		this.search_criteria = search_criteria;
	}
    
	

}
