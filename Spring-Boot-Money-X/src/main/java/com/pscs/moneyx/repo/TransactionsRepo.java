package com.pscs.moneyx.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pscs.moneyx.entity.Transactions;

@Repository
public interface TransactionsRepo extends JpaRepository <Transactions, Long> {
    // Define custom query methods if needed
	
	List<Transactions> findByAcctNo(String acctNo);
	List<Transactions> findByAcctNoAndTxnDate(String acctNo, String txnDate);
//	Transactions findByAcctNoAndTxnDate(String actcNo, String txnDate, String paymentReference);
	Transactions findByPaymentReference(String paymentReference);
	
	

}
