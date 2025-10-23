/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.embedly.caller.EmbedlyServiceCaller;
import com.pscs.moneyxhub.entity.Transactions;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.TransactionsRepo;
@Service
public class TransactionService {
	
	private final CustomerBusinessService customerBusinessService;
	
	private final TransactionsRepo transactionsRepo;

	public TransactionService(TransactionsRepo transactionsRepo, CustomerBusinessService customerBusinessService) {
		this.customerBusinessService = customerBusinessService;
		this.transactionsRepo = transactionsRepo;
	}

	public ResponseData getBanks(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				
				
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			String jbody = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			JSONObject jbodyJson = new JSONObject(jbody);
			jbodyJson.put("customerTransactionReference", System.currentTimeMillis()+"");
			jbodyJson.put("narration", jbodyJson.getString("remarks"));
			jbodyJson.put("transactionTypeId", System.nanoTime()+"");
			
			reqJson.put("jbody", jbodyJson);
			String jheader = ConvertRequestUtils.getJsonString(requestBody.getJheader());
			JSONObject jheaderJson = new JSONObject(jheader);
			String userName = jheaderJson.getString("userid");
			String txnPin = jbodyJson.getString("txnPin");
			
			ResponseData validateTxnPin = customerBusinessService.validateTxnPin(txnPin, userName);
			if (!validateTxnPin.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateTxnPin;
			}
			
			//Validate OTP
			ResponseData validateOtp = customerBusinessService.validateOtp(requestBody);
			if (!validateOtp.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateOtp;
			}
			

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				
				Transactions txn = new Transactions();
				txn.setAcctNo(jbodyJson.getString("sourceAccountNumber"));
				txn.setSourceAccountName(jbodyJson.getString("sourceAccountName"));
				txn.setTxnType("INTERBANK_TRANSFER");
				txn.setAmount(jbodyJson.getDouble("amount"));
				txn.setCurrency(jbodyJson.getString("currency"));
				txn.setTxnDate(new Date());
				txn.setPaymentReference(jbodyJson.getString("customerTransactionReference"));
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respMessage"));
				txn.setCreatedDate(new Date());
				txn.setBeneficiaryaccount(jbodyJson.getString("destinationAccountNumber"));
				txn.setBeneficiarybank(jbodyJson.getString("beneficiaryBank"));
				txn.setBeneficiaryname(jbodyJson.getString("destinationAccountName"));
				txn.setResponseJbody(callService.toString());
				txn.setRequestJbody(reqJson.toString());
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respmsg"));
				txn.setRemarks(jbodyJson.getString("remarks"));
				txn.setDrnarration(jbodyJson.getString("narration"));
				txn.setCrnarration(jbodyJson.getString("narration"));
				txn.setDestinationBankCode(jbodyJson.getString("destinationBankCode"));
				txn.setWebhookUrl(jbodyJson.getString("webhookUrl"));
				txn.setStatus("SUCCESS");
				transactionsRepo.save(txn);
				
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);
			String jbody = ConvertRequestUtils.getJsonString(requestBody.getJbody());
			JSONObject jbodyJson = new JSONObject(jbody);
			
			jbodyJson.put("transactionReference", System.currentTimeMillis()+"");
			jbodyJson.put("narration", jbodyJson.getString("remarks"));
			jbodyJson.put("transactionTypeId", System.nanoTime()+"");
			
			
			

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			reqJson.put("jbody", jbodyJson);
			
			String jheader = ConvertRequestUtils.getJsonString(requestBody.getJheader());
			JSONObject jheaderJson = new JSONObject(jheader);
			String userName = jheaderJson.getString("userid");
			String txnPin = jbodyJson.getString("txnPin");
			
			ResponseData validateTxnPin = customerBusinessService.validateTxnPin(txnPin, userName);
			if (!validateTxnPin.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateTxnPin;
			}
			
			//Validate OTP
			ResponseData validateOtp = customerBusinessService.validateOtp(requestBody);
			if (!validateOtp.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateOtp;
			}
			

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				
				Transactions txn = new Transactions();
				txn.setAcctNo(jbodyJson.getString("fromAccount"));
				txn.setTxnType("WALLET_TO_WALLET");
				txn.setAmount(jbodyJson.getDouble("amount"));
				txn.setCurrency(jbodyJson.getString("currency"));
				txn.setTxnDate(new Date());
				txn.setPaymentReference(jbodyJson.getString("transactionReference"));
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respMessage"));
				txn.setCreatedDate(new Date());
				txn.setBeneficiaryaccount(jbodyJson.getString("destinationAccountNumber"));
				txn.setBeneficiarybank(jbodyJson.has("beneficiaryBank") ?jbodyJson.getString("beneficiaryBank"):"");
				txn.setBeneficiaryname(jbodyJson.has("beneficiaryName") ?jbodyJson.getString("beneficiaryName"):"");
				txn.setResponseJbody(callService.toString());
				txn.setRequestJbody(reqJson.toString());
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respmsg"));
				txn.setRemarks(jbodyJson.getString("remarks"));
				txn.setDrnarration(jbodyJson.getString("narration"));
				txn.setCrnarration(jbodyJson.getString("narration"));
				txn.setStatus("SUCCESS");
				transactionsRepo.save(txn);
				
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
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
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respmsg"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	

	public ResponseData getOrgWalletTrans(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + callService);
			
			if (callService.getString("respCode").equals("00")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED +"  " + callService.getString("respmsg"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
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
	
}
