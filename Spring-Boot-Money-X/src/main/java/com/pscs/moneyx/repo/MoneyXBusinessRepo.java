package com.pscs.moneyx.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.MoneyXBusiness;

@Repository
public interface MoneyXBusinessRepo extends JpaRepository<MoneyXBusiness, Long> {
	List<MoneyXBusiness> findByBusinessName(String businessName);

	MoneyXBusiness findByUserNameAndPassword(String userName, String password);

	Optional<MoneyXBusiness> findByUserName(String userName);
	MoneyXBusiness findByAccountNumber(String accountNumber);
}
