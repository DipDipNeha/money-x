package com.pscs.moneyx.services;



import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.helper.CoreConstant;
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
	    try {
	        logger.info("Request: " + request);
	        String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
	        logger.info("Request JSON String: " + jsonString);

	        JSONObject jsonObject = new JSONObject(jsonString);
	        String ekycNumber = jsonObject.optString("ekycNumber");
	        String ekycType = jsonObject.optString("ekycType");
	        String businessCountry = jsonObject.optString("businessCountry");

	        // Validate mandatory fields
	        if (isNullOrEmpty(businessCountry)) {
	            return createErrorResponse("Business Country is missing", "400");
	        }

	        if (isNullOrEmpty(ekycType)) {
	            return createErrorResponse("Ekyc Type is missing", "400");
	        }

	        // Determine URL and parameters based on country and ekyc type
	        String urlType = getUrlType(businessCountry, ekycType);
	        if (urlType == null) {
	            return createErrorResponse("Invalid Ekyc Type or Business Country", "400");
	        }

	        String params = getParams(ekycType, ekycNumber, jsonObject);
	        if (params == null) {
	            return createErrorResponse("Invalid parameters for Ekyc Type", "400");
	        }

	        // Process the request
	        JSONObject apiResponse = ekycPostingService.sendGetRequest(jsonString, urlType, params);
	        return processApiResponse(apiResponse);

	    } catch (Exception e) {
	        logger.error("Error processing ekyc", e);
	        return createErrorResponse("Failed to process ekyc", "500");
	    }
	}

	// Helper method to validate if a string is null or empty
	private boolean isNullOrEmpty(String value) {
	    return value == null || value.trim().isEmpty();
	}

	// Helper method to create an error response
	private ResponseData createErrorResponse(String message, String code) {
	    ResponseData responseData = new ResponseData();
	    responseData.setResponseMessage(message);
	    responseData.setResponseCode(code);
	    return responseData;
	}

	// Helper method to determine the URL type based on country and ekyc type
	private String getUrlType(String businessCountry, String ekycType) {
	    Map<String, String> urlMapping = new HashMap<>();
	    urlMapping.put("NG_BVN", "LOOKUP_BY_BVN_URL");
	    urlMapping.put("NG_NIN", "LOOKUP_BY_NIN_URL");
	    urlMapping.put("NG_PHONE", "LOOKUP_BY_PHONE_URL");
	    urlMapping.put("NG_NUBAN", "LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("NG_DL", "LOOKUP_BY_DL_URL");
	    urlMapping.put("NG_VOTERID", "LOOKUP_BY_VOTER_URL");
	    urlMapping.put("GH_NIN", "GH_LOOKUP_BY_NIN_URL");
	    urlMapping.put("GH_PHONE", "GH_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("GH_NUBAN", "GH_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("KE_NIN", "KE_LOOKUP_BY_NIN_URL");
	    urlMapping.put("KE_PHONE", "KE_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("KE_NUBAN", "KE_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("ZA_NIN", "ZA_LOOKUP_BY_NIN_URL");
	    urlMapping.put("ZA_PHONE", "ZA_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("ZA_NUBAN", "ZA_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("TZ_NIN", "TZ_LOOKUP_BY_NIN_URL");
	    urlMapping.put("TZ_PHONE", "TZ_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("TZ_NUBAN", "TZ_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("UG_NIN", "UG_LOOKUP_BY_NIN_URL");
	    urlMapping.put("UG_PHONE", "UG_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("UG_NUBAN", "UG_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("ZI_NIN", "ZI_LOOKUP_BY_NIN_URL");
	    urlMapping.put("ZI_PHONE", "ZI_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("ZI_NUBAN", "ZI_LOOKUP_BY_NUBAN_EKY_URL");
	    urlMapping.put("SA_NIN", "SA_LOOKUP_BY_NIN_URL");
	    urlMapping.put("SA_PHONE", "SA_LOOKUP_BY_PHONE_URL");
	    urlMapping.put("SA_NUBAN", "SA_LOOKUP_BY_NUBAN_EKY_URL");

	    return urlMapping.get(businessCountry.toUpperCase() + "_" + ekycType.toUpperCase());
	}

	// Helper method to generate request parameters
	private String getParams(String ekycType, String ekycNumber, JSONObject jsonObject) {
	    switch (ekycType.toUpperCase()) {
	        case "BVN":
	        case "NIN":
	        case "PHONE":
	            return "?" + ekycType.toLowerCase() + "=" + ekycNumber;
	        case "NUBAN":
	            return "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.optString("bankCode");
	        case "DL":
	            return "?license_number=" + ekycNumber;
	        case "VOTERID":
	            return "?vin=" + ekycNumber;
	        default:
	            return null;
	    }
	}

	// Helper method to process API response
	private ResponseData processApiResponse(JSONObject apiResponse) {
	    ResponseData responseData = new ResponseData();
	    String responseCode = apiResponse.optString("respcode");
	    if ("200".equalsIgnoreCase(responseCode)) {
	        responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
	        responseData.setResponseMessage(CoreConstant.SUCCESS);
	        responseData.setResponseData(apiResponse.toMap());
	    } else {
	        responseData.setResponseCode(responseCode);
	        responseData.setResponseMessage(apiResponse.optString("respmsg"));
	    }
	    return responseData;
	}
	public ResponseData verifyDocument(RequestData request) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject sendGetRequest = ekycPostingService.sendPostRequest(jsonObject.toString(), "DOCUMENT_VERIFICATION_URL");
			// need to write business code here
			if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
				responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
				responseData.setResponseMessage(CoreConstant.SUCCESS);
				responseData.setResponseData(sendGetRequest.toMap());
			} else {
				responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
				responseData.setResponseCode(sendGetRequest.getString("respcode"));
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage(CoreConstant.ERROR);
			responseData.setResponseCode("500");
			return responseData;
		}

		return responseData;
	}

	public ResponseData businessDocVerify(RequestData request) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject sendGetRequest = ekycPostingService.sendPostRequest(jsonObject.toString(), "BUSINESS_DOC_URL");
			if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
				responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
				responseData.setResponseMessage(CoreConstant.SUCCESS);
				responseData.setResponseData(sendGetRequest.toMap());
			} else {
				responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
				responseData.setResponseCode(sendGetRequest.getString("respcode"));
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage(CoreConstant.ERROR);
			responseData.setResponseCode("500");
			return responseData;
		}

		return responseData;
	}

	public ResponseData livelinessCheck(RequestData request) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject sendGetRequest = ekycPostingService.sendPostRequest(jsonObject.toString(), "LIVENESS_URL");
			if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
				responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
				responseData.setResponseMessage(CoreConstant.SUCCESS);
				responseData.setResponseData(sendGetRequest.toMap());
			} else {
				responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
				responseData.setResponseCode(sendGetRequest.getString("respcode"));
				return responseData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage(CoreConstant.ERROR);
			responseData.setResponseCode("500");
			return responseData;
		}

		return responseData;
	}

	public ResponseData accountEnquiry(RequestData requestBody) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			
			String params = "?account_number=" + jsonObject.getString("accountNumber") + "&bank_code=" + jsonObject.getString("bankCode");
			
			
			JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonObject.toString(),
					"ACCOUNT_ENQUIRY_URL",params);
			// need to write business code here
			if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
				responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
				responseData.setResponseMessage(CoreConstant.SUCCESS);
				responseData.setResponseData(sendGetRequest.toMap());
			} else {
				responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
				responseData.setResponseCode(sendGetRequest.getString("respcode"));
				return responseData;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage(CoreConstant.ERROR);
			responseData.setResponseCode("500");
			return responseData;
		}
		
		return null;
	}

}
