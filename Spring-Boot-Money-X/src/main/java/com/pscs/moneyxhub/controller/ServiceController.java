package com.pscs.moneyxhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.ServiceBusinessService;

@CrossOrigin
@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceBusinessService serviceBusinessService;

    public ServiceController(ServiceBusinessService serviceBusinessService) {
        this.serviceBusinessService = serviceBusinessService;
    }

    // 1. echo api
    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        return new ResponseEntity<>("Service Controller is up!", HttpStatus.OK);
    }

    // 2. view balance
    @PostMapping("/view-balance")
    public ResponseEntity<ResponseData> viewBalance(@RequestBody RequestData requestBody) {
        ResponseData response = serviceBusinessService.viewBalance(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 3. mini statement
    @PostMapping("/mini-statement")
    public ResponseEntity<ResponseData> miniStatement(@RequestBody RequestData requestBody) {
        ResponseData response = serviceBusinessService.miniStatement(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 4. full statement
    @PostMapping("/full-statement")
    public ResponseEntity<ResponseData> fullStatement(@RequestBody RequestData requestBody) {
        ResponseData response = serviceBusinessService.fullStatement(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 5. instant receipts
    @PostMapping("/instant-receipts")
    public ResponseEntity<ResponseData> instantReceipts(@RequestBody RequestData requestBody) {
        ResponseData response = serviceBusinessService.instantReceipts(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
