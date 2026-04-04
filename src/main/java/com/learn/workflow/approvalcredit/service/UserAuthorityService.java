package com.learn.workflow.approvalcredit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.workflow.approvalcredit.entity.ApprovalUserAuthority;
import com.learn.workflow.approvalcredit.repository.ApprovalUserAuthorityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserAuthorityService {
	
	private final ApprovalUserAuthorityRepository repository;

    public List<String> getApprovers(String role) {

        List<ApprovalUserAuthority> users = repository.findActiveByRole(role);

        if (users.isEmpty()) {
            throw new IllegalStateException(
                    "No active approvers configured for role: " + role);
        }

        return users.stream()
                .map(ApprovalUserAuthority::getUsername)
                .toList();
    }

    public List<String> getApprovers(String role, String branch) {

    	List<ApprovalUserAuthority> users = repository.findByRoleAndBranch(role, branch);

        if (users.isEmpty()) {
            // Fallback to HO
            users = repository.findByRoleAndBranch(role, "HO");
        }

        if (users.isEmpty()) {
            throw new IllegalStateException("No active approvers configured for role: "
                            + role + " branch: " + branch);
        }

        return users.stream()
                .map(ApprovalUserAuthority::getUsername)
                .toList();
    }
}
