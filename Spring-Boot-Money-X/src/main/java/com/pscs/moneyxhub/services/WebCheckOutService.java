/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.entity.SubscriptionAddons;
import com.pscs.moneyxhub.entity.SubscriptionPayments;
import com.pscs.moneyxhub.entity.SubscriptionPlan;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.SubscriptionAddonsRepo;
import com.pscs.moneyxhub.repo.SubscriptionPaymentsRepo;
import com.pscs.moneyxhub.repo.SubscriptionPlanRepo;
import com.pscs.moneyxhub.services.post.InterswitchService;

@Service
public class WebCheckOutService {

	private final SubscriptionPlanRepo subscriptionPlanRepo;
	private final SubscriptionAddonsRepo subscriptionAddonsRepo;
	private final InterswitchService interswitchService;
	private final SubscriptionPaymentsRepo subscriptionPaymentsRepo;
	
	public WebCheckOutService(SubscriptionPlanRepo subscriptionPlanRepo,
			SubscriptionAddonsRepo subscriptionAddonsRepo, InterswitchService interswitchService, SubscriptionPaymentsRepo subscriptionPaymentsRepo) {
		this.subscriptionPlanRepo = subscriptionPlanRepo;
		this.subscriptionAddonsRepo = subscriptionAddonsRepo;
		this.interswitchService = interswitchService;
		this.subscriptionPaymentsRepo = subscriptionPaymentsRepo;
		
	}

	public ResponseData loadPackage(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			response.setResponseCode("01");
			response.setResponseMessage("Packages Failed to Load");
			
			List<SubscriptionPlan> subscriptionPlan = subscriptionPlanRepo.findAll();
			List<SubscriptionAddons> subscriptionAddons = subscriptionAddonsRepo.findAll();
			
			JSONObject responseData = new JSONObject();
			responseData.put("subscriptionPlan", subscriptionPlan);
			responseData.put("subscriptionAddons", subscriptionAddons);
			response.setResponseData(responseData.toMap());
			response.setResponseCode("00");
			response.setResponseMessage("Packages Loaded Successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public ResponseData getTransactionStatus(RequestData requestBody) {
	
		ResponseData response = new ResponseData();
		try {
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			JSONObject requestJson = new JSONObject(jsonString);
			response.setResponseCode("01");
			response.setResponseMessage("Transaction Status Fetch Failed");

			JSONObject interswitchResponse = interswitchService.getTransaction(requestJson.getString("merchantcode"),
					requestJson.getString("transactionreference"), requestJson.getString("amount"));

			Optional<Object> byTransactionReference = subscriptionPaymentsRepo.findByTransactionReference(requestJson.getString("transactionreference"));
			if (byTransactionReference.isPresent()) {
				SubscriptionPayments payment = (SubscriptionPayments) byTransactionReference.get();
				payment.setResponseData(interswitchResponse.toString());
				JSONObject respData = interswitchResponse.getJSONObject("data");
				payment.setResponseMessage(respData.getString("responseDescription"));
				payment.setResponseCode(respData.getString("responseCode"));
				subscriptionPaymentsRepo.save(payment);
				response.setResponseCode(respData.getString("responseCode"));
				response.setResponseMessage(respData.getString("responseDescription"));
				
			}
			response.setResponseData(interswitchResponse.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public ResponseData initiatedPayment(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			JSONObject requestJson = new JSONObject(jsonString);
			response.setResponseCode("01");
			response.setResponseMessage("Transaction Status Fetch Failed");

			SubscriptionPayments payment = new SubscriptionPayments();
			payment.setAmount(requestJson.getString("amount"));
			payment.setTransactionReference(requestJson.getString("transactionReference"));
			payment.setPaymentStatus("INITIATED");
			payment.setTxnDate(new Date()+"");
			payment.setMobileNumber(requestJson.getString("mobileNumber"));
			payment.setPlanId(requestJson.getString("planId"));
			payment.setRequestData(requestJson.toString());
			payment.setCreatedAt(new java.util.Date());
			payment.setResponseData(requestJson.getJSONObject("responseData").toString());
			JSONObject respData = requestJson.getJSONObject("responseData");
			
			payment.setResponseMessage(respData.getString("responseDescription"));
			payment.setAddonsId(requestJson.getString("addonsId"));
			payment.setResponseCode(respData.getString("responseCode"));
			payment.setDuration(requestJson.getString("duration"));
			
			subscriptionPaymentsRepo.save(payment);
			response.setResponseCode("00");
			response.setResponseMessage("Payment Initiated Successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
