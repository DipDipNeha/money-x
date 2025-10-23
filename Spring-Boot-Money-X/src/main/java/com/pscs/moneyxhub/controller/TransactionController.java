/**
 * @author Dipak
 */

/**
 * 
 */
package com.pscs.moneyxhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.TransactionService;

/**
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// Get Banks
	@PostMapping("/getbank")
	public ResponseEntity<ResponseData> getBanks(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.getBanks(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Bank Account Name Enquiry
	@PostMapping("/accountnameenquiry")
	public ResponseEntity<ResponseData> accountNameEnquiry(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.accountNameEnquiry(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Inter Bank Transfer
	@PostMapping("/interbanktransfer")
	public ResponseEntity<ResponseData> interBankTransfer(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.interBankTransfer(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Transaction Status Re-Query
	@PostMapping("/transactionstatus")
	public ResponseEntity<ResponseData> transactionStatus(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.transactionStatus(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Wallet to Wallet Transfers
	@PostMapping("/transferWallet")
	public ResponseEntity<ResponseData> transferWallet(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.transferWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//		    Wallet to Wallet Requery
	@PostMapping("/requeryWalletTrans")
	public ResponseEntity<ResponseData> requeryWalletTrans(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.requeryWalletTrans(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Reverse Transaction
	@PostMapping("/reverseTxn")
	public ResponseEntity<ResponseData> reverseTxn(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.reverseTxn(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get Organization Wallet Transactions
	@PostMapping("/getOrgWalletTrans")
	public ResponseEntity<ResponseData> getOrgWalletTrans(@RequestBody RequestData requestBody) {
		ResponseData response = transactionService.getOrgWalletTrans(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
