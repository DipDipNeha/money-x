package com.pscs.moneyx.services;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.post.EkycPostingService;

/**
 * 
 */
@Service
public class EkycService {
	private static Logger logger = Logger.getLogger(EkycService.class);
	private final EkycPostingService ekycPostingService;
	

	public EkycService(EkycPostingService ekycPostingService) {
		this.ekycPostingService = ekycPostingService;
	}


	public ResponseData doEkyc(RequestData request) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			String ekycNumber=jsonObject.getString("ekycNumber");
			String ekycType=jsonObject.getString("ekycType");
			JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString,ekycType,ekycNumber);
		    //need to write business code here	
			responseData.setResponseCode("00");
			responseData.setResponseMessage("Ekyc processed successfully");
			responseData.setResponseData(sendGetRequest.toMap());
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage("Failed to process ekyc");
			responseData.setResponseCode("500");
			return responseData;	
		}
		return responseData;
	}
	
}
