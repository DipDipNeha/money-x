/**
 * @author Dipak
 */

package com.pscs.moneyxhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.WebCheckOutService;

@RestController
@RequestMapping("/api/webcheckout")
public class WebCheckOutController {

	private  final WebCheckOutService webCheckOutService ;
	
	public WebCheckOutController(WebCheckOutService webCheckOutService) {
		this.webCheckOutService = webCheckOutService;
	}
	
	
	//load plan and pricing page and packages
	@PostMapping("/loadpackage" )
	public ResponseEntity<ResponseData> loadPackage(@RequestBody RequestData requestBody) {

		ResponseData response = webCheckOutService.loadPackage(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//get transaction status
	@PostMapping("/gettransactionstatus" )
	public ResponseEntity<ResponseData> getTransactionStatus(@RequestBody RequestData requestBody) {

		ResponseData response = webCheckOutService.getTransactionStatus(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PostMapping("/initiatedpayment")
	public ResponseEntity<ResponseData> initiatedPayment(@RequestBody RequestData requestBody) {

		ResponseData response = webCheckOutService.initiatedPayment(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
