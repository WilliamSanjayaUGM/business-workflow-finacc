package com.learn.workflow.controller;

import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.workflow.model.LoanStartRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workflow/loan")
@RequiredArgsConstructor
public class LoanProcessController {
	private final RuntimeService runtimeService;
	
	@PostMapping("/start")
    public ResponseEntity<?> start(@Valid @RequestBody LoanStartRequest req) {

        String businessKey = req.loanApplicationId().toString();

        runtimeService.startProcessInstanceByKey(
            "loan-origination",
            businessKey,
            Map.of(
                "loanApplicationId", req.loanApplicationId(),
                "customerId", req.customerId(),
                "idempotencyKey", req.idempotencyKey()
            )
        );

        return ResponseEntity.accepted().build();
    }
}
