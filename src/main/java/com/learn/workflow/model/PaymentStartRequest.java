package com.learn.workflow.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentStartRequest(
		@NotNull
	    UUID paymentId,

	    @NotBlank
	    PaymentChannel channel,

	    @NotBlank
	    String idempotencyKey
	    ) {

}
