package com.pscs.moneyxhub.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyxhub.entity.WalletAcctData;

@Repository
public interface WalletAcctDataRepository extends JpaRepository<WalletAcctData, Long>{

	WalletAcctData findByCustId(String customerId);

	WalletAcctData findByAcctNo(String creditAccountNumber);

	WalletAcctData findByWalletAcctId(String walletId);
	
}
