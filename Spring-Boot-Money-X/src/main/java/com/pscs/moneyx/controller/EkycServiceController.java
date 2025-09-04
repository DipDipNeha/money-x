/**
 * 
 */
package com.pscs.moneyx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.EkycService;

/**
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/api/ekycservice")
public class EkycServiceController {

	private final EkycService ekycService;

	public EkycServiceController(EkycService ekycService) {
		this.ekycService = ekycService;
	}

	@PostMapping("/ekyc")
	public ResponseEntity<ResponseData> doEkyc(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.doEkyc(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/verifydocument")
	public ResponseEntity<ResponseData> verifyDocument(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.verifyDocument(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/businessdocverify")
	public ResponseEntity<ResponseData> verifyFace(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.businessDocVerify(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/livelinesscheck")
	public ResponseEntity<ResponseData> livelinessCheck(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.livelinessCheck(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	@PostMapping("/accountenquiry")
	public ResponseEntity<ResponseData> accountEnquiry(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.accountEnquiry(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	

}
