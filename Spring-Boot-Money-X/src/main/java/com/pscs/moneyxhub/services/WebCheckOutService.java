/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.entity.SubscriptionAddons;
import com.pscs.moneyxhub.entity.SubscriptionPlan;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.SubscriptionAddonsRepo;
import com.pscs.moneyxhub.repo.SubscriptionPlanRepo;

@Service
public class WebCheckOutService {

	private final SubscriptionPlanRepo subscriptionPlanRepo;
	private final SubscriptionAddonsRepo subscriptionAddonsRepo;
	
	public WebCheckOutService(SubscriptionPlanRepo subscriptionPlanRepo,
			SubscriptionAddonsRepo subscriptionAddonsRepo) {
		this.subscriptionPlanRepo = subscriptionPlanRepo;
		this.subscriptionAddonsRepo = subscriptionAddonsRepo;
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

}
