package com.learn.workflow.approvalcredit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learn.workflow.approvalcredit.entity.ApprovalUserAuthority;

@Repository
public interface ApprovalUserAuthorityRepository extends JpaRepository<ApprovalUserAuthority, Long> {
	
	@Query("""
	        SELECT u FROM ApprovalUserAuthority u
	        WHERE u.roleCode = :role
	        AND u.active = true
	    """)
	List<ApprovalUserAuthority> findActiveByRole(@Param("role") String role);
	
	@Query("""
		    SELECT u FROM ApprovalUserAuthority u
		    WHERE u.roleCode = :role
		    AND u.branchCode = :branch
		    AND u.active = true
		""")
	List<ApprovalUserAuthority> findByRoleAndBranch(@Param("role") String role, @Param("branch") String branch);
}
