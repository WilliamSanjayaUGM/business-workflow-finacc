package com.learn.workflow.approvalcredit.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "approval_user_authority")
@Data
public class ApprovalUserAuthority {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String username;
    
    @Column(name = "role_code")
    private String roleCode;
    
    @Column(name = "branch_code")
    private String branchCode;

    private Boolean active;
}
