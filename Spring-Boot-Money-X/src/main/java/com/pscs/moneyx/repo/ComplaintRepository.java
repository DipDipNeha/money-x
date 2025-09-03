package com.pscs.moneyx.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pscs.moneyx.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    List<Complaint> findByUserId(String userId);

	Complaint findByPaymentReference(String paymentReference);
}
