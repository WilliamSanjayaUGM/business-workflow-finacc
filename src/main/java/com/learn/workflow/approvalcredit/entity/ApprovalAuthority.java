package com.learn.workflow.approvalcredit.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "approval_authority_matrix")
@Data
public class ApprovalAuthority {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Version
    private Long version;
	
	@Column(name = "role_code")
    private String roleCode;
	
	@Column(name = "min_amount")
    private BigDecimal minAmount;
	
	@Column(name = "max_amount")
    private BigDecimal maxAmount;
	
	@Column(name = "required_approvals")
    private Integer requiredApprovals;
	
	@Column(name = "next_role")
    private String nextRole;

    private Boolean active;
}
