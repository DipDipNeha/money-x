package com.pscs.moneyxhub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.LicenseGeneratorService;

@RestController
@RequestMapping("/api/v1/license")
public class LicenseController {

    @Autowired
    private LicenseGeneratorService generatorService;
   

    @PostMapping("/generate")
    public ResponseEntity<ResponseData> generateLicense(@RequestBody RequestData requestBody) throws Exception {
    	ResponseData response=new ResponseData();
    	response= generatorService.generateLicense(requestBody);
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
  
}
