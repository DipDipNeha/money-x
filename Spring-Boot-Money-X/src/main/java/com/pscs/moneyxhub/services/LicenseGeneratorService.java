/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.time.LocalDate;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.feignclient.LicenseFeignClient;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.LicenseRequest;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;

@Service
public class LicenseGeneratorService {

	@Autowired
	public  LicenseFeignClient licenseFeignClient;


	public ResponseData generateLicense(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request for License");
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject jsonObject = new JSONObject(jsonString);

			LicenseRequest licenseRequest = new LicenseRequest();
			licenseRequest.setEmail(jsonObject.getString("email"));
			licenseRequest.setExpiryDate(LocalDate.parse(jsonObject.getString("expiryDate")));
			licenseRequest.setProductId(jsonObject.getString("productId"));

			Map<String, String> license = licenseFeignClient.generateLicense(licenseRequest);
			System.err.println(license);

			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS);
			response.setResponseData(license);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
