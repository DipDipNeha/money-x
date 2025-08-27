package com.pscs.moneyx.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.WalletAcctData;

@Repository
public interface WalletAcctDataRepository extends JpaRepository<WalletAcctData, String>{

	List<WalletAcctData> findByCustId(String custId);
	WalletAcctData findByByUsingRef(String bvnnin);
	WalletAcctData findByAcctNo(String accountNo);
	
}
