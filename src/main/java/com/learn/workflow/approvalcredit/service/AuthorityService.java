package com.learn.workflow.approvalcredit.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.learn.workflow.approvalcredit.entity.ApprovalAuthority;
import com.learn.workflow.approvalcredit.repository.ApprovalAuthorityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityService {
	
	private final ApprovalAuthorityRepository repository;

	public ApprovalAuthority resolveAuthority(BigDecimal amount) {
		List<ApprovalAuthority> authorities = repository.findByAmountOrdered(amount);

	    if (authorities.isEmpty()) {
	        throw new IllegalStateException(
	                "No authority configured for amount " + amount);
	    }
	    
	    if (authorities.size() > 1) {
	        log.error("Overlapping authority configuration detected for amount {}", amount);
	        throw new IllegalStateException("Overlapping authority configuration");
	    }

	    return authorities.get(0);
    }
}
