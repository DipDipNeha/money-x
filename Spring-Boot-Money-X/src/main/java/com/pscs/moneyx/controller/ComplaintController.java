package com.pscs.moneyx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.ComplaintService;

@CrossOrigin
@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }
    
 // Echo endpoint
    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        return new ResponseEntity<>("Complaint Controller is up!", HttpStatus.OK);
    }
  

    // 3. view complaint status
    @PostMapping("/status")
    public ResponseEntity<ResponseData> viewComplaintStatus(@RequestBody RequestData requestBody) {
        ResponseData response = complaintService.viewComplaintStatus(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 4. fetch all complaints based on user
    @PostMapping("/user-complaints")
    public ResponseEntity<ResponseData> fetchAllComplaintsByUser(@RequestBody RequestData requestBody) {
        ResponseData response = complaintService.fetchAll(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 5. raise complaint (optional, if you want an endpoint for it)
    @PostMapping("/raise")
    public ResponseEntity<ResponseData> raiseComplaint(@RequestBody RequestData requestBody) {
        ResponseData response = complaintService.raiseComplaint(requestBody);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
