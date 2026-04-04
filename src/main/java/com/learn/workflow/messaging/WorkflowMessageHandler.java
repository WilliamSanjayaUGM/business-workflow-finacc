package com.learn.workflow.messaging;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WorkflowMessageHandler {
	private final RuntimeService runtimeService;
	
	public void correlate(String message, String businessKey) {
        runtimeService.createMessageCorrelation(message)
            .processInstanceBusinessKey(businessKey)
            .correlate();
    }
}
