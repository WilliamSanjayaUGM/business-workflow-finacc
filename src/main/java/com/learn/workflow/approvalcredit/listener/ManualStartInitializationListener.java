package com.learn.workflow.approvalcredit.listener;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import com.learn.workflow.approvalcredit.service.UserAuthorityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManualStartInitializationListener implements ExecutionListener {
	private final UserAuthorityService userAuthorityService;

    @Override
    public void notify(DelegateExecution execution) {

    	String role = (String) execution.getVariable("approvalRole");
        String branch = (String) execution.getVariable("branchCode");

        List<String> users = userAuthorityService.getApprovers(role, branch);

        if ("CREDIT_COMMITTEE".equals(role)) {
            execution.setVariable("committeeMembers", users);
        } else {
            execution.setVariable("approvers", users);
        }

        execution.setVariable("approvalCount", 0);
        execution.setVariable("committeeApproved", false);
        
        execution.removeVariable("approved");
        execution.removeVariable("approvalUser");

        log.info("Loaded {} approvers for role {}", users.size(), role);
    }

}
