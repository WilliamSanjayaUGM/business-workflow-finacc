package com.learn.workflow.controller;

import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.workflow.model.PaymentStartRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/workflow/payment")
@RequiredArgsConstructor
public class PaymentProcessController {
	private final RuntimeService runtimeService;

    @PostMapping("/start")
    public ResponseEntity<?> start(@Valid @RequestBody PaymentStartRequest req) {

        runtimeService.startProcessInstanceByKey(
            "multi-channel-payment-clearing",
            req.paymentId().toString(),
            Map.of(
                "paymentId", req.paymentId(),
                "channel", req.channel(),
                "idempotencyKey", req.idempotencyKey()
            )
        );

        return ResponseEntity.accepted().build();
    }
}
