package com.learn.workflow.approvalcredit.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ComiteeVoteListener implements TaskListener {

	@Override
	public void notify(DelegateTask task) {
		Boolean approved = (Boolean) task.getVariable("approved");

        if (!Boolean.TRUE.equals(approved)) {
            return;
        }

        DelegateExecution execution = task.getExecution();

        Integer count = (Integer) execution.getVariable("approvalCount");
        Integer required = (Integer) execution.getVariable("requiredApprovals");

        if (count == null) {
            count = 0;
        }

        count++;

        execution.setVariable("approvalCount", count);

        log.info("Committee vote recorded. count={} required={}", count, required);

        if (required != null && count >= required) {
            execution.setVariable("committeeApproved", true);
            log.info("Committee approval threshold reached.");
        }
	}

}
