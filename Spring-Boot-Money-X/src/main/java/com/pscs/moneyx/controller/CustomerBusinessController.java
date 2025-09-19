/**
 * 
 */
package com.pscs.moneyx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyx.entity.CustomerLogin;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.CustomerBusinessService;

/**
 * 
 */
@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerBusinessController {

	private final CustomerBusinessService customerLoginService;

	public CustomerBusinessController(CustomerBusinessService customerLoginService) {
		this.customerLoginService = customerLoginService;
	}

	// echo
	@GetMapping("/echo")
	public ResponseEntity<String> echo() {
		return new ResponseEntity<>("Welcome to PSCS", HttpStatus.OK);
	}
	//check username exist
	@PostMapping("/checkusername")
	public ResponseEntity<ResponseData> checkUserName(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.checkUserName(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	// create profile post api by using
	@PostMapping("/register")
	public ResponseEntity<ResponseData> createProfile(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.createProfile(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// create login post api by using
	@PostMapping("/login")
	public ResponseEntity<ResponseData> login(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.login(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// generate otp
	@PostMapping("/generateotp")
	public ResponseEntity<ResponseData> generateOtp(@RequestBody RequestData requestBody) {

		// Assuming a method to generate OTP exists in the service
		ResponseData response = customerLoginService.generateOtp(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// forget password
	@PostMapping("/forgetpassword")
	public ResponseEntity<ResponseData> forgetPassword(@RequestBody RequestData requestBody){
		
		ResponseData response = customerLoginService.forgetPassword(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	

	@PostMapping("/current-user")
	public ResponseEntity<ResponseData> getCurrentUser(@RequestBody RequestData requestBody) {
		CustomerLogin loggedInUser = customerLoginService.getLoggedInUser();

		if (loggedInUser == null) {
			ResponseData response = new ResponseData();
			response.setResponseCode("01");
			response.setResponseMessage("No user is logged in");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

		ResponseData response = new ResponseData();
		response.setResponseCode("00");
		response.setResponseMessage("User retrieved successfully");
		response.setResponseData(loggedInUser);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
  //fetch country
	@PostMapping("/fetchcountry")
	public ResponseEntity<ResponseData> fetchCountry(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchCountry();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//fetch business type
	@PostMapping("/fetchbusinesstype")
	public ResponseEntity<ResponseData> fetchBusinessType(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchBusinessType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//fetch assign role
	@PostMapping("/fetchassignrole")
	public ResponseEntity<ResponseData> fetchAssignRole(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchAssignRole();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//fetch upload document type based on country
	@PostMapping("/fetchdocumenttype")
	public ResponseEntity<ResponseData> fetchDocumentType(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.fetchDocumentType(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//view balance api
	@PostMapping("/viewbalance")
	public ResponseEntity<ResponseData> viewBalance(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.viewBalance(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/logout")
	public ResponseEntity<ResponseData> logout() {
		customerLoginService.logout();

		ResponseData response = new ResponseData();
		response.setResponseCode("00");
		response.setResponseMessage("Logout Successful");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//start from here embedly integration 
	
	@PostMapping("/createcustomer")
	public ResponseEntity<ResponseData> createCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.createCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//get Customer details by Customer id
	@PostMapping("/getcustomerdetails")
	public ResponseEntity<ResponseData> getCustomerDetails(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.getCustomerDetails(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//get all customer details
	@PostMapping("/getallcustomerdetails")
	public ResponseEntity<ResponseData> getAllCustomerDetails(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.getAllCustomerDetails(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
     //Update Customer Name
	@PostMapping("/UpdateCustomerName")
	public ResponseEntity<ResponseData> UpdateCustomerName(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.UpdateCustomerName(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Update Customer Contact
	@PostMapping("/UpdateCustomerContact")
	public ResponseEntity<ResponseData> UpdateCustomerContact(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.UpdateCustomerContact(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	//Get Customer KYC Property Status
	@PostMapping("/getCustKycStatus")
	public ResponseEntity<ResponseData> getCustKycStatus(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.getCustKycStatus(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	//Get All Customer types
	@PostMapping("/GetAllCustomerTypes")
	public ResponseEntity<ResponseData> GetAllCustomerTypes(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.GetAllCustomerTypes(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	// Create Corporate Customer
	@PostMapping("/CreateCorporateCustomer")
	public ResponseEntity<ResponseData> CreateCorporateCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.CreateCorporateCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	//Get A Corporate Customer
	@PostMapping("/GetACorporateCustomer")
	public ResponseEntity<ResponseData> GetACorporateCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.GetACorporateCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Update Corporate Customer
	@PostMapping("/updateCorpCustomer")
	public ResponseEntity<ResponseData> updateCorpCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerLoginService.updateCorpCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Add Director to Corporate Customer
    @PostMapping("/addDirector")
           public ResponseEntity<ResponseData> addDirector(@RequestBody RequestData requestBody) {
    	        ResponseData response = customerLoginService.addDirector(requestBody);
    	        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    //Get A Corporate Customer Director
    @PostMapping("/getCorpDirector")
	public ResponseEntity<ResponseData> getCorpDirector(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.getCorpDirector(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    //Get All Corporate Customer Directors
    @PostMapping("/getAllCorpDirectors")
        public ResponseEntity<ResponseData> getAllCorpDirectors(@RequestBody RequestData requestBody) {	
			ResponseData response = customerLoginService.getAllCorpDirectors(requestBody);
			return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //Update Corporate Customer Director
    @PostMapping("/updateCorpDirector")
        public ResponseEntity<ResponseData> updateCorpDirector(@RequestBody RequestData requestBody) {
    	            ResponseData response = customerLoginService.updateCorpDirector(requestBody);
    	            return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    //Upload Corporate Customer Documents
	@PostMapping("/uploadCorpDocuments")
	public ResponseEntity<ResponseData> uploadCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.uploadCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    //Update Corporate Customer Documents
	@PostMapping("/updateCorpDocuments")
	public ResponseEntity<ResponseData> updateCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.updateCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//Get Corporate Customer Documents
	@PostMapping("/getCorpDocuments")
	public ResponseEntity<ResponseData> getCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.getCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Create Wallet
    @PostMapping("/createWallet")
	public ResponseEntity<ResponseData> createWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.createWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
    //Create Corporate Customer Wallet
    @PostMapping("/createCustWallet")
        public ResponseEntity<ResponseData> createCustWallet(@RequestBody RequestData requestBody) {
			ResponseData response = customerLoginService.createCustWallet(requestBody);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //Get Wallet by ID
	@PostMapping("/getWalletById")
        public ResponseEntity<ResponseData> getWalletById(@RequestBody RequestData requestBody) {
		    ResponseData response = customerLoginService.getWalletById(requestBody);
		    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Get Wallet by Account Number
	@PostMapping("/getWalletByAccNumber")
	public ResponseEntity<ResponseData> getWalletByAccNumber(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.getWalletByAccNumber(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// Wallet to Wallet Transfers
	@PostMapping("/transferWallet")
	public ResponseEntity<ResponseData> transferWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.transferWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
     
	//Get Organization Wallet Transactions
    @PostMapping("/getOrgWalletTrans")
        public ResponseEntity<ResponseData> getOrgWalletTrans(@RequestBody RequestData requestBody) {
    	            ResponseData response = customerLoginService.getOrgWalletTrans(requestBody);
    	            return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    Wallet to Wallet Requery
	@PostMapping("/requeryWalletTrans")
	public ResponseEntity<ResponseData> requeryWalletTrans(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.requeryWalletTrans(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//Wallet History
	@PostMapping("/walletHistory")
	public ResponseEntity<ResponseData> walletHistory(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.walletHistory(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//Reverse Transaction
	@PostMapping("/reverseTxn")
	public ResponseEntity<ResponseData> reverseTxn(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.reverseTxn(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//Close Wallet
	@PostMapping("/closeWallet")
	public ResponseEntity<ResponseData> closeWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.closeWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//Restrict by AccountID
	@PostMapping("/restrictByAccId")
	public ResponseEntity<ResponseData> restrictByAccId(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.restrictByAccId(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//	Restrict Wallet
	@PostMapping("/restrictWallet")
	public ResponseEntity<ResponseData> restrictWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerLoginService.restrictWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
