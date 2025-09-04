package com.pscs.moneyx.services;

import java.util.List;

import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.Transactions;
import com.pscs.moneyx.entity.WalletAcctData;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.TransactionsRepo;
import com.pscs.moneyx.repo.WalletAcctDataRepository;

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
        WalletAcctData wallet = walletRepo.findByAcctNo(accountNumber);

        if (wallet == null) {
            response.setResponseCode("01");
            response.setResponseMessage("Failed Balance fetched ");
            response.setResponseData("Wallet not found");
        } else {
            response.setResponseCode("00");
            response.setResponseMessage("Balance fetched successfully");
            JSONObject balance = new JSONObject();
            balance.put("balance", wallet.getBalance());
            balance.put("accountNumber", wallet.getAcctNo());
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
				response.setResponseCode("01");
				response.setResponseMessage("No transactions found");
				response.setResponseData(null);
				return response;
			} else {
				response.setResponseCode("00");
				response.setResponseMessage("Transactions fetched successfully");
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
        List<Transactions> all =transactionsRepo.findByAcctNoAndTxnDate(requestJson.getString("accountNumber"), requestJson.getString("txnDate"));
        
		if (all.isEmpty()) {
			response.setResponseCode("01");
			response.setResponseMessage("No transactions found");
			response.setResponseData(null);
			return response;
		}
		else {
			response.setResponseCode("00");
			response.setResponseMessage("Transactions fetched successfully");
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
        		response.setResponseCode("01");
        	
			response.setResponseMessage("Instant Receipts not implemented yet");
			response.setResponseData(null);
		} else {
			response.setResponseCode("00");
			response.setResponseMessage("Transaction fetched successfully");
			response.setResponseData(byPaymentReference);
		}
        }catch(Exception e) {
			e.printStackTrace();
			}
        return response;
    }
}
