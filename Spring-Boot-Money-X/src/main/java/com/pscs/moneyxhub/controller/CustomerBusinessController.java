package com.pscs.moneyxhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscs.moneyxhub.entity.CustomerLogin;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.services.CustomerBusinessService;

/**
 * @author Dipak Kumar
 */
@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerBusinessController {

	private final CustomerBusinessService customerBusinessService;

	public CustomerBusinessController(CustomerBusinessService customerBusinessService) {
		this.customerBusinessService = customerBusinessService;
	}


	// echo
	@GetMapping("/echo")
	public ResponseEntity<String> echo() {
		return new ResponseEntity<>("Welcome to PSCS", HttpStatus.OK);
	}

	// check username exist
	@PostMapping("/checkusername")
	public ResponseEntity<ResponseData> checkUserName(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.checkUserName(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// create profile post api by using
	@PostMapping("/register")
	public ResponseEntity<ResponseData> createProfile(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.createProfile(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// create login post api by using
	@PostMapping("/login")
	public ResponseEntity<ResponseData> login(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.login(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// generate otp
	@PostMapping("/generateotp")
	public ResponseEntity<ResponseData> generateOtp(@RequestBody RequestData requestBody) {

		// Assuming a method to generate OTP exists in the service
		ResponseData response = customerBusinessService.generateOtp(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// forget password
	@PostMapping("/forgetpassword")
	public ResponseEntity<ResponseData> forgetPassword(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.forgetPassword(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/current-user")
	public ResponseEntity<ResponseData> getCurrentUser(@RequestBody RequestData requestBody) {
		CustomerLogin loggedInUser = customerBusinessService.getLoggedInUser();

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

	// fetch country
	@PostMapping("/fetchcountry")
	public ResponseEntity<ResponseData> fetchCountry(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.fetchCountry(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//fetch country dial code
	@PostMapping("/fetchcountrydialcode")
	public ResponseEntity<ResponseData> fetchCountryDialCode(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.fetchCountryDialCode();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	// fetch business type
	@PostMapping("/fetchbusinesstype")
	public ResponseEntity<ResponseData> fetchBusinessType(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.fetchBusinessType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// fetch assign role
	@PostMapping("/fetchassignrole")
	public ResponseEntity<ResponseData> fetchAssignRole(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.fetchAssignRole();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// fetch upload document type based on country
	@PostMapping("/fetchdocumenttype")
	public ResponseEntity<ResponseData> fetchDocumentType(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.fetchDocumentType(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// view balance api
	@PostMapping("/viewbalance")
	public ResponseEntity<ResponseData> viewBalance(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.viewBalance(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseData> logout() {
		customerBusinessService.logout();

		ResponseData response = new ResponseData();
		response.setResponseCode("00");
		response.setResponseMessage("Logout Successful");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//start from here embedly integration 

	@PostMapping("/createcustomer")
	public ResponseEntity<ResponseData> createCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.createCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// get Customer details by Customer id
	@PostMapping("/getcustomerdetails")
	public ResponseEntity<ResponseData> getCustomerDetails(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.getCustomerDetails(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// get all customer details
	@PostMapping("/getallcustomerdetails")
	public ResponseEntity<ResponseData> getAllCustomerDetails(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.getAllCustomerDetails(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Customer Name
	@PostMapping("/UpdateCustomerName")
	public ResponseEntity<ResponseData> UpdateCustomerName(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.UpdateCustomerName(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Customer Contact
	@PostMapping("/UpdateCustomerContact")
	public ResponseEntity<ResponseData> UpdateCustomerContact(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.UpdateCustomerContact(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get Customer KYC Property Status
	@PostMapping("/getCustKycStatus")
	public ResponseEntity<ResponseData> getCustKycStatus(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.getCustKycStatus(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Create Corporate Customer
	@PostMapping("/CreateCorporateCustomer")
	public ResponseEntity<ResponseData> CreateCorporateCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.CreateCorporateCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get A Corporate Customer
	@PostMapping("/GetACorporateCustomer")
	public ResponseEntity<ResponseData> GetACorporateCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.GetACorporateCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Corporate Customer
	@PostMapping("/updateCorpCustomer")
	public ResponseEntity<ResponseData> updateCorpCustomer(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.updateCorpCustomer(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Add Director to Corporate Customer
	@PostMapping("/addDirector")
	public ResponseEntity<ResponseData> addDirector(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.addDirector(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get A Corporate Customer Director
	@PostMapping("/getCorpDirector")
	public ResponseEntity<ResponseData> getCorpDirector(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getCorpDirector(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get All Corporate Customer Directors
	@PostMapping("/getAllCorpDirectors")
	public ResponseEntity<ResponseData> getAllCorpDirectors(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getAllCorpDirectors(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Corporate Customer Director
	@PostMapping("/updateCorpDirector")
	public ResponseEntity<ResponseData> updateCorpDirector(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.updateCorpDirector(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Upload Corporate Customer Documents
	@PostMapping("/uploadCorpDocuments")
	public ResponseEntity<ResponseData> uploadCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.uploadCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Corporate Customer Documents
	@PostMapping("/updateCorpDocuments")
	public ResponseEntity<ResponseData> updateCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.updateCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get Corporate Customer Documents
	@PostMapping("/getCorpDocuments")
	public ResponseEntity<ResponseData> getCorpDocuments(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getCorpDocuments(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get All Customer types
	@PostMapping("/getAllCustomerTypes")
	public ResponseEntity<ResponseData> GetAllCustomerTypes(@RequestBody RequestData requestBody) {

		ResponseData response = customerBusinessService.GetAllCustomerTypes(requestBody);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Create Wallet
	@PostMapping("/createWallet")
	public ResponseEntity<ResponseData> createWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.createWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Create Corporate Customer Wallet
	@PostMapping("/createCustWallet")
	public ResponseEntity<ResponseData> createCustWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.createCustWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get Wallet by ID
	@PostMapping("/getWalletById")
	public ResponseEntity<ResponseData> getWalletById(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getWalletById(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get Wallet by Account Number
	@PostMapping("/getWalletByAccNumber")
	public ResponseEntity<ResponseData> getWalletByAccNumber(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getWalletByAccNumber(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	// Get Organization Wallet Transactions
	@PostMapping("/getOrgWalletTrans")
	public ResponseEntity<ResponseData> getOrgWalletTrans(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getOrgWalletTrans(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	// Wallet History
	@PostMapping("/walletHistory")
	public ResponseEntity<ResponseData> walletHistory(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.walletHistory(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	// Close Wallet
	@PostMapping("/closeWallet")
	public ResponseEntity<ResponseData> closeWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.closeWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Restrict by AccountID
	@PostMapping("/restrictByAccId")
	public ResponseEntity<ResponseData> restrictByAccId(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.restrictByAccId(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	Restrict Wallet
	@PostMapping("/restrictWallet")
	public ResponseEntity<ResponseData> restrictWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.restrictWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	Retrieve Currencies
	@PostMapping("/retrieveCurrencies")
	public ResponseEntity<ResponseData> retrieveCurrencies(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.retrieveCurrencies(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Retrieve Wallet Restriction Types
	@PostMapping("/getWltResTypes")
	public ResponseEntity<ResponseData> getWltResTypes(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getWltResTypes(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Account Closure
	@PostMapping("/accountClosure")
	public ResponseEntity<ResponseData> accountClosure(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.accountClosure(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Add WalletGroup
	@PostMapping("/addWalletGroup")
	public ResponseEntity<ResponseData> addWalletGroup(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.addWalletGroup(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

//	Add Wallet to WalletGroup
	@PostMapping("/addWalletToGroup")
	public ResponseEntity<ResponseData> addWalletToGroup(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.addWalletToGroup(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//Add WalletGroup Feature
	@PostMapping("/addWalletGroupFeature")
	public ResponseEntity<ResponseData> addWalletGroupFeature(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.addWalletGroupFeature(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// WalletGroup Feature
	@PostMapping("/getWalletGroupFeature")
	public ResponseEntity<ResponseData> getWalletGroupFeature(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getWalletGroupFeature(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// WalletGroup ID
	@PostMapping("/getWalletGroupById")
	public ResponseEntity<ResponseData> getWalletGroupById(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getWalletGroupById(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// WalletGroup
	@PostMapping("/getAllWalletGroups")
	public ResponseEntity<ResponseData> getAllWalletGroups(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getAllWalletGroups(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Create new product
	@PostMapping("/createProduct")
	public ResponseEntity<ResponseData> createProduct(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.createProduct(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// List of all products
	@PostMapping("/getAllProducts")
	public ResponseEntity<ResponseData> getAllProducts(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getAllProducts(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update existing Product
	@PostMapping("/updateProduct")
	public ResponseEntity<ResponseData> updateProduct(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.updateProduct(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Activate Product by its ID
	@PostMapping("/activateProduct")
	public ResponseEntity<ResponseData> activateProduct(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.activateProduct(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Deactivate Product by its ID
	@PostMapping("/deactivateProduct")
	public ResponseEntity<ResponseData> deactivateProduct(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.deactivateProduct(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Retrieve Limit of a product and Currency
	@PostMapping("/getProductLimit")
	public ResponseEntity<ResponseData> getProductLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getProductLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Limit for Customer
	@PostMapping("/setCustomerLimit")
	public ResponseEntity<ResponseData> setCustomerLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.setCustomerLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Default transaction limits
	@PostMapping("/setDefaultTxnLimit")
	public ResponseEntity<ResponseData> setDefaultTxnLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.setDefaultTxnLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Limit Customer
	@PostMapping("/getCustomerLimit")
	public ResponseEntity<ResponseData> getCustomerLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getCustomerLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update default transaction limit
	@PostMapping("/updDefTxnLimit")
	public ResponseEntity<ResponseData> updDefTxnLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.updDefTxnLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Add Limit to existing Product
	@PostMapping("/addProductLimit")
	public ResponseEntity<ResponseData> addProductLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.addProductLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Customer Limit
	@PostMapping("/customerLimit")
	public ResponseEntity<ResponseData> customerLimit(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.customerLimit(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get Organization Prefix Mappings
	@PostMapping("/getOrgPrefixMappings")
	public ResponseEntity<ResponseData> getOrgPrefixMappings(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getOrgPrefixMappings(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Create Checkout Wallet
	@PostMapping("/createCheckoutWallet")
	public ResponseEntity<ResponseData> createCheckoutWallet(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.createCheckoutWallet(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get checkout wallets
	@PostMapping("/getChckkoWallets")
	public ResponseEntity<ResponseData> getChckkoWallets(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getChckkoWallets(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get checkout wallet transactions
	@PostMapping("/getChckkoWltTrans")
	public ResponseEntity<ResponseData> getChckkoWltTrans(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getChckkoWltTrans(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Issue Afrigo Card
	@PostMapping("/issueAfrigoCard")
	public ResponseEntity<ResponseData> issueAfrigoCard(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.issueAfrigoCard(requestBody);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Activate Afrigo Card
	@PostMapping("/activateAfrigoCard")
	public ResponseEntity<ResponseData> activateAfrigoCard(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.activateAfrigoCard(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Update Afrigo Card Information
	@PostMapping("/updateAfrigoCardInfo")
	public ResponseEntity<ResponseData> updateAfrigoCardInfo(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.updateAfrigoCardInfo(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Map Physical Afrigo to Customer
	@PostMapping("/mapPhysAfrigoCard")
	public ResponseEntity<ResponseData> mapPhysAfrigoCard(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.mapPhysAfrigoCard(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//get OrganizationId
    @PostMapping("/getOrgId")
	public ResponseEntity<ResponseData> getOrgId(@RequestBody RequestData requestBody) {
		ResponseData response = customerBusinessService.getOrgId(requestBody);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
