package com.learn.workflow.approvalcredit.listener;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApprovalAuditListener implements ExecutionListener {
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String processInstanceId = execution.getProcessInstanceId();
        String businessKey = execution.getBusinessKey();
        String activityId = execution.getCurrentActivityId();
        String approver = (String) execution.getVariable("approvalUser");
        Boolean approved = (Boolean) execution.getVariable("approved");
        String approvalLevel = (String) execution.getVariable("approvalRole");
        
        if (approver != null && approver.equals(execution.getVariable("initiator"))) {
        	log.error("Self approval detected for businessKey={}", businessKey);
            throw new BpmnError("SELF_APPROVAL", "Self approval detected");
        }

        Map<String, Object> auditRecord = new HashMap<>();
        auditRecord.put("timestamp", Instant.now());
        auditRecord.put("processInstanceId", processInstanceId);
        auditRecord.put("businessKey", businessKey);
        auditRecord.put("activityId", activityId);
        auditRecord.put("approver", approver);
        auditRecord.put("approved", approved);
        auditRecord.put("approvalLevel", approvalLevel);

        // Send to audit system / Kafka / DB
        log.info("APPROVAL_AUDIT {}", auditRecord);
	}
}
