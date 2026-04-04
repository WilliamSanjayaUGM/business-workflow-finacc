package com.learn.workflow.approvalcredit.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learn.workflow.approvalcredit.entity.ApprovalAuthority;

@Repository
public interface ApprovalAuthorityRepository extends JpaRepository<ApprovalAuthority, Long> {
	
	@Query("""
		    SELECT a FROM ApprovalAuthority a
		    WHERE a.active = true
		    AND :amount >= a.minAmount
		    AND (a.maxAmount IS NULL OR :amount <= a.maxAmount)
		    ORDER BY a.minAmount DESC
		""")
	List<ApprovalAuthority> findByAmountOrdered(@Param("amount") BigDecimal amount);
	
	@Query("""
		    SELECT a FROM ApprovalAuthority a
		    WHERE a.roleCode = :role
		    AND a.active = true
		""")
	Optional<ApprovalAuthority> findByRole(@Param("role") String role);
}
