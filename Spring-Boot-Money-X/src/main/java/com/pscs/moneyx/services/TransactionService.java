/**
 * @author Dipak
 */

package com.pscs.moneyx.services;

import org.json.JSONObject;

import com.pscs.embedly.caller.EmbedlyServiceCaller;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.helper.CoreConstant;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;

public class TransactionService {

	public ResponseData getBanks(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}

		return response;
	}

	public ResponseData accountNameEnquiry(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData interBankTransfer(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData transactionStatus(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	

	public ResponseData transferWallet(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	public ResponseData requeryWalletTrans(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	public ResponseData reverseTxn(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	
}
