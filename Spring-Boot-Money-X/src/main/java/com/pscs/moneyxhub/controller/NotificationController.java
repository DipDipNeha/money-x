/**
 * @author Dipak
 */

/**
 * 
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
import com.pscs.moneyxhub.services.WebHookService;

/**
 * 
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	private final WebHookService webHookService;
	
	public NotificationController(WebHookService webHookService) {
		this.webHookService = webHookService;
	}
	
	@PostMapping("/fetch")
	public ResponseEntity<ResponseData> fetchNotification(@RequestBody RequestData requestBody) {

		ResponseData response = webHookService.fetchNotifications(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
