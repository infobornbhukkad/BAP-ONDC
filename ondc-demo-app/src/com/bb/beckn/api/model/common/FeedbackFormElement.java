package com.bb.beckn.api.model.common;

import lombok.Data;

@Data
public class FeedbackFormElement {
	private String id;
	private String parentId;
	private String question;
	private String answer;
	private String answerType;

}
