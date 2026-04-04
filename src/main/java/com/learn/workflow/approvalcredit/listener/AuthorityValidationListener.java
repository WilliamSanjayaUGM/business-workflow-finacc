package com.learn.workflow.approvalcredit.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorityValidationListener implements ExecutionListener {
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Integer required = (Integer) execution.getVariable("requiredApprovals");

        if (required != null && required > 1) {
            execution.setVariable("multiLevelApproval", true);
        } else {
            execution.setVariable("multiLevelApproval", false);
        }
	}

}
