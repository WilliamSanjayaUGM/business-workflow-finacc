package com.learn.workflow.approvalcredit.listener;

import java.math.BigDecimal;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import com.learn.workflow.approvalcredit.entity.ApprovalAuthority;
import com.learn.workflow.approvalcredit.service.AuthorityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicAuthorityResolverListener implements ExecutionListener {

    private final AuthorityService authorityService;

    @Override
    public void notify(DelegateExecution execution) {

        BigDecimal amount = (BigDecimal) execution.getVariable("loanAmount");

        ApprovalAuthority authority =
                authorityService.resolveAuthority(amount);

        execution.setVariable("approvalRole", authority.getRoleCode());
        execution.setVariable("requiredApprovals", authority.getRequiredApprovals());
        execution.setVariable("nextApprovalRole", authority.getNextRole());

        execution.setVariable("approvalCount", 0);
        execution.setVariable("committeeApproved", false);
        
        execution.removeVariable("approved");
        execution.removeVariable("approvalUser");

        log.info("Dynamic authority resolved → role={} approvals={}",
                authority.getRoleCode(),
                authority.getRequiredApprovals());
    }
}
