package com.learn.workflow.model;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanStartRequest(
		@NotNull
	    UUID loanApplicationId,

	    @NotNull
	    UUID customerId,

	    @NotBlank
	    String idempotencyKey
	    ) {

}
