package com.pscs.moneyxhub.services;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.embedly.caller.EmbedlyServiceCaller;
import com.pscs.moneyxhub.entity.MobContactInfo;
import com.pscs.moneyxhub.entity.MobCustomerMaster;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.MobContactInfoRepo;
import com.pscs.moneyxhub.repo.MobCustomerMasterRepo;
import com.pscs.moneyxhub.services.post.EkycPostingService;

import jakarta.transaction.Transactional;

/**
 * 
 */
@Service
public class EkycService {
	private static Logger logger = Logger.getLogger(EkycService.class);
	private final EkycPostingService ekycPostingService;
private final MobCustomerMasterRepo mobCustomerMasterRepo;
private final MobContactInfoRepo mobContactInfoRepo;
	
	

	public EkycService(EkycPostingService ekycPostingService, MobCustomerMasterRepo mobCustomerMasterRepo,
		MobContactInfoRepo mobContactInfoRepo) {
	this.ekycPostingService = ekycPostingService;
	this.mobCustomerMasterRepo = mobCustomerMasterRepo;
	this.mobContactInfoRepo = mobContactInfoRepo;
}

	@Transactional
	public ResponseData doEkyc(RequestData request) {
	    ResponseData responseData = new ResponseData();
	    try {
	        logger.info("Request: " + request);

	        String jrequest = ConvertRequestUtils.getJsonString(request);
	        JSONObject jrequestjson = new JSONObject(jrequest);

	        String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
	        String jsonStringHeader = ConvertRequestUtils.getJsonString(request.getJheader());

	        JSONObject jsonHeader = new JSONObject(jsonStringHeader);
	        JSONObject jsonBody = new JSONObject(jsonString);

	        String ekycNumber = jsonBody.optString("ekycNumber");
	        String ekycType = jsonBody.optString("ekycType");
	        String businessCountry = jsonBody.optString("businessCountry");
	        String customerId = jsonBody.optString("customerId");

	        // Validate mandatory fields
	        if (isNullOrEmpty(businessCountry)) {
	            return createErrorResponse("Business Country is missing", "400");
	        }
	        if (isNullOrEmpty(ekycType)) {
	            return createErrorResponse("Ekyc Type is missing", "400");
	        }
	        if (isNullOrEmpty(ekycNumber)) {
	            return createErrorResponse("Ekyc Number is missing", "400");
	        }

	        // Placeholder for Embedly or API call response
	        JSONObject apiResponse;
	     // Process the request
	        // JSONObject apiResponse = ekycPostingService.sendGetRequest(jsonString, urlType, params); 
	        // return processApiResponse(apiResponse);
	        if ("NIN".equalsIgnoreCase(ekycType)) {
	            apiResponse = customerKycUpgrade(jrequestjson);
	        } else {
	            apiResponse = premiumEkyc(jrequestjson);
	        }

	        logger.info("API Response: " + apiResponse);

	        String respCode = apiResponse.optString("respCode", "99");
	        if ("00".equalsIgnoreCase(respCode)) {
	        	
	        	
	        	
	        	
	        	
	        	
	            String userId = jsonHeader.optString("userid");
	            MobCustomerMaster customerMaster = mobCustomerMasterRepo.findByUserName(userId);
	            MobContactInfo contactInfo = mobContactInfoRepo.findByCustId(customerId);

	            if (customerMaster != null) {
	                if ("NIN".equalsIgnoreCase(ekycType)) {
	                	
	                	
	                	JSONObject data = apiResponse.optJSONObject("data");
	                    JSONObject summary = data.optJSONObject("summary");
	                    JSONObject nin_check = summary.optJSONObject("nin_check");
	                    
	                    String status= nin_check.optString("status");
//						if ("EXACT_MATCH".equalsIgnoreCase(status)) {
							
		                    customerMaster.setBvnVerified(true);
	                	
		                    customerMaster.setNin(ekycNumber);
	                } else {
	                	
	                	JSONObject data = apiResponse.optJSONObject("data");
	    	        	JSONObject bvnResponse = data.getJSONObject("response");
	    	        	JSONObject bvn = bvnResponse.getJSONObject("bvn");
	    	        	
	                    
	                    JSONObject summary = bvnResponse.optJSONObject("summary");
	                    JSONObject bvn_check = summary.optJSONObject("bvn_check");
	                    
	                    String status= bvn_check.optString("status");
//						if ("EXACT_MATCH".equalsIgnoreCase(status)) {
							String gender= bvn.optString("gender");
		    	        	 String nationality = bvn.optString("nationality");
		    	        	 String stateOfResidence= bvn.optString("state_of_residence");
		    	        	 String marital_status= bvn.optString("marital_status");
		    	        	 String lgaOfResidence= bvn.optString("lga_of_residence");
		    	        	
		                	
		    	        	 customerMaster.setBvn(ekycNumber);
		    	        	 customerMaster.setGender(gender);
		    	        	 customerMaster.setBvnVerified(true);
		    	        	
		    	        	 
		    	        	 contactInfo.setNationality(nationality);
		    	        	 contactInfo.setrState(stateOfResidence);
		    	        	 contactInfo.setMaritalStatus(marital_status);
		    	        	 contactInfo.setRlLga(lgaOfResidence);
		                   
//						}
	                    
	                    
	                }
	                mobCustomerMasterRepo.save(customerMaster);
	                mobContactInfoRepo.save(contactInfo);
	            } else {
	                logger.warn("User not found for ID: " + userId);
	            }

	            responseData.setResponseCode(CoreConstant.SUCCESS_CODE);
	            responseData.setResponseMessage(CoreConstant.SUCCESS);
	            responseData.setResponseData(apiResponse.getJSONObject("data").toMap());
	        } else {
	            responseData.setResponseCode(CoreConstant.FAILURE_CODE);
	            responseData.setResponseMessage("EKYC Failed: " + apiResponse.optString("respmsg", "Unknown error"));
	            responseData.setResponseData(apiResponse.toMap());
	        }

	        return responseData;

	    } catch (Exception e) {
	        logger.error("Error processing EKYC", e);
	        return createErrorResponse("Failed to process EKYC: " + e.getMessage(), "500");
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
		
		return responseData;
	}

	public ResponseData customerKYCUpgradeNin(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			logger.info("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseMessage(CoreConstant.ERROR);
			response.setResponseCode("500");
			return response;
		}
		return response;
	}

	public ResponseData customerKYCUpgradeBvn(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			logger.info("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseMessage(CoreConstant.ERROR);
			response.setResponseCode("500");
			return response;
		}
		return response;
	}

	public ResponseData customerAddressVerification(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			logger.info("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);
			logger.info("Request Json String : " + jsonString);
			JSONObject reqJson = new JSONObject(jsonString);
			String jsonStringheader = ConvertRequestUtils.getJsonString(requestBody.getJheader());
			JSONObject reqJsonheader = new JSONObject(jsonStringheader);	

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				
				MobCustomerMaster byUserName = mobCustomerMasterRepo.findByUserName(reqJsonheader.getString("userid"));
				String mPrdCode = byUserName.getmPrdCode();
				int prdCode = Integer.parseInt(mPrdCode)+1;
				byUserName.setmPrdCode(prdCode+"");
				mobCustomerMasterRepo.save(byUserName);
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseMessage(CoreConstant.ERROR);
			response.setResponseCode("500");
			return response;
		}
		return response;
	}

	public static void buildResponseData(ResponseData response, JSONObject callService) {
		Object data = callService.get("data");

		if (data instanceof JSONObject) {
		    response.setResponseData(((JSONObject) data).toMap());
		} else if (data instanceof JSONArray) {
		    JSONArray jsonArray = (JSONArray) data;
		    List<Object> list = new ArrayList<>();
		    for (int i = 0; i < jsonArray.length(); i++) {
		        Object element = jsonArray.get(i);
		        if (element instanceof JSONObject) {
		            list.add(((JSONObject) element).toMap());
		        } else {
		            list.add(element);
		        }
		    }
		    response.setResponseData(list);
		} else {
		    response.setResponseData(callService.toMap());
		}

	}
	
	
	public static JSONObject customerKycUpgrade(JSONObject jrequest) {
	    JSONObject response = new JSONObject();
	    try {
	        JSONObject rjheader = jrequest.getJSONObject("jheader");
	        JSONObject rjbody = jrequest.getJSONObject("jbody");

	        JSONObject request = new JSONObject();
	        JSONObject jheader = new JSONObject();

	        jheader.put("userid", rjheader.optString("userid"));
	        jheader.put("ip", rjheader.optString("ip"));
	        jheader.put("timestamp", rjheader.optString("timestamp"));
	        jheader.put("requestType", "CUST_KYC_UPGRADE");
	        jheader.put("channel", rjheader.optString("channel"));
	        request.put("jheader", jheader);

	        JSONObject jbody = new JSONObject();
	        jbody.put("nin", rjbody.optString("ekycNumber"));
	        jbody.put("customerId", rjbody.optString("customerId"));
	        jbody.put("firstname", rjbody.optString("firstname"));
	        jbody.put("lastname", rjbody.optString("lastname"));
	        jbody.put("dob", rjbody.optString("dob"));
	        request.put("jbody", jbody);

	        logger.info("Request: " + request);
	        EmbedlyServiceCaller service = new EmbedlyServiceCaller();
	        response = service.callService(request);
	        logger.info("Response: " + response);
	    } catch (Exception e) {
	        logger.error("Error in customerKycUpgrade", e);
	    }
	    return response;
	}

	
	
	//premium ekyc - bvn
	public static JSONObject premiumEkyc(JSONObject jrequest) {
	    JSONObject response = new JSONObject();
	    try {
	        JSONObject rjheader = jrequest.getJSONObject("jheader");
	        JSONObject rjbody = jrequest.getJSONObject("jbody");

	        JSONObject request = new JSONObject();
	        JSONObject jheader = new JSONObject();

	        jheader.put("userid", rjheader.optString("userid"));
	        jheader.put("ip", rjheader.optString("ip"));
	        jheader.put("timestamp", rjheader.optString("timestamp"));
	        jheader.put("requestType", "CUST_PREMIUM_KYC");
	        jheader.put("channel", rjheader.optString("channel"));
	        request.put("jheader", jheader);

	        JSONObject jbody = new JSONObject();
	        jbody.put("customerId", rjbody.optString("customerId"));
	        jbody.put("bvn", rjbody.optString("ekycNumber"));
	        request.put("jbody", jbody);

	        logger.info("Request: " + request);
	        EmbedlyServiceCaller service = new EmbedlyServiceCaller();
	        response = service.callService(request);
	        logger.info("Response: " + response);
	    } catch (Exception e) {
	        logger.error("Error in premiumEkyc", e);
	    }
	    return response;
	}

}
