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
			String urlType = "";
			String params = "";

			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			logger.info("Request Json String : " + jsonString);
			JSONObject jsonObject = new JSONObject(jsonString);
			String ekycNumber = jsonObject.getString("ekycNumber");
			String ekycType = jsonObject.getString("ekycType");
			String businessCountry = jsonObject.getString("businessCountry");
			if (businessCountry == null || businessCountry.isEmpty()) {
				responseData.setResponseMessage("Business Country is missing");
				responseData.setResponseCode("400");
				return responseData;
			}
			else if (businessCountry.equalsIgnoreCase("NG")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("BVN")) {
					urlType = "LOOKUP_BY_BVN_URL";
					params = "?bvn=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber+ "&bank_code="+jsonObject.getString("bankCode");

				} else if (ekycType.equalsIgnoreCase("DL")) {
					urlType = "LOOKUP_BY_DL_URL";
					params = "?license_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("VOTERID")) {
					urlType = "LOOKUP_BY_VOTER_URL";
					params = "?vin=" + ekycNumber;

				}
				else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType,  params);
				// need to write business code here 
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}

			} else if (businessCountry.equalsIgnoreCase("GH")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "GH_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "GH_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "GH_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}

			} else if (businessCountry.equalsIgnoreCase("KE")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "KE_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "KE_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "KE_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
			} else if (businessCountry.equalsIgnoreCase("ZA")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "ZA_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "ZA_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "ZA_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
			} else if (businessCountry.equalsIgnoreCase("TZ")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "TZ_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "TZ_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "TZ_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
			} //GH
			else if (businessCountry.equalsIgnoreCase("UG")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "UG_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "UG_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "UG_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
			} else if(businessCountry.equalsIgnoreCase("ZI")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "ZI_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "ZI_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "ZI_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
			} else if (businessCountry.equalsIgnoreCase("SA")) {
				if (ekycType == null || ekycType.isEmpty()) {
					responseData.setResponseMessage("Ekyc Type is missing");
					responseData.setResponseCode("400");
					return responseData;
				} else if (ekycType.equalsIgnoreCase("NIN")) {
					urlType = "SA_LOOKUP_BY_NIN_URL";
					params = "?nin=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("PHONE")) {
					urlType = "SA_LOOKUP_BY_PHONE_URL";
					params = "?phone_number=" + ekycNumber;

				} else if (ekycType.equalsIgnoreCase("NUBAN")) {
					urlType = "SA_LOOKUP_BY_NUBAN_EKY_URL";
					params = "?account_number=" + ekycNumber + "&bank_code=" + jsonObject.getString("bankCode");

				} else {
					responseData.setResponseMessage("Invalid Ekyc Type");
					responseData.setResponseCode("400");
					return responseData;
				}

				JSONObject sendGetRequest = ekycPostingService.sendGetRequest(jsonString, urlType, params);
				// need to write business code here
				if (sendGetRequest.getString("respcode").equalsIgnoreCase("200")) {
					responseData.setResponseCode("00");
					responseData.setResponseMessage("Ekyc processed successfully");
					responseData.setResponseData(sendGetRequest.toMap());
				} else {
					responseData.setResponseMessage(sendGetRequest.getString("respmsg"));
					responseData.setResponseCode(sendGetRequest.getString("respcode"));
					return responseData;
				}
				
			}
			
			else {
				responseData.setResponseMessage("Invalid Business Country");
				responseData.setResponseCode("400");
				return responseData;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
			responseData.setResponseMessage("Failed to process ekyc");
			responseData.setResponseCode("500");
			return responseData;
		}
		return responseData;
	}
	
}
