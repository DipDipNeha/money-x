package com.pscs.moneyx.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.entity.WalletAcctData;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.repo.WalletAcctDataRepository;

@Service
public class ServiceBusinessService {

    private final WalletAcctDataRepository walletRepo;

    public ServiceBusinessService(WalletAcctDataRepository walletRepo) {
        this.walletRepo = walletRepo;
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
        
        	 response.setResponseCode("01");
             response.setResponseMessage("Mini Statement not implemented yet");
             response.setResponseData(null);
        }catch(Exception e) {
        	e.printStackTrace();
        }
       
        return response;
    }

    // 3. Full statement (to be implemented properly later)
    public ResponseData fullStatement(RequestData request) {
        ResponseData response = new ResponseData();
        try {
        response.setResponseCode("01");
        response.setResponseMessage("Full Statement not implemented yet");
        response.setResponseData(null);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return response;
    }

    // 4. Instant receipts (to be implemented properly later)
    public ResponseData instantReceipts(RequestData request) {
        ResponseData response = new ResponseData();
        try{
        	response.setResponseCode("01");
			response.setResponseMessage("Instant Receipts not implemented yet");
			response.setResponseData(null);
        }catch(Exception e) {
			e.printStackTrace();
			}
        return response;
    }
}
