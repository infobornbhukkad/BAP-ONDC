package com.bb.beckn.api.model.onrating;

import java.util.List;

import com.bb.beckn.api.model.common.FeedbackForm;
import com.bb.beckn.api.model.common.FeedbackUrl;

import lombok.Data;

@Data
public class RatingMessage {
	private String ratingCategory;
	private String id;
	private Float value;
	private List<FeedbackForm> feedbackForm;
	private FeedbackUrl feedbackId;
}