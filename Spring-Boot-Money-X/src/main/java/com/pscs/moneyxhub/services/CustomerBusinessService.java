package com.pscs.moneyxhub.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.embedly.caller.EmbedlyServiceCaller;
import com.pscs.moneyxhub.entity.Country;
import com.pscs.moneyxhub.entity.CustomerDocInfo;
import com.pscs.moneyxhub.entity.CustomerLogin;
import com.pscs.moneyxhub.entity.MoneyXBusiness;
import com.pscs.moneyxhub.entity.OtpDataTabl;
import com.pscs.moneyxhub.entity.WalletAcctData;
import com.pscs.moneyxhub.helper.ConvertRequestUtils;
import com.pscs.moneyxhub.helper.CoreConstant;
import com.pscs.moneyxhub.model.ImageUpload;
import com.pscs.moneyxhub.model.RequestData;
import com.pscs.moneyxhub.model.ResponseData;
import com.pscs.moneyxhub.repo.BusinessRoleRepo;
import com.pscs.moneyxhub.repo.BusinessTypeRepo;
import com.pscs.moneyxhub.repo.CountryRepo;
import com.pscs.moneyxhub.repo.CustomerDocInfoRepo;
import com.pscs.moneyxhub.repo.CustomerLoginRepo;
import com.pscs.moneyxhub.repo.DocumentRepo;
import com.pscs.moneyxhub.repo.MoneyXBusinessRepo;
import com.pscs.moneyxhub.repo.OtpDataTablRepo;
import com.pscs.moneyxhub.repo.WalletAcctDataRepository;
import com.pscs.moneyxhub.services.post.EmailAndSMSPostingService;
import com.pscs.moneyxhub.utils.CommonUtils;

import jakarta.transaction.Transactional;

@Service
public class CustomerBusinessService {
	private static final Logger logger = Logger.getLogger(CustomerBusinessService.class);
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

