/**
 * @author Dipak
 */

package com.pscs.moneyxhub.services;

import java.util.Date;

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
	
	
	private final TransactionsRepo transactionsRepo;

	public TransactionService(TransactionsRepo transactionsRepo) {
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


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				

				Transactions txn = new Transactions();
				txn.setAcctNo(reqJson.getString("accountNumber"));
				txn.setTxnType("INTERBANK_TRANSFER");
				txn.setAmount(reqJson.getDouble("amount"));
				txn.setCurrency(reqJson.getString("currency"));
				txn.setTxnDate(new Date());
				txn.setPaymentReference(callService.getString("paymentReference"));
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respMessage"));
				txn.setCreatedDate(new Date());
				txn.setBeneficiaryaccount(reqJson.getString("beneficiaryAccount"));
				txn.setBeneficiarybank(reqJson.getString("beneficiaryBank"));
				txn.setBeneficiaryname(callService.getString("beneficiaryName"));
				txn.setResponseJbody(callService.toString());
				txn.setRequestJbody(reqJson.toString());
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respmsg"));
				txn.setStatus("SUCCESS");
				transactionsRepo.save(txn);
				
				
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
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

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				
				Transactions txn = new Transactions();
				txn.setAcctNo(reqJson.getString("accountNumber"));
				txn.setTxnType("INTERBANK_TRANSFER");
				txn.setAmount(reqJson.getDouble("amount"));
				txn.setCurrency(reqJson.getString("currency"));
				txn.setTxnDate(new Date());
				txn.setPaymentReference(callService.getString("paymentReference"));
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respMessage"));
				txn.setCreatedDate(new Date());
				txn.setBeneficiaryaccount(reqJson.getString("beneficiaryAccount"));
				txn.setBeneficiarybank(reqJson.getString("beneficiaryBank"));
				txn.setBeneficiaryname(callService.getString("beneficiaryName"));
				txn.setResponseJbody(callService.toString());
				txn.setRequestJbody(reqJson.toString());
				txn.setResponseCode(callService.getString("respCode"));
				txn.setResponseMessage(callService.getString("respmsg"));
				txn.setStatus("SUCCESS");
				transactionsRepo.save(txn);
				
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(callService.toMap());
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
	
}
