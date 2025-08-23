package com.pscs.moneyx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.OtpDataTabl;

@Repository
public interface OtpDataTablRepo extends JpaRepository<OtpDataTabl, Long> {

	OtpDataTabl findByUserIdAndOtp(String userId, String otp);

	OtpDataTabl findByRefNo(Long refNo);

	OtpDataTabl findByMobileNo(String mobileNo);

	OtpDataTabl findByEmailId(String emailId);

	OtpDataTabl findByUserIdAndChannel(String userId, String channel);
	OtpDataTabl findByUserIdAndOtpAndChannel(String userId, String otp, String channel);
	

}
