package com.pscs.moneyx.services;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.Complaint;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.ComplaintRepository;

@Service
public class ComplaintService {

	@Autowired
	private ComplaintRepository complaintRepository;

	public ResponseData raiseComplaint(RequestData requestData) {
		ResponseData response = new ResponseData();
		try {
			String jsonString = ConvertRequestUtils.getJsonString(requestData.getJbody());
			JSONObject requestJson = new JSONObject(jsonString);
			
		    			// Create and populate Complaint entity

			Complaint complaint = new Complaint();
			complaint.setPaymentReference(requestJson.getString("paymentReference"));
			complaint.setAccountNo(requestJson.getString("accountNo"));
			complaint.setAccountType(requestJson.getString("accountType"));
			complaint.setAmount(requestJson.getString("amount"));
			complaint.setCurrency(requestJson.getString("currency"));
			complaint.setTransactionDate(requestJson.getString("transactionDate"));
			complaint.setTransactionType(requestJson.getString("transactionType"));
			complaint.setCreatedDate(LocalDateTime.now());
			complaint.setUpdatedDate(LocalDateTime.now());
			

			// generate complaintId like C10001
			String generatedId = "C" + System.currentTimeMillis();
			complaint.setComplaintId(generatedId);

			complaint.setUserId(requestJson.getString("username"));
			complaint.setDescription(requestJson.getString("description"));
			complaint.setStatus("OPEN");

			//Check complaint for same paymentReference
			String paymentReference = requestJson.getString("paymentReference");
			Complaint existingComplaint = complaintRepository.findByPaymentReference(paymentReference);
			if (existingComplaint != null) {
				response.setResponseCode("01");
				response.setResponseMessage("A complaint for this payment reference already exists.");
				return response;
			}
			complaintRepository.save(complaint);

			response.setResponseCode("00");
			response.setResponseMessage("Complaint raised successfully. Your complaint Id is "+generatedId);
			response.setResponseData(complaint);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public ResponseData viewComplaintStatus(RequestData requestData) {
		ResponseData response = new ResponseData();
		try {
		String jsonString = ConvertRequestUtils.getJsonString(requestData.getJbody());
		JSONObject requestJson = new JSONObject(jsonString);

		String complaintId = requestJson.getString("complaintId");
		Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

		if (complaint != null) {
			response.setResponseCode("00");
			response.setResponseMessage("Complaint status retrieved");
			response.setResponseData(complaint.getStatus());
		} else {
			response.setResponseCode("01");
			response.setResponseMessage("Complaint not found");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public ResponseData fetchAll(RequestData requestData) {
		ResponseData response = new ResponseData();
		
		try {
		String jsonString = ConvertRequestUtils.getJsonString(requestData.getJbody());
		JSONObject requestJson = new JSONObject(jsonString);

		String customerId = requestJson.getString("username");
		List<Complaint> complaints = complaintRepository.findByUserId(customerId);
		if (complaints.isEmpty()) {
			response.setResponseCode("01");
			response.setResponseMessage("No complaints found for the user");
			return response;
		}

		response.setResponseCode("00");
		response.setResponseMessage("Fetched all complaints successfully");
		response.setResponseData(complaints);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	


}
