package com.pscs.moneyxhub.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.embedly.caller.EmbedlyServiceCaller;
import com.pscs.moneyxhub.entity.CorporateCustomer;
import com.pscs.moneyxhub.entity.Country;
import com.pscs.moneyxhub.entity.CustomerDocInfo;
import com.pscs.moneyxhub.entity.CustomerLogin;
import com.pscs.moneyxhub.entity.MoneyXBusiness;
import com.pscs.moneyxhub.entity.OtpDataTabl;
import com.pscs.moneyxhub.entity.Transactions;
import com.pscs.moneyxhub.entity.WalletAcctData;
import com.pscs.moneyxhub.entity.WalletAcctData_old;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.ImageUpload;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.BusinessRoleRepo;
import com.pscs.moneyxhub.repo.BusinessTypeRepo;
import com.pscs.moneyxhub.repo.CorporateCustomerRepo;
import com.pscs.moneyxhub.repo.CountryRepo;
import com.pscs.moneyxhub.repo.CustomerDocInfoRepo;
import com.pscs.moneyxhub.repo.CustomerLoginRepo;
import com.pscs.moneyxhub.repo.DocumentRepo;
import com.pscs.moneyxhub.repo.MoneyXBusinessRepo;
import com.pscs.moneyxhub.repo.OtpDataTablRepo;
import com.pscs.moneyxhub.repo.TransactionsRepo;
import com.pscs.moneyxhub.repo.WalletAcctDataRepository;
import com.pscs.moneyxhub.services.post.EmailAndSMSPostingService;
import com.pscs.moneyxhub.utils.CommonUtils;

import jakarta.transaction.Transactional;

@Service
public class CustomerBusinessService {
	private static final Logger logger = Logger.getLogger(CustomerBusinessService.class);
	private ResourceBundle bundle = ResourceBundle.getBundle("embedlyservice");
	private final CustomerLoginRepo customerLoginRepo;
	private final CountryRepo countryRepo;
	private final BusinessTypeRepo businessTypeRepo;
	private final BusinessRoleRepo businessRoleRepo;
	private final CustomerDocInfoRepo customerDocInfoRepo;
	private final DocumentRepo documentRepo;
	private final OtpDataTablRepo otpDataTablRepo;
	private final EmailAndSMSPostingService smsPostingService;
	private final MoneyXBusinessRepo moneyXBusinessRepo;
	private final WalletAcctDataRepository walletAcctDataRepository;
	private final CorporateCustomerRepo corporateCustomerRepo;
	private final TransactionsRepo transactionsRepo;

	public CustomerBusinessService(CustomerLoginRepo customerLoginRepo, CountryRepo countryRepo,
			BusinessTypeRepo businessTypeRepo, BusinessRoleRepo businessRoleRepo,
			CustomerDocInfoRepo customerDocInfoRepo, DocumentRepo documentRepo, OtpDataTablRepo otpDataTablRepo,
			EmailAndSMSPostingService smsPostingService, MoneyXBusinessRepo moneyXBusinessRepo,
			WalletAcctDataRepository walletAcctDataRepository, CorporateCustomerRepo corporateCustomerRepo, TransactionsRepo transactionsRepo) {
		this.customerLoginRepo = customerLoginRepo;
		this.countryRepo = countryRepo;
		this.businessTypeRepo = businessTypeRepo;
		this.businessRoleRepo = businessRoleRepo;
		this.customerDocInfoRepo = customerDocInfoRepo;
		this.documentRepo = documentRepo;
		this.otpDataTablRepo = otpDataTablRepo;
		this.smsPostingService = smsPostingService;
		this.moneyXBusinessRepo = moneyXBusinessRepo;
		this.walletAcctDataRepository = walletAcctDataRepository;
		this.corporateCustomerRepo = corporateCustomerRepo;
		this.transactionsRepo = transactionsRepo;
	}

	// find by username
	@Transactional
	public ResponseData login(RequestData request) {
		ResponseData response = new ResponseData();
		String isLoginAttemptActive = "N";
		int maxRetryLoginAttempt = 3;
		int retryLoginAttempt = 0;
		try {
			
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			// Convert the request body to a JSON object
			JSONObject requestJson = new JSONObject(jsonString);
			logger.info("Request Body: " + requestJson.toString());
			
			String customerType = requestJson.has("customerType") ? requestJson.getString("customerType") : "INDIVIDUAL";
			
			if (customerType.toUpperCase().equals("CORPORATE")) {

				return corporateCustomerLogin(request);
			}else {
			MoneyXBusiness checkCustomerres = moneyXBusinessRepo.findByUserName(requestJson.getString("username"));
			if (checkCustomerres != null) {
				
//				ResponseData validateOtp = validateOtp(request);
				
				
				
				
				isLoginAttemptActive = checkCustomerres.getIsLoginAttemptActive();
				retryLoginAttempt = checkCustomerres.getRetryLoginAttempt();

				if (checkCustomerres.getIsActive().equals("N")) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INACTIVE_AC);
					return response;
				}
				if (checkCustomerres.getIsLocked().equals("L")) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.ACCOUNT_LOCKED);
					return response;
				}

				checkCustomerres = moneyXBusinessRepo.findByUserNameAndPassword(requestJson.getString("username"),
						CommonUtils.b64_sha256(requestJson.getString("password")));
				// check if user exist
				if (checkCustomerres == null) {

					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INVALID_CREDENTIALS);

