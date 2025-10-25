package com.pscs.moneyxhub.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pscs.moneyxhub.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    List<Complaint> findByUserId(String userId);

	Complaint findByPaymentReference(String paymentReference);
}
