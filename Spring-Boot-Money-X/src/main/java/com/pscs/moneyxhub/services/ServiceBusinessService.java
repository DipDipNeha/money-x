package com.pscs.moneyxhub.services;

import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pscs.moneyxhub.entity.Transactions;
import com.pscs.moneyxhub.entity.WalletAcctData;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.TransactionsRepo;
import com.pscs.moneyxhub.repo.WalletAcctDataRepository;

@Service
public class ServiceBusinessService {

    private final WalletAcctDataRepository walletRepo;
    private final TransactionsRepo transactionsRepo;
    


    public ServiceBusinessService(WalletAcctDataRepository walletRepo, TransactionsRepo transactionsRepo) {
		
		this.walletRepo = walletRepo;
		this.transactionsRepo = transactionsRepo;
	}

	// 1. View balance
    public ResponseData viewBalance(RequestData request) {
        ResponseData response = new ResponseData();
        try {
        String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
        JSONObject requestJson = new JSONObject(jsonString);
        System.out.println("Request Body: " + requestJson.toString());

        String accountNumber = requestJson.getString("accountNumber");
        WalletAcctData wallet = null;//walletRepo.findByAcctNo(accountNumber);

        if (wallet == null) {
            response.setResponseCode(CoreConstant.FAILURE_CODE);
            response.setResponseMessage(CoreConstant.FAILED);
            response.setResponseData("Wallet not found");
        } else {
            response.setResponseCode(CoreConstant.SUCCESS_CODE);
            response.setResponseMessage(CoreConstant.SUCCESS);
            JSONObject balance = new JSONObject();
            response.setResponseData(balance.toMap());
        }
        }catch(Exception e) {
			e.printStackTrace();
		}
        return response;
    }

    // 2. Mini statement (to be implemented properly later)
    public ResponseData miniStatement(RequestData request) {
  
        ResponseData response = new ResponseData();
        try {
        	String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
            JSONObject requestJson = new JSONObject(jsonString);
            System.out.println("Request Body: " + requestJson.toString());
            
			List<Transactions> content = transactionsRepo.findAll(PageRequest.of(0, 5))	.getContent();
			if (content.isEmpty()) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED);
				response.setResponseData(null);
				return response;
			} else {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(content);
				return response;
			}

			// Placeholder response	
        
        	
      
        }catch(Exception e) {
        	e.printStackTrace();
        }
       
        return response;
    }

    // 3. Full statement (to be implemented properly later)
    public ResponseData fullStatement(RequestData request) {
        ResponseData response = new ResponseData();
        try {
        	String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
            JSONObject requestJson = new JSONObject(jsonString);
            System.out.println("Request Body: " + requestJson.toString());
        List<Transactions> all =null;//transactionsRepo.findByAcctNoAndTxnDate(requestJson.getString("accountNumber"), requestJson.getString("txnDate"));
        
		if (all.isEmpty()) {
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED);
			response.setResponseData(null);
			return response;
		}
		else {
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS);
			response.setResponseData(all);
			return response;
		}
        
       
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return response;
    }

    // 4. Instant receipts (to be implemented properly later)
    public ResponseData instantReceipts(RequestData request) {
        ResponseData response = new ResponseData();
        try{
        	  String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
              JSONObject requestJson = new JSONObject(jsonString);
              System.out.println("Request Body: " + requestJson.toString());
        	
        	
        	Transactions byPaymentReference = transactionsRepo.findByPaymentReference(requestJson.getString("paymentReference"));
        	if(byPaymentReference==null) {
        		response.setResponseCode(CoreConstant.FAILURE_CODE);
        	
			response.setResponseMessage(CoreConstant.FAILED);
			response.setResponseData(null);
		} else {
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS);
			response.setResponseData(byPaymentReference);
		}
        }catch(Exception e) {
			e.printStackTrace();
			}
        return response;
    }
}