					if (isLoginAttemptActive.equals("Y")) {

						if (retryLoginAttempt >= maxRetryLoginAttempt) {
							moneyXBusinessRepo.updateIsLockedByUserName("L", requestJson.getString("username"));
							response.setResponseCode(CoreConstant.FAILURE_CODE);
							response.setResponseMessage(CoreConstant.ACCOUNT_LOCKED);
							return response;
						} else {
							response.setResponseMessage("Invalid Username or Password You Have "
									+ (maxRetryLoginAttempt - retryLoginAttempt) + " Attempt Left");
							moneyXBusinessRepo.incrementRetryLoginAttemptNative(requestJson.getString("username"));
						}

						return response;
					}

				} else {
					checkCustomerres.setLastLoginTime(new Date() + "");

					
					
					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.LOGIN_SUCCESSFUL);
					
					String customerResponse = ConvertRequestUtils.getJsonString(checkCustomerres);
					JSONObject customerJson = new JSONObject(customerResponse);
					
					
					
					WalletAcctData walletAcctData = walletAcctDataRepository.findByCustId(checkCustomerres.getCustomerId());
					if (walletAcctData != null) {
							customerJson.put("walletId",walletAcctData.getWalletId());
							customerJson.put("accountNumber", walletAcctData.getAcctNo());
						
						} else {
							customerJson.put("walletId", "NA");
							customerJson.put("accountNumber", "NA");
							
						}
					
					
					response.setResponseData(customerJson.toMap());
					if (isLoginAttemptActive.equals("Y")) {
						moneyXBusinessRepo.resetRetryLoginAttempt(checkCustomerres.getUserName());
					}
				}

			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.USER_NOT_FOUND);
				return response;
			}
			
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}


	private ResponseData corporateCustomerLogin(RequestData request) {
		ResponseData response = new ResponseData();
		String isLoginAttemptActive = "N";
		int maxRetryLoginAttempt = 3;
		int retryLoginAttempt = 0;
		try {
			
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			// Convert the request body to a JSON object
			JSONObject requestJson = new JSONObject(jsonString);
			logger.info("Request Body: " + requestJson.toString());
			
		
			CorporateCustomer checkCustomerres = corporateCustomerRepo.findByUserName(requestJson.getString("username"));
			if (checkCustomerres != null) {
				
//				ResponseData validateOtp = validateOtp(request);
				
				
				
				
				isLoginAttemptActive = checkCustomerres.getIsLoginAttemptActive();
				retryLoginAttempt = checkCustomerres.getRetryLoginAttempt();

				if (checkCustomerres.getIsActive().equals("N")) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INACTIVE_AC);
					return response;
				}
				if (checkCustomerres.getIsLocked().equals("L")) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.ACCOUNT_LOCKED);
					return response;
				}

				checkCustomerres = corporateCustomerRepo.findByUserNameAndPassword(requestJson.getString("username"),
						CommonUtils.b64_sha256(requestJson.getString("password")));
				// check if user exist
				if (checkCustomerres == null) {

					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INVALID_CREDENTIALS);

					if (isLoginAttemptActive.equals("Y")) {

						if (retryLoginAttempt >= maxRetryLoginAttempt) {
							corporateCustomerRepo.updateIsLockedByUserName("L", requestJson.getString("username"));
							response.setResponseCode(CoreConstant.FAILURE_CODE);
							response.setResponseMessage(CoreConstant.ACCOUNT_LOCKED);
							return response;
						} else {
							response.setResponseMessage("Invalid Username or Password You Have "
									+ (maxRetryLoginAttempt - retryLoginAttempt) + " Attempt Left");
							corporateCustomerRepo.incrementRetryLoginAttemptNative(requestJson.getString("username"));
						}

						return response;
					}

				} else {
					checkCustomerres.setLastLoginTime(new Date() + "");

					
					
					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.LOGIN_SUCCESSFUL);
					
					String customerResponse = ConvertRequestUtils.getJsonString(checkCustomerres);
					JSONObject customerJson = new JSONObject(customerResponse);
					
					
					
					WalletAcctData walletAcctData = walletAcctDataRepository.findByCustId(checkCustomerres.getCustomerId());
					if (walletAcctData != null) {
							customerJson.put("walletId",walletAcctData.getWalletId());
							customerJson.put("accountNumber", walletAcctData.getAcctNo());
						} else {
							customerJson.put("walletId", "NA");
							customerJson.put("accountNumber", "NA");
							
						}
					
					
					response.setResponseData(customerJson.toMap());
					if (isLoginAttemptActive.equals("Y")) {
						corporateCustomerRepo.resetRetryLoginAttempt(checkCustomerres.getUserName());
					}
				}

			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.USER_NOT_FOUND);
				return response;
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;

	}

	public ResponseData checkUserName(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			// Convert the request body to a JSON object
			JSONObject requestJson = new JSONObject(jsonString);
			logger.info("Request Body: " + requestJson.toString());
			MoneyXBusiness checkCustomerres = moneyXBusinessRepo.findByUserName(requestJson.getString("username"));
			if (checkCustomerres != null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.USER_EXIST);
			}else {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.USER_NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Transactional
	public ResponseData createProfile(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			// Convert the request body to a JSON object
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject jsonObject = new JSONObject(jsonString);
			logger.info("Request Body: " + jsonObject.toString());

			MoneyXBusiness customerLogin = new MoneyXBusiness();

			

			customerLogin.setOrganizationId(jsonObject.getString("organizationId"));
			customerLogin.setFirstName(jsonObject.getString("firstName"));
			customerLogin.setLastName(jsonObject.getString("lastName"));
			customerLogin.setMiddleName(jsonObject.getString("middleName"));
			customerLogin.setEmailAddress(jsonObject.getString("emailAddress"));
			customerLogin.setMobileNumber(jsonObject.getString("mobileNumber"));
			customerLogin.setDob(jsonObject.getString("dob"));
			customerLogin.setCustomerTypeId(jsonObject.getString("customerTypeId"));
			customerLogin.setAddress(jsonObject.getString("address"));
			customerLogin.setCity(jsonObject.getString("city"));
			customerLogin.setCountryId(jsonObject.getString("countryId"));
			customerLogin.setAlias(jsonObject.getString("alias"));
			customerLogin.setCurrency(jsonObject.has("currency") ?jsonObject.getString("currency"):"NGN");
			customerLogin.setUserName(jsonObject.getString("userName"));
			customerLogin.setPassword(CommonUtils.b64_sha256(jsonObject.getString("password")));
			customerLogin.setTxnPin(jsonObject.getString("txnPin"));
			customerLogin.setAuthType(jsonObject.getString("authType"));
			customerLogin.setAuthValue(jsonObject.getString("authValue"));
			customerLogin.setCustomerType(jsonObject.has("customerType") ?  jsonObject.getString("customerType"):"");
			customerLogin.setCustomerId(jsonObject.has("customerId") ?jsonObject.getString("customerId"):"");
			customerLogin.setCuntry(jsonObject.has("cuntry") ? jsonObject.getString("cuntry") :"");
			
			
			
			

			
			ResponseData validateOtp = validateOtp(request);	
			if (!validateOtp.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateOtp; // Return if OTP validation fails
			}else {
			
			customerLogin.setIsActive("A");
			customerLogin.setIsLocked("N");
			customerLogin.setIsLoginAttemptActive("Y");
			customerLogin.setRetryLoginAttempt(0);
			

			MoneyXBusiness customer = moneyXBusinessRepo.findByEmailAddressOrUserName(jsonObject.getString("emailAddress"),jsonObject.getString("userName"));
			if (customer == null) {
				
				String requestJson = ConvertRequestUtils.getJsonString(request);

				JSONObject reqJson = new JSONObject(requestJson);
				
				

				
				EmbedlyServiceCaller service = new EmbedlyServiceCaller();
				 JSONObject callService = service.callService(reqJson);
				
				if (callService.getString("respCode").equals("00")) {
					
					
					JSONObject responseData = callService.getJSONObject("data");
					
					customerLogin.setCustomerId(responseData.getString("id"));
					customerLogin.setCustomerTierId(responseData.has("customerTierId") ? responseData.getInt("customerTierId")+"" :"");
					
					customer = moneyXBusinessRepo.save(customerLogin);
					if (customer == null) {
						response.setResponseCode(CoreConstant.FAILURE_CODE);
						response.setResponseMessage(CoreConstant.FAILED + " to Create Profile");

					} else {
						response.setResponseCode(CoreConstant.SUCCESS_CODE);
						response.setResponseMessage(CoreConstant.SUCCESS + " Profile Created Successfully");
					}
					buildResponseData(response, callService);
				} else {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.FAILED +" " + callService.getString("respmsg"));
				}
				
			
				
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(
						CoreConstant.RECORD_ALREADY_EXISTS+":" + jsonObject.getString("emailAddress"));
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public ResponseData generateOtp(RequestData request) {
		ResponseData response = new ResponseData();
		try {

			String mobileNumber = "";
			String email = "";
			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			String strJheader = ConvertRequestUtils.getJsonString(request.getJheader());
			JSONObject jHeader = new JSONObject(strJheader);

			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println("Request Body: " + jsonObject.toString());
			
			String requestType = jHeader.getString("requestType");
			
			if (requestType.equals("LOGIN_OTP")) {
				
				MoneyXBusiness byUserName = moneyXBusinessRepo.findByUserName(jsonObject.getString("username"));
				mobileNumber= byUserName != null ? byUserName.getMobileNumber():"";
				email = byUserName != null ? byUserName.getEmailAddress():"";
				
			}
			else {
				mobileNumber = jsonObject.getString("mobileNumber");
				email = jsonObject.has("email")?   jsonObject.getString("email"):"";
			}
			
			
			otpDataTablRepo.updateMobileNoandOtpStatus(mobileNumber, "E");
			
			String otp = "221232" ;// CommonUtils.createRandomNumber(6);
			String encryptedOtp = CommonUtils.b64_sha256(otp);
			// generate otp by using random
			OtpDataTabl otpDataTabl = new OtpDataTabl();
			otpDataTabl.setOtp(encryptedOtp);
			otpDataTabl.setUserId(jHeader.getString("userid"));
			otpDataTabl.setTransType(jHeader.getString("requestType"));
			otpDataTabl.setMobileNo(mobileNumber);
			otpDataTabl.setEmailId(email);
			
			
			
			//get email from db if email is blank
			if (otpDataTabl.getEmailId() == null || otpDataTabl.getEmailId().isEmpty()) {
				MoneyXBusiness business = moneyXBusinessRepo.findByUserName(jHeader.getString("userid"));
				if (business != null) {
					otpDataTabl.setEmailId(business.getEmailAddress());
				}
			}
			// get phone from db if phone is blank
			if (otpDataTabl.getMobileNo() == null || otpDataTabl.getMobileNo().isEmpty()) {
				MoneyXBusiness business = moneyXBusinessRepo.findByUserName(jHeader.getString("userid"));
				if (business != null) {
					otpDataTabl.setMobileNo(business.getMobileNumber());
				}
			}
			
			
			
			otpDataTabl.setChannel(jHeader.getString("channel"));
			otpDataTabl.setOtpStatus("A");

			OtpDataTabl save = otpDataTablRepo.save(otpDataTabl);

			if (save == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + " to Generate OTP");
				return response;
			} else {
				JSONObject smsRequest = new JSONObject();
				if (mobileNumber.startsWith("234")) {

					smsRequest.put("messages", ConvertRequestUtils.generateSMSJson(mobileNumber,
							jHeader.getString("requestType"), otp));

				} else {
					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.SMS_SENT + otp);
					return response;
				}

				response.setResponseMessage(CoreConstant.OTP_SENT + otp);
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				JSONObject sendSMSRes = smsPostingService.sendPostRequest(smsRequest.toString(),"sms.url");
				
				JSONObject emailRequest = new JSONObject();
				
				emailRequest.put("messages", ConvertRequestUtils.generateEmailJson(otpDataTabl.getEmailId(),
						"MoneyX One Time Password", "Dear Customer, Your OTP is " + otp));
				
				JSONObject sendMailRes = smsPostingService.sendPostRequest(emailRequest.toString(),"email.url");

				if (sendSMSRes.getString("respsode").equals("200")) {
					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.SMS_SENT + otp);
					response.setResponseData(sendSMSRes.toMap());

				} else {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.FAILED + " to send SMS");
					response.setResponseData(sendSMSRes.toMap());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED );

		}
		return response;

	}

	// Validate otp
	public ResponseData validateOtp(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			
			String mobileNumber = "";
			String email = "";

			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			String strJheader = ConvertRequestUtils.getJsonString(request.getJheader());
			JSONObject jHeader = new JSONObject(strJheader);
			JSONObject requestJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + requestJson.toString());
			
			String requestType = jHeader.getString("requestType");
			
			
			if (requestType.equals("LOGIN")) {

				MoneyXBusiness byUserName = moneyXBusinessRepo.findByUserName(requestJson.getString("username"));
				mobileNumber = byUserName != null ? byUserName.getMobileNumber() : "";
				email = byUserName != null ? byUserName.getEmailAddress() : "";

			} else {
				mobileNumber = requestJson.getString("mobileNumber");
				email = requestJson.has("email") ? requestJson.getString("email") : "";
			}
			

			String otp = requestJson.getString("authValue");
			String username = jHeader.getString("userid");
