package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pscs.moneyxhub.entity.OtpDataTabl;

@Repository
public interface OtpDataTablRepo extends JpaRepository<OtpDataTabl, Long> {

	OtpDataTabl findByUserIdAndOtp(String userId, String otp);

	OtpDataTabl findByRefNo(Long refNo);

	OtpDataTabl findByMobileNo(String mobileNo);

	OtpDataTabl findByEmailId(String emailId);

	OtpDataTabl findByUserIdAndChannel(String userId, String channel);
	OtpDataTabl findByUserIdAndOtpAndChannel(String userId, String otp, String channel);
	@Modifying
	@Transactional
	@Query("UPDATE OtpDataTabl u SET u.otpStatus = :newStatus WHERE u.userId = :userId AND u.otpStatus = 'A'")
	int updateOtpStatusByUserId(String userId, String newStatus);

	OtpDataTabl findByUserIdAndOtpAndOtpStatus(String username, String b64_sha256, String string);

	

}
