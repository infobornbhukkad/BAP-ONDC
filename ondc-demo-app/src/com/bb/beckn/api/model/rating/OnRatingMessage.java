package com.bb.beckn.api.model.rating;

import lombok.Data;

@Data
public class OnRatingMessage {
	private Boolean feedbackAck;
	private Boolean ratingAck;
}