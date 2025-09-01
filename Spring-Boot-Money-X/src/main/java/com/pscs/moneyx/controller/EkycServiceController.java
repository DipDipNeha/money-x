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
	@PostMapping("/ekcy")
	public ResponseEntity<ResponseData> doEkyc(@RequestBody RequestData requestBody) {

		ResponseData response = ekycService.doEkyc(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	

}

