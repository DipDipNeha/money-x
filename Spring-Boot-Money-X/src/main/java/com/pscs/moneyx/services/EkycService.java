package com.pscs.moneyx.services;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;

/**
 * 
 */
@Service
public class EkycService {
	private static Logger logger = Logger.getLogger(EkycService.class);

	public ResponseData doEkyc(RequestData request) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
		    //need to write business code here	
			responseData.setResponseCode("00");
			responseData.setResponseMessage("Ekyc processed successfully");
			responseData.setResponseData(null);
			
			
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