	public CustomerBusinessService(CustomerLoginRepo customerLoginRepo, CountryRepo countryRepo,
			BusinessTypeRepo businessTypeRepo, BusinessRoleRepo businessRoleRepo,
			CustomerDocInfoRepo customerDocInfoRepo, DocumentRepo documentRepo, OtpDataTablRepo otpDataTablRepo,
			EmailAndSMSPostingService smsPostingService, MoneyXBusinessRepo moneyXBusinessRepo,
			WalletAcctDataRepository walletAcctDataRepository) {
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
			
			MoneyXBusiness checkCustomerres = moneyXBusinessRepo.findByUserName(requestJson.getString("username"));
			if (checkCustomerres != null) {
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
					response.setResponseData(checkCustomerres);
					if (isLoginAttemptActive.equals("Y")) {
						moneyXBusinessRepo.resetRetryLoginAttempt(checkCustomerres.getUserName());
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
			customerLogin.setCustomerType(jsonObject.getString("customerType"));
			customerLogin.setCustomerId(jsonObject.getString("customerId"));

			customerLogin.setIsActive("A");
			customerLogin.setIsLocked("N");
			customerLogin.setIsLoginAttemptActive("Y");
			customerLogin.setRetryLoginAttempt(0);
			

			MoneyXBusiness customer = moneyXBusinessRepo.findByEmailAddressOrUserName(jsonObject.getString("emailAddress"),jsonObject.getString("userName"));
			if (customer == null) {
				customer = moneyXBusinessRepo.save(customerLogin);
				if (customer == null) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.FAILED + " to Create Profile");

				} else {
					response.setResponseCode(CoreConstant.SUCCESS_CODE);
					response.setResponseMessage(CoreConstant.SUCCESS + " Profile Created Successfully");
					response.setResponseData(customer);
				}
			} else {
				response.setResponseCode(CoreConstant.FAILURE_CODE);
				response.setResponseMessage(
						CoreConstant.RECORD_ALREADY_EXISTS+":" + jsonObject.getString("accountNumber"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public ResponseData generateOtp(RequestData request) {
		ResponseData response = new ResponseData();
		try {

			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			String strJheader = ConvertRequestUtils.getJsonString(request.getJheader());
			JSONObject jHeader = new JSONObject(strJheader);

			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println("Request Body: " + jsonObject.toString());


			otpDataTablRepo.updateOtpStatusByUserId(jHeader.getString("userid"), "E");
			
			String otp = CommonUtils.createRandomNumber(6);
			String encryptedOtp = CommonUtils.b64_sha256(otp);
			// generate otp by using random
			OtpDataTabl otpDataTabl = new OtpDataTabl();
			otpDataTabl.setOtp(encryptedOtp);
			otpDataTabl.setUserId(jHeader.getString("userid"));
			otpDataTabl.setTransType(jHeader.getString("requestType"));
			otpDataTabl.setMobileNo(jsonObject.getString("mobileNumber"));
			otpDataTabl.setEmailId(jsonObject.getString("email"));
			
			
			
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
				if (jsonObject.getString("mobileNumber").startsWith("234")) {

					smsRequest.put("messages", ConvertRequestUtils.generateSMSJson(jsonObject.getString("mobileNumber"),
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

			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject requestJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + requestJson.toString());

			String otp = requestJson.getString("authValue");
			String username = requestJson.getString("username");
			String mobileNumber = requestJson.getString("mobileNumber");

			OtpDataTabl otpDataTabl = otpDataTablRepo.findByUserIdAndOtpAndOtpStatus(username, CommonUtils.b64_sha256(otp),"A");

			// Check if the OTP is older than 2 minutes

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
				MoneyXBusiness customerLogin = moneyXBusinessRepo.findByUserNameAndPassword(username, oldPassword);

				if (customerLogin == null) {
					response.setResponseCode(CoreConstant.FAILURE_CODE);
					response.setResponseMessage(CoreConstant.INVALID_CREDENTIALS);

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

	public ResponseData createCustomer(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData getAllCustomerDetails(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData UpdateCustomerContact(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(CoreConstant.FAILURE_CODE);
			response.setResponseMessage(CoreConstant.FAILED + e.getMessage());
		}
		return response;
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
		return null;
	}


	public ResponseData CreateCorporateCustomer(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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
	public ResponseData updateCorpCustomer(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData getCorpDirector(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData updateCorpDirector(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData updateCorpDocuments(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData getCorpDocuments(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

			EmbedlyServiceCaller service = new EmbedlyServiceCaller();
			 JSONObject callService = service.callService(reqJson);
			System.out.println("Response " + response.toString());
			
			if (callService.getString("respCode").equals("00")) {
				
				MoneyXBusiness byCustomerId = moneyXBusinessRepo.findByCustomerId(reqJson.getJSONObject("jbody").getString("customerId"));
				JSONObject data=callService.getJSONObject("data");

				byCustomerId.setAccountNumber(data.getJSONObject("virtualAccount").getString("accountNumber"));
				byCustomerId.setAccountType("WALLET");
				byCustomerId.setCurrency(data.getString("currencyId"));
				
				moneyXBusinessRepo.save(byCustomerId);
				 WalletAcctData wallet=new WalletAcctData() ;
				 	wallet.setAccountNumber(data.getJSONObject("virtualAccount").getString("accountNumber"));
					wallet.setAvailableBalance(data.optDoubleObject("availableBalance"));
					wallet.setLedgerBalance(data.optDoubleObject("ledgerBalance"));
					wallet.setCurrencyId(data.getString("currencyId"));
					wallet.setCustomerId(reqJson.getJSONObject("jbody").getString("customerId"));
					wallet.setCustomerTypeId(data.getString("customerTypeId"));
					wallet.setIsDefault(data.getBoolean("isDefault"));
					wallet.setIsInternal(data.getBoolean("isInternal"));
					wallet.setMobNum(data.optString("mobNum"));
					wallet.setName(data.getString("name"));
					wallet.setOverdraft(data.optDoubleObject("overdraft"));
					wallet.setWalletClassificationId("WALLET");
					wallet.setWalletRestrictionId(data.optString("walletRestrictionId"));
					wallet.setBankCode(data.getJSONObject("virtualAccount").getString("bankCode"));
					wallet.setBankName(data.getJSONObject("virtualAccount").getString("bankName"));

					WalletAcctData save = walletAcctDataRepository.save(wallet);
					if (save == null) {
						response.setResponseCode(CoreConstant.FAILURE_CODE);
						response.setResponseMessage(CoreConstant.FAILED + " to create wallet");
						return response;
					}
				 
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

	public ResponseData createCustWallet(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData getWalletByAccNumber(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	
	public ResponseData walletHistory(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData restrictByAccId(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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
	public ResponseData getWltResTypes(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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
	

	public ResponseData addWalletGroup(RequestData requestBody) {
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
				response.setResponseMessage(CoreConstant.FAILED + callService.getString("respMessage"));
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

	public ResponseData addWalletGroupFeature(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getWalletGroupFeature(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getWalletGroupById(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getAllWalletGroups(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData createProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getAllProducts(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData activateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData deactivateProduct(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getProductLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData setCustomerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData setDefaultTxnLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getCustomerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updDefTxnLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData addProductLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData customerLimit(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getOrgPrefixMappings(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData createCheckoutWallet(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getChckkoWallets(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData getChckkoWltTrans(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData issueAfrigoCard(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData activateAfrigoCard(RequestData requestBody) {
	ResponseData response = new ResponseData();
	try {
		System.out.println("Request : " + requestBody);
		String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData updateAfrigoCardInfo(RequestData requestBody) {
		ResponseData response = new ResponseData();
		try {
			System.out.println("Request : " + requestBody);
			String jsonString = ConvertRequestUtils.getJsonString(requestBody );

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

	public ResponseData mapPhysAfrigoCard(RequestData requestBody) {
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
