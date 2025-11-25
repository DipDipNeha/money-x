/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.entity.CreditTransactionLog;
import com.pscs.moneyxhub.entity.MobCustomerMaster;
import com.pscs.moneyxhub.entity.WalletAcctData;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.CreditTransactionLogRepo;
import com.pscs.moneyxhub.repo.MobCustomerMasterRepo;
import com.pscs.moneyxhub.repo.WalletAcctDataRepository;

@Service
public class WebHookService {

	private final WalletAcctDataRepository walletAcctDataRepository;
	private final CreditTransactionLogRepo creditTransactionLogRepo;
	private final MobCustomerMasterRepo mobCustomerMasterRepo;

	public WebHookService(WalletAcctDataRepository walletAcctDataRepository,
			CreditTransactionLogRepo creditTransactionLogRepo, MobCustomerMasterRepo mobCustomerMasterRepo) {
		this.walletAcctDataRepository = walletAcctDataRepository;
		this.creditTransactionLogRepo = creditTransactionLogRepo;
		this.mobCustomerMasterRepo = mobCustomerMasterRepo;
	}

	public void processPayout(String sessionId, String debitAccountNumber, String creditAccountNumber,
			String debitAccountName, String creditAccountName, double amount, String currency, String status,
			String paymentReference, String dateOfTransaction, JSONObject requestData) {
		try {
			String userId = "";
			WalletAcctData byAcctNo = walletAcctDataRepository.findByAcctNo(creditAccountNumber);
			if (byAcctNo != null) {
				MobCustomerMaster byCustomerId = mobCustomerMasterRepo.findByCustomerId(byAcctNo.getCustId());
				if (byCustomerId != null) {
					userId = byCustomerId.getUserName();
				}

			} else {
				System.out.println("No wallet account found for credit account number: " + creditAccountNumber);
			}

			CreditTransactionLog log = new CreditTransactionLog();

			log.setUserId(userId);
			log.setSessionId(sessionId);
			log.setEventType("payout");
			log.setDebitAccountNumber(debitAccountNumber);
			log.setCreditAccountNumber(creditAccountNumber);
			log.setDebitAccountName(debitAccountName);
			log.setCreditAccountName(creditAccountName);
			log.setAmount(amount);
			log.setCurrency(currency);
			log.setStatus(status);
			log.setPaymentReference(paymentReference);
			log.setDateOfTransaction(dateOfTransaction);
			log.setProcessedAt(java.time.LocalDateTime.now().toString());
			log.setRemarks("Payout processed via webhook");
			log.setCreatedAt(new java.util.Date());
			log.setRequestData(requestData.toString());
			creditTransactionLogRepo.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void processCheckoutPayment(String transactionId, String walletId, String checkoutRef, double amount,
			String status, String message, String senderAccountNumber, String senderName, String senderBankCode,
			String recipientAccountNumber, String recipientName, String reference, String createdAt,
			JSONObject requestData) {
		try {
			String userId = "";
			WalletAcctData byAcctNo = walletAcctDataRepository.findByWalletAcctId(walletId);
			if (byAcctNo != null) {
				MobCustomerMaster byCustomerId = mobCustomerMasterRepo.findByCustomerId(byAcctNo.getCustId());
				if (byCustomerId != null) {
					userId = byCustomerId.getUserName();
				}

			} else {
				System.out.println("No wallet account found for credit account number: " + walletId);
			}
			CreditTransactionLog log = new CreditTransactionLog();
			log.setUserId(userId);
			log.setSessionId(transactionId);
			log.setEventType("checkout.payment.success");
			log.setDebitAccountNumber(senderAccountNumber);
			log.setCreditAccountNumber(recipientAccountNumber);
			log.setDebitAccountName(senderName);
			log.setCreditAccountName(recipientName);
			log.setAmount(amount);
			log.setCurrency("NGN");
			log.setStatus(status);
			log.setPaymentReference(reference);
			log.setDateOfTransaction(createdAt);
			log.setProcessedAt(java.time.LocalDateTime.now().toString());
			log.setRemarks("Checkout Payment processed via webhook");
			log.setCreatedAt(new java.util.Date());
			log.setRequestData(requestData.toString());
			creditTransactionLogRepo.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processNIP(String accountNumber, String reference, double amount, double fee, String senderName,
			String senderBank, String dateOfTransaction, String description, JSONObject requestData) {
		try {
			String userId = "";
			WalletAcctData byAcctNo = walletAcctDataRepository.findByAcctNo(accountNumber);
			if (byAcctNo != null) {
				MobCustomerMaster byCustomerId = mobCustomerMasterRepo.findByCustomerId(byAcctNo.getCustId());
				if (byCustomerId != null) {
					userId = byCustomerId.getUserName();
				}

			} else {
				System.out.println("No wallet account found for credit account number: " + accountNumber);

			}
			CreditTransactionLog log = new CreditTransactionLog();
			log.setUserId(userId);
			log.setSessionId(reference);
			log.setEventType("nip");
			log.setDebitAccountNumber("");
			log.setCreditAccountNumber(accountNumber);
			log.setDebitAccountName(senderName);
			log.setCreditAccountName("");
			log.setAmount(amount);
			log.setCurrency("NGN");
			log.setStatus("SUCCESS");
			log.setPaymentReference(reference);
			log.setDateOfTransaction(dateOfTransaction);
			log.setProcessedAt(java.time.LocalDateTime.now().toString());
			log.setRemarks("NIP transaction processed via webhook");
			log.setCreatedAt(new java.util.Date());
			log.setRequestData(requestData.toString());
			creditTransactionLogRepo.save(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResponseData fetchNotifications(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			String jsonHeader = ConvertRequestUtils.getJsonString(requestBody.getJheader());
			// Convert the request body to a JSON object
			JSONObject jbody = new JSONObject(jsonString);
			JSONObject jheader = new JSONObject(jsonHeader);
			
			 List<CreditTransactionLog> creditTransactionLog = creditTransactionLogRepo.findByUserId(jheader.getString("userid"));
			
			
			response.setResponseCode("00");
			response.setResponseMessage("Notifications fetched successfully");
			response.setResponseData(creditTransactionLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	

}
