package com.learn.workflow.approvalcredit.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import com.learn.workflow.approvalcredit.entity.ApprovalAuthority;
import com.learn.workflow.approvalcredit.repository.ApprovalAuthorityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EscalationListener implements ExecutionListener {
	private final ApprovalAuthorityRepository repository;

    @Override
    public void notify(DelegateExecution execution) {
    	String nextRole = (String) execution.getVariable("nextApprovalRole");

        // Final escalation reached → reject
        if (nextRole == null) {
            execution.setVariable("approvalType", "REJECT");
            log.warn("Final escalation reached. Loan rejected.");
            return;
        }

        ApprovalAuthority authority = repository
                .findByRole(nextRole)
                .orElseThrow(() ->
                        new IllegalStateException("Authority config not found: " + nextRole));

        // Set next approval layer
        execution.setVariable("approvalRole", authority.getRoleCode());
        execution.setVariable("requiredApprovals", authority.getRequiredApprovals());
        execution.setVariable("nextApprovalRole", authority.getNextRole());

        // Reset approval state for new layer
        execution.setVariable("approvalCount", 0);
        execution.setVariable("committeeApproved", false);

        // clear previous decision flag
        execution.removeVariable("approved");
        execution.removeVariable("approvalUser");

        log.info("Escalated to role={} requiredApprovals={}",
                authority.getRoleCode(),
                authority.getRequiredApprovals());
    }
}
