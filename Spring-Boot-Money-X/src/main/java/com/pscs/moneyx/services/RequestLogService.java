package com.pscs.moneyx.services;

import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.RequestLog;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.RequestLogRepo;

@Service
public class RequestLogService {

	 private static final Logger logger = Logger.getLogger(RequestLogService.class);
	private final RequestLogRepo requestLogRepo;

	public RequestLogService(RequestLogRepo requestLogRepo) {
		this.requestLogRepo = requestLogRepo;
	}

	// write logs
	public void writeLog(RequestData request,ResponseData response) {
		try {
			logger.info("Writing log for request: " + request.toString());
		String strjheader = ConvertRequestUtils.getJsonString(request.getJheader());
		JSONObject jHeader = new JSONObject(strjheader);
		
		
		RequestLog requestLog = new RequestLog();
		requestLog.setChannel(jHeader.getString("channel"));
		requestLog.setUsername(jHeader.getString("userid"));
		requestLog.setReqType(jHeader.getString("requestType"));
		requestLog.setRequestTime(new Date());
		// Convert RequestData to JsonObject 
		
		JSONObject requestJson = ConvertRequestUtils.convertRequestDataToJson(response);
		JSONObject responseJson = ConvertRequestUtils.convertRequestDataToJson(request);
		
		requestLog.setResponse(requestJson.toString());
		
		requestLog.setResponseCode(response.getResponseCode());
		requestLog.setResponseMessage(response.getResponseMessage());
		requestLog.setRequest(responseJson.toString());
		
		requestLogRepo.save(requestLog);
	} catch (Exception e) {
		logger.error("Error writing log: " + e.getMessage());
		e.printStackTrace();
	}
		logger.info("Log written successfully");
		
	}
	
}