//			String mobileNumber = requestJson.getString("mobileNumber");
			String encryptedOtp = CommonUtils.b64_sha256(otp);
			
			
			
			OtpDataTabl otpDataTabl = otpDataTablRepo.findByMobileNoAndOtpAndOtpStatus(mobileNumber, encryptedOtp,"A");


			if (otpDataTabl != null) {
				long otpCreationTime = otpDataTabl.getTransDttm().getTime();
				long currentTime = System.currentTimeMillis();
				long timeDifference = currentTime - otpCreationTime;

				// Check if the OTP is older than 2 minutes (120000 milliseconds) use constant
				// for 2 minutes
				if (timeDifference > CommonUtils.OTP_VALIDITY_DURATION) {
					otpDataTabl.setOtpStatus("E"); // Set status to Expired
					otpDataTablRepo.save(otpDataTabl);
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.EXPIRED_OTP);
					return response;
				}
			}

			if (otpDataTabl == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.INVALID_OTP);
				return response;
			} else if (otpDataTabl.getOtpStatus().equals("A")) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.OTP_VERIFIED);
				// if success the update the oto status to S
				otpDataTabl.setOtpStatus("S");
				otpDataTablRepo.save(otpDataTabl);

			} else if (otpDataTabl.getOtpStatus().equals("S")) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.OTP_USED);
			} else if (otpDataTabl.getOtpStatus().equals("E")) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.EXPIRED_OTP);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + " to Validate OTP");
		}
		return response;
	}

	// create forget password method
	public ResponseData forgetPassword(RequestData request) {
		ResponseData response = new ResponseData();
		try {

			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject requestJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + requestJson.toString());

			String username = requestJson.getString("username");
			String otp = requestJson.getString("authValue");
			String email = requestJson.getString("email");
			String mobileNumber = requestJson.getString("mobileNumber");

			ResponseData validateOtp = validateOtp(request);
			if (!validateOtp.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateOtp; // Return if OTP validation fails
			} else {
				String oldPassword = CommonUtils.b64_sha256(requestJson.getString("password"));
				String newPassword = CommonUtils.b64_sha256(requestJson.getString("newPassword"));
				// find customer login by username
				MoneyXBusiness customerLogin = moneyXBusinessRepo.findByUserName(username);

				if (customerLogin == null) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INVALID_USERNAME);

				} else {
					customerLogin.setPassword(newPassword);
					moneyXBusinessRepo.save(customerLogin);

					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.PASSWORD_CHANGED );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + " to Change Password");
		}
		return response;
	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	public CustomerLogin getLoggedInUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseData fetchCountry(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED +" " + callService.getString("respmsg"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + " to fetch countries");
		}

		return response;
	}

	public ResponseData fetchBusinessType() {

		ResponseData response = new ResponseData();
		try {
			List<String> businessTypes = new ArrayList<>();
			JSONObject jsonObject = new JSONObject();
			businessTypeRepo.findAll().forEach(businessType -> {
				businessTypes.add(businessType.getBusinessType());
			});

			jsonObject.put("businessTypes", businessTypes);
			response.setResponseData(jsonObject.toMap());
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS );

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + " to fetch business types");
		}
		return response;
	}

	public ResponseData fetchAssignRole() {
		ResponseData response = new ResponseData();
		try {
			List<String> roles = new ArrayList<>();
			businessRoleRepo.findAll().forEach(role -> {
				roles.add(role.getRoleName());
			});
			response.setResponseData(roles);
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS );
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + " to fetch roles");
		}

		return response;
	}

	public ResponseData uploadImage(ImageUpload imageUpload) {
		ResponseData response = new ResponseData();
		try {
			JSONObject resjson = new JSONObject();

			CustomerDocInfo mobCustomerDocInfo = new CustomerDocInfo();
			String docId = "PSCS" + System.nanoTime();
			mobCustomerDocInfo.setId(docId);
			mobCustomerDocInfo.setImageData(imageUpload.getFile().getBytes());
			mobCustomerDocInfo.setImageType(imageUpload.getFileType());
			mobCustomerDocInfo.setMakerId(imageUpload.getUserId());
			mobCustomerDocInfo.setMakerDttm(new Date());
			CustomerDocInfo saveresponse = customerDocInfoRepo.save(mobCustomerDocInfo);

			resjson.put("docUploadId", docId);

			if (saveresponse == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED + " to upload image");
				return response;
			} else {

				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS );
				response.setResponseData(resjson.toMap());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
			return response;
		}

		return response;
	}

	public ResponseData fetchDocumentType(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			List<String> documentTypes = new ArrayList<>();
			JSONObject jsonObject = new JSONObject();

			// Fetch document types based on countryId
			documentRepo.findByCountryCode(reqJson.getString("countryCode")).forEach(document -> {
				documentTypes.add(document.getDocumentType());
			});

			jsonObject.put("documentTypes", documentTypes);
			response.setResponseData(jsonObject.toMap());
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS );

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}

		return response;
	}

	public ResponseData viewBalance(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED +" " + callService.getString("respmsg"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData createCustomer(RequestData requestBody) {
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
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData getCustomerDetails(RequestData requestBody) {
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

	public ResponseData getAllCustomerDetails(RequestData requestBody) {
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
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData UpdateCustomerName(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);
	        String jsonStringHeader = ConvertRequestUtils.getJsonString(requestBody.getJheader());

	        JSONObject jsonHeader = new JSONObject(jsonStringHeader);
			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());


			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + callService);
			
			if (callService.getString("respCode").equals("00")) {
				
				
				String userId = jsonHeader.optString("userid");
	            MoneyXBusiness byUserName = moneyXBusinessRepo.findByUserName(userId);

				if (byUserName == null) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.USER_NOT_FOUND + ": " + userId);
					return response;
				}
	            byUserName.setFirstName(reqJson.optString("firstName", byUserName.getFirstName()));
	            byUserName.setLastName(reqJson.optString("lastName", byUserName.getLastName()));
	            byUserName.setMiddleName(reqJson.optString("middleName", byUserName.getMiddleName()));
	            byUserName.setDob(reqJson.optString("dob", byUserName.getDob()));
	            byUserName.setCity(reqJson.optString("city", byUserName.getCity()));
	            byUserName.setAddress(reqJson.optString("address", byUserName.getAddress()));
	            byUserName.setOccupation(reqJson.optString("occupation", byUserName.getOccupation()));
				byUserName.setGender(reqJson.optString("gender", byUserName.getGender()));
	            byUserName.setBvn(reqJson.optString("bvn", byUserName.getBvn()));
	            byUserName.setNin(reqJson.optString("nin", byUserName.getNin()));
	            byUserName.setBvnVerified(reqJson.optBoolean("bvnVerified", byUserName.isBvnVerified()));
	            byUserName.setNinVerified(reqJson.optBoolean("ninVerified", byUserName.isNinVerified()));
	            moneyXBusinessRepo.save(byUserName);
	            
				
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

	public ResponseData UpdateCustomerContact(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);
			 String jsonStringHeader = ConvertRequestUtils.getJsonString(requestBody.getJheader());

		        JSONObject jsonHeader = new JSONObject(jsonStringHeader);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + callService);
			
			if (callService.getString("respCode").equals("00")) {
				
				
				String userId = jsonHeader.optString("userid");
	            MoneyXBusiness byUserName = moneyXBusinessRepo.findByUserName(userId);
				byUserName.setMobileNumber(reqJson.optString("mobileNumber", byUserName.getMobileNumber()));
				byUserName.setEmailAddress(reqJson.optString("emailAddress", byUserName.getEmailAddress()));
				moneyXBusinessRepo.save(byUserName);
				
	            
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				
				
				buildResponseData(response, callService);
				
				
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED +"  " + callService.getString("respmsg"));
			}
			
		}
		catch (Exception e) {
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

	
	public ResponseData getCustKycStatus(RequestData requestBody) {
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


	public ResponseData CreateCorporateCustomer(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			logger.info("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject jsonObject = new JSONObject(jsonString);
			logger.info("Request Body: " + jsonObject.toString());

			CorporateCustomer customerLogin = new CorporateCustomer();

			

//	      
			customerLogin
					.setOrganizationId(jsonObject.has("organizationId") ? jsonObject.getString("organizationId") : "");
			customerLogin
					.setCustomerTypeId(jsonObject.has("customerTypeId") ? jsonObject.getString("customerTypeId") : "");
			customerLogin.setCustomerType(jsonObject.has("customerType") ? jsonObject.getString("customerType") : "");
			customerLogin.setCity(jsonObject.has("city") ? jsonObject.getString("city") : "");
			customerLogin.setRcNumber(jsonObject.has("rcNumber") ? jsonObject.getString("rcNumber") : "");
			customerLogin.setFullBusinessName(
					jsonObject.has("fullBusinessName") ? jsonObject.getString("fullBusinessName") : "");
			customerLogin.setWalletPreferredName(
					jsonObject.has("walletPreferredName") ? jsonObject.getString("walletPreferredName") : "");
			customerLogin.setTin(jsonObject.has("tin") ? jsonObject.getString("tin") : "");
			customerLogin.setBusinessAddress(
					jsonObject.has("businessAddress") ? jsonObject.getString("businessAddress") : "");
			customerLogin.setCountryId(jsonObject.has("countryId") ? jsonObject.getString("countryId") : "");
			customerLogin.setEmail(jsonObject.has("emailAddress") ? jsonObject.getString("emailAddress") : jsonObject.has("email") ? jsonObject.getString("email"):"" );
			customerLogin.setUserName(jsonObject.has("userName") ? jsonObject.getString("userName") : "");
			customerLogin.setPassword(
					jsonObject.has("password") ? CommonUtils.b64_sha256(jsonObject.getString("password")) : "");
			customerLogin.setTxnPin(
					jsonObject.has("txnPin") ? CommonUtils.b64_sha256(jsonObject.getString("txnPin")) : "");
			customerLogin.setMobileNumber(jsonObject.has("mobileNumber") ? jsonObject.getString("mobileNumber") : "");
			customerLogin.setCountry(jsonObject.has("cuntry") ? jsonObject.getString("cuntry") : "");
			
			
			
			

			
			ResponseData validateOtp = validateOtp(request);	
			if (!validateOtp.getResponseCode().equals(CoreConstant.SUCCESS_CODE)) {
				return validateOtp; // Return if OTP validation fails
			}else {
			
			customerLogin.setIsActive("A");
			customerLogin.setIsLocked("N");
			customerLogin.setIsLoginAttemptActive("Y");
			customerLogin.setRetryLoginAttempt(0);
			
			String email=jsonObject.has("emailAddress") ? jsonObject.getString("emailAddress") : jsonObject.has("email") ? jsonObject.getString("email"):"" ;

			CorporateCustomer customer = corporateCustomerRepo.findByEmailOrUserName(email,jsonObject.getString("userName"));
			if (customer == null) {
				
				String requestJson = ConvertRequestUtils.getJsonString(request);

				JSONObject reqJson = new JSONObject(requestJson);
				
				

				
				EmbedlyServiceCaller service = new EmbedlyServiceCaller();
				 JSONObject callService = service.callService(reqJson);
				
				if (callService.getString("respCode").equals("00")) {
					
					
					JSONObject responseData = callService.getJSONObject("data");
					
					customerLogin.setCustomerId(responseData.getString("id"));
					
					customerLogin.setCustomerTierId(responseData.has("customerTierId") ? responseData.getInt("customerTierId")+"" :"");
					customer = corporateCustomerRepo.save(customerLogin);
					if (customer == null) {
						response.setResponseCode(CoreConstant.FAILURE_CODE);
						response.setResponseMessage(CoreConstant.FAILED + " to Create Profile");

					} else {
						response.setResponseCode(CoreConstant.SUCCESS_CODE);
						response.setResponseMessage(CoreConstant.SUCCESS + " Profile Created Successfully");
					}
					buildResponseData(response, callService);
				} else {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.FAILED +" " + callService.getString("respmsg"));
				}
				
			
				
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(
						CoreConstant.RECORD_ALREADY_EXISTS+":" + jsonObject.getString("emailAddress"));
			}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData GetACorporateCustomer(RequestData requestBody) {
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
	public ResponseData updateCorpCustomer(RequestData requestBody) {
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

	

	public ResponseData addDirector(RequestData requestBody) {
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

	public ResponseData getCorpDirector(RequestData requestBody) {
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

	public ResponseData getAllCorpDirectors(RequestData requestBody) {
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

	public ResponseData updateCorpDirector(RequestData requestBody) {
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

	public ResponseData uploadCorpDocuments(RequestData requestBody) {
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

	public ResponseData updateCorpDocuments(RequestData requestBody) {
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
	
	public ResponseData GetAllCustomerTypes(RequestData requestBody) {
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

	public ResponseData getCorpDocuments(RequestData requestBody) {
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

	public ResponseData createWallet(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			JSONObject requestJson = reqJson.getJSONObject("jbody");
			
			String customerType =	 requestJson.has("customerType") ? requestJson.getString("customerType") : "INDIVIDUAL";
			
			if (customerType.toUpperCase().equals("CORPORATE")) {
				return createCorpWallet(requestBody);
			}else {

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + callService);
			
			if (callService.getString("respCode").equals("00")) {
				
				MoneyXBusiness byCustomerId = moneyXBusinessRepo.findByCustomerId(reqJson.getJSONObject("jbody").getString("customerId"));
				JSONObject data=callService.getJSONObject("data");

				byCustomerId.setAccountNumber(data.getJSONObject("virtualAccount").getString("accountNumber"));
				byCustomerId.setAccountType("WALLET");
				byCustomerId.setCurrency(data.getString("currencyId"));
				
				moneyXBusinessRepo.save(byCustomerId);
				 WalletAcctData wallet=new WalletAcctData() ;
				 	wallet.setAcctNo(data.getJSONObject("virtualAccount").getString("accountNumber"));
					wallet.setBalance( new BigDecimal( data.optDoubleObject("availableBalance")));
					wallet.setLedgerBal(new BigDecimal( data.optDoubleObject("ledgerBalance")));
					wallet.setCurrencyId(data.getString("currencyId"));
					wallet.setCustId(reqJson.getJSONObject("jbody").getString("customerId"));
					wallet.setCustomerTypeId(data.getString("customerTypeId"));
					wallet.setAcctName(data.getString("name"));
					wallet.setWalletClassificationId("WALLET");
					wallet.setWalletRestrictionId(data.optString("walletRestrictionId"));
					wallet.setBankCode(data.getJSONObject("virtualAccount").getString("bankCode"));
					wallet.setBankName(data.getJSONObject("virtualAccount").getString("bankName"));
					wallet.setWalletId(data.getString("id"));
					wallet.setCreatedAt(new Date());
					
					WalletAcctData save = walletAcctDataRepository.save(wallet);
					if (save == null) {
						response.setResponseCode(CoreConstant.FAILURE_CODE);
						response.setResponseMessage(CoreConstant.FAILED + " to create wallet");
						return response;
					}
				 
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				buildResponseData(response, callService);
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(CoreConstant.FAILED +"  " + callService.getString("respmsg"));
			}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData createCorpWallet(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody);

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			JSONObject requestJson = reqJson.getJSONObject("jbody");
			JSONObject jheader = reqJson.getJSONObject("jheader");
			jheader.put("requestType", "CREATE_CORP_CUST_WALLET");
			requestJson.put("jheader", jheader);
			String customerType =	 requestJson.has("customerType") ? requestJson.getString("customerType") : "INDIVIDUAL";
			
		
			
			
			
			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + callService);
			
			if (callService.getString("respCode").equals("00")) {
				
				CorporateCustomer byCustomerId = corporateCustomerRepo.findByCustomerId(reqJson.getJSONObject("jbody").getString("customerId"));
				JSONObject data=callService.getJSONObject("data");

				byCustomerId.setAccountNumber(data.getJSONObject("virtualAccount").getString("accountNumber"));
				byCustomerId.setAccountType("WALLET");
				byCustomerId.setCurrency(data.getString("currencyId"));
				byCustomerId.setCurrency(reqJson.getJSONObject("jbody").getString("name"));
				
				corporateCustomerRepo.save(byCustomerId);
				 WalletAcctData wallet=new WalletAcctData() ;
				 	wallet.setAcctNo(data.getJSONObject("virtualAccount").getString("accountNumber"));
				   double balance=	data.has("availableBalance")?  data.optDoubleObject("availableBalance"): 0.0;
					wallet.setBalance(new BigDecimal(balance));
					double ledgerBalance= data.has("ledgerBalance")? data.optDoubleObject("ledgerBalance"): 0.0;
					wallet.setLedgerBal(new BigDecimal(ledgerBalance));
					wallet.setCurrencyId(data.getString("currencyId"));
					wallet.setCustId(reqJson.getJSONObject("jbody").getString("customerId"));
					wallet.setCustomerTypeId(data.getString("customerTypeId"));
					wallet.setAcctName(data.getString("name"));
					wallet.setWalletClassificationId("WALLET");
//					wallet.setWalletRestrictionId(data.optString("walletRestrictionId"));
					wallet.setBankCode(data.getJSONObject("virtualAccount").getString("bankCode"));
					wallet.setBankName(data.getJSONObject("virtualAccount").getString("bankName"));
					wallet.setWalletId(data.getString("id"));
					wallet.setCreatedAt(new Date());
					
					WalletAcctData save = walletAcctDataRepository.save(wallet);
					if (save == null) {
						response.setResponseCode(CoreConstant.FAILURE_CODE);
						response.setResponseMessage(CoreConstant.FAILED + " to create wallet");
						return response;
					}
				 
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

	public ResponseData getWalletById(RequestData requestBody) {
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

	public ResponseData getWalletByAccNumber(RequestData requestBody) {
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
	
	public ResponseData walletHistory(RequestData requestBody) {
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

	
	


	public ResponseData closeWallet(RequestData requestBody) {
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

	public ResponseData restrictByAccId(RequestData requestBody) {
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

	public ResponseData restrictWallet(RequestData requestBody) {
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
		return 	response;
	}
	

	public ResponseData retrieveCurrencies(RequestData requestBody) {
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
	public ResponseData getWltResTypes(RequestData requestBody) {
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
	public ResponseData accountClosure(RequestData requestBody) {
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
	

	public ResponseData addWalletGroup(RequestData requestBody) {
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

	public ResponseData addWalletToGroup(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData addWalletGroupFeature(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getWalletGroupFeature(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getWalletGroupById(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getAllWalletGroups(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData createProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getAllProducts(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData activateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData deactivateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getProductLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData setCustomerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData setDefaultTxnLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getCustomerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updDefTxnLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData addProductLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData customerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getOrgPrefixMappings(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData createCheckoutWallet(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getChckkoWallets(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getChckkoWltTrans(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData issueAfrigoCard(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData activateAfrigoCard(RequestData requestBody) {
	ResponseData response = new ResponseData();
	try {
		System.out.println("Request : " + requestBody);
		String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updateAfrigoCardInfo(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData mapPhysAfrigoCard(RequestData requestBody) {
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

	public ResponseData fetchCountryDialCode() {
		ResponseData response = new ResponseData();
		try {
			List<Country> countries = countryRepo.findAll();
			
			
			if (countries.isEmpty()) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage("No countries found");
			} else {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage(CoreConstant.SUCCESS);
				response.setResponseData(countries);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}

	public ResponseData getOrgId(RequestData requestBody) {
	ResponseData response = new ResponseData();
	try {
		
		List<String> orgIds =new ArrayList<>();
		orgIds.add(bundle.getString("ORG_ID"));
		response.setResponseCode(CoreConstant.SUCCESS_CODE);
		response.setResponseMessage(CoreConstant.SUCCESS);
		response.setResponseData(orgIds);
	}
	catch (Exception e) {
		e.printStackTrace();
		response.setResponseCode(CoreConstant.FAILURE_CODE);
		response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
	}
		return response;
	}

	public ResponseData getWalletId(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			WalletAcctData byCustomerId = walletAcctDataRepository.findByCustId(reqJson.getString("customerId"));
			if (byCustomerId == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage("No wallet found for customerId " + reqJson.getString("customerId"));
				return response;
			}
			JSONObject respJson = new JSONObject();
			respJson.put("walletId", byCustomerId.getWalletId());
			respJson.put("customerId", byCustomerId.getCustId());
			respJson.put("accountNumber", byCustomerId.getAcctNo());
			
			
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS);
			response.setResponseData(respJson.toMap());

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	public ResponseData validateTxnPin(String  txnpin, String  userName) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request For Pin Validate : " +userName);

			MoneyXBusiness byUserName = moneyXBusinessRepo.findByUserName(userName);
			if (byUserName == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage("No wallet found for userName " + userName);
				return response;
			}
			if (byUserName.getTxnPin().equals(txnpin)) {
				response.setResponseCode(CoreConstant.SUCCESS_CODE);
				response.setResponseMessage("Transaction Pin validated successfully");
				
			} else {

				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage("Invalid Transaction Pin");
				return response;
			}

			

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	// view wallet transaction history

	public ResponseData getTxnByReference(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody.getJbody());

			JSONObject reqJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + reqJson.toString());
			Transactions byPaymentReference = transactionsRepo.findByPaymentReference(reqJson.getString("transactionReference"));
			if (byPaymentReference == null) {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(
						"No transaction found for reference " + reqJson.getString("transactionReference"));
				return response;
			}
			JSONObject respJson = new JSONObject();
			respJson.put("transactionReference", byPaymentReference.getPaymentReference());
			respJson.put("amount", byPaymentReference.getAmount());
			respJson.put("status", byPaymentReference.getStatus());
			respJson.put("currency", byPaymentReference.getCurrency());
			respJson.put("txnDate", byPaymentReference.getTxnDate());
			respJson.put("acctNo", byPaymentReference.getAcctNo());
			respJson.put("txnType", byPaymentReference.getTxnType());
			respJson.put("responseCode", byPaymentReference.getResponseCode());
			respJson.put("responseMessage", byPaymentReference.getResponseMessage());
			respJson.put("createdDate", byPaymentReference.getCreatedDate());
			respJson.put("beneficiaryaccount", byPaymentReference.getBeneficiaryaccount());
			respJson.put("beneficiaryname", byPaymentReference.getBeneficiaryname());
			respJson.put("beneficiarybank", byPaymentReference.getBeneficiarybank());
			respJson.put("remarks", byPaymentReference.getRemarks());
			respJson.put("channel", byPaymentReference.getChannel());
			respJson.put("drnarration",byPaymentReference.getDrnarration());
			
			response.setResponseCode(CoreConstant.SUCCESS_CODE);
			response.setResponseMessage(CoreConstant.SUCCESS);
			response.setResponseData(respJson.toMap());

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
	}
	
	
}
