package com.pscs.moneyx.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.pscs.moneyx.entity.Country;
import com.pscs.moneyx.entity.CustomerDocInfo;
import com.pscs.moneyx.entity.CustomerLogin;
import com.pscs.moneyx.entity.MoneyXBusiness;
import com.pscs.moneyx.entity.OtpDataTabl;
import com.pscs.moneyx.helper.ConvertRequestUtils;
import com.pscs.moneyx.model.ImageUpload;
import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.repo.BusinessRoleRepo;
import com.pscs.moneyx.repo.BusinessTypeRepo;
import com.pscs.moneyx.repo.CountryRepo;
import com.pscs.moneyx.repo.CustomerDocInfoRepo;
import com.pscs.moneyx.repo.CustomerLoginRepo;
import com.pscs.moneyx.repo.DocumentRepo;
import com.pscs.moneyx.repo.MoneyXBusinessRepo;
import com.pscs.moneyx.repo.OtpDataTablRepo;
import com.pscs.moneyx.services.post.SMSPostingService;
import com.pscs.moneyx.utils.CommonUtils;

@Service
public class CustomerBusinessService {
	private final CustomerLoginRepo customerLoginRepo;
	private final CountryRepo countryRepo;
	private final BusinessTypeRepo businessTypeRepo;
	private final BusinessRoleRepo businessRoleRepo;
	private final CustomerDocInfoRepo customerDocInfoRepo;
	private final DocumentRepo documentRepo;
	private final OtpDataTablRepo otpDataTablRepo;
	private final SMSPostingService smsPostingService;
	private final MoneyXBusinessRepo moneyXBusinessRepo;

	public CustomerBusinessService(CustomerLoginRepo customerLoginRepo, CountryRepo countryRepo,
			BusinessTypeRepo businessTypeRepo, BusinessRoleRepo businessRoleRepo,
			CustomerDocInfoRepo customerDocInfoRepo, DocumentRepo documentRepo, OtpDataTablRepo otpDataTablRepo,
			SMSPostingService smsPostingService, MoneyXBusinessRepo moneyXBusinessRepo) {
		this.customerLoginRepo = customerLoginRepo;
		this.countryRepo = countryRepo;
		this.businessTypeRepo = businessTypeRepo;
		this.businessRoleRepo = businessRoleRepo;
		this.customerDocInfoRepo = customerDocInfoRepo;
		this.documentRepo = documentRepo;
		this.otpDataTablRepo = otpDataTablRepo;
		this.smsPostingService = smsPostingService;
		this.moneyXBusinessRepo = moneyXBusinessRepo;
	}

	// find by username
	public ResponseData login(RequestData request) {
		ResponseData response = new ResponseData();
		try {

			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());
			// Convert the request body to a JSON object
			JSONObject requestJson = new JSONObject(jsonString);
			System.out.println("Request Body: " + requestJson.toString());

			 MoneyXBusiness checkCustomerres = moneyXBusinessRepo.findByUserName(requestJson.getString("username"))
					.orElseThrow(() -> new ResourceNotFoundException(
							"No Data Found By this Username: " + requestJson.getString("username")));
			if (checkCustomerres != null) {
				checkCustomerres = moneyXBusinessRepo.findByUserNameAndPassword(requestJson.getString("username"),
						CommonUtils.b64_sha256( requestJson.getString("password")));

			}

			if (checkCustomerres == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Login Failed");

			} else {
				response.setResponseCode("00");
				response.setResponseMessage("Login Successful");
				checkCustomerres.setPassword(null);
				response.setResponseData(checkCustomerres);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	public ResponseData createProfile(RequestData request) {
		ResponseData response = new ResponseData();
		try {
			// Convert the request body to a JSON object
			System.out.println("Request : " + request);
			String jsonString = ConvertRequestUtils.getJsonString(request.getJbody());

			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println("Request Body: " + jsonObject.toString());

			MoneyXBusiness customerLogin = new MoneyXBusiness();

			customerLogin.setBusinessName(jsonObject.getString("businessName"));
			customerLogin.setBusinessType(jsonObject.getString("businessType"));
			customerLogin.setBusinessEmail(jsonObject.getString("businessEmail"));
			customerLogin.setBusinessPhone(jsonObject.getString("businessPhone"));
			customerLogin.setBusinessIndustry(jsonObject.getString("businessIndustry"));
			customerLogin.setBusinessId(jsonObject.getString("businessId"));
			customerLogin.setBusinessAddress(jsonObject.getString("businessAddress"));
			customerLogin.setBusinessCountry(jsonObject.getString("businessCountry"));
			customerLogin.setBusinessStatus(jsonObject.getString("businessStatus"));
			customerLogin.setBusinessRole(jsonObject.getString("businessRole"));
			customerLogin.setInvitedByEmail(jsonObject.getString("invitedByEmail"));
			customerLogin.setOwnerName(jsonObject.getString("ownerName"));
			customerLogin.setOwnerEmail(jsonObject.getString("ownerEmail"));
			customerLogin.setOwnerPhone(jsonObject.getString("ownerPhone"));
			customerLogin.setAccountNumber(jsonObject.getString("accountNumber"));
			customerLogin.setAccountType(jsonObject.getString("accountType"));
			customerLogin.setCurrency(jsonObject.getString("currency"));
			customerLogin.setUserName(jsonObject.getString("userName"));
			System.out.println(jsonObject.getString("password"));
			customerLogin.setPassword(CommonUtils.b64_sha256( jsonObject.getString("password")));
			customerLogin.setTxnPin(jsonObject.getString("txnPin"));
			customerLogin.setAuthType(jsonObject.getString("authType"));
			customerLogin.setAuthValue(jsonObject.getString("authValue"));
			customerLogin.setCustomerType(jsonObject.getString("customerType"));
			customerLogin.setCustomerId(jsonObject.getString("customerId"));
			customerLogin.setDocInfo(jsonObject.getJSONArray("docInfo").toString());
			customerLogin.setRegistrationDate(new Date());
						
			MoneyXBusiness customer =	moneyXBusinessRepo.findByAccountNumber(jsonObject.getString("accountNumber"));
			if(customer==null) {
			 customer = moneyXBusinessRepo.save(customerLogin);
			//validate accountNumber
			System.out.println(customer);
			 
			if (customer == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Profile Creation Failed");

			} else {
				response.setResponseCode("00");
				response.setResponseMessage("Profile Created Successfully");
				response.setResponseData(customer);
			}
			}else {
				response.setResponseCode("01");
				response.setResponseMessage("This Account Profile Already Exist On System +"+jsonObject.getString("accountNumber"));
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

			String otp = CommonUtils.createRandomNumber(6);
			String encryptedOtp = CommonUtils.b64_sha256(otp);
			// generate otp by using random
			OtpDataTabl otpDataTabl = new OtpDataTabl();
			otpDataTabl.setOtp(encryptedOtp);
			otpDataTabl.setUserId(jHeader.getString("userid"));
			otpDataTabl.setTransType(jHeader.getString("requestType"));
			otpDataTabl.setMobileNo(jsonObject.getString("mobileNumber"));
			otpDataTabl.setEmailId(jsonObject.getString("email"));
			otpDataTabl.setChannel(jHeader.getString("channel"));
			otpDataTabl.setOtpStatus("A");

			OtpDataTabl save = otpDataTablRepo.save(otpDataTabl);

			if (save == null) {
				response.setResponseCode("01");
				response.setResponseMessage("OTP Generation Failed");
				return response;
			} else {

				JSONObject smsRequest = new JSONObject();

				JSONArray messages = new JSONArray();
				messages.put(new JSONObject().put("sender", "InfoSMS")
						.put("destinations",
								new JSONArray().put(new JSONObject().put("to", jsonObject.getString("mobileNumber"))))
						.put("content", new JSONObject().put("text", "Your OTP is: " + otp)));

				smsRequest.put("messages", messages);

				response.setResponseMessage("OTP Generated Successfully " + otp);
				response.setResponseCode("00");
				JSONObject sendPostRequest = smsPostingService.sendPostRequest(smsRequest.toString());

				if (sendPostRequest.getString("respsode").equals("200")) {
					response.setResponseCode("00");
					response.setResponseMessage("SMS Sent Successfully "+otp);
					response.setResponseData(sendPostRequest.toMap());

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to Generate OTP");

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
			String mobileNumber=requestJson.getString("mobileNumber");

			OtpDataTabl otpDataTabl = otpDataTablRepo.findByUserIdAndOtp(username, CommonUtils.b64_sha256(otp));

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
					response.setResponseCode("01");
					response.setResponseMessage("OTP Expired or Invalid");
					return response;
				}
			}

			if (otpDataTabl == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Invalid OTP");
				return response;
			} else if (otpDataTabl.getOtpStatus().equals("A")) {
				response.setResponseCode("00");
				response.setResponseMessage("OTP Validated Successfully");
				// if success the update the oto status to S
				otpDataTabl.setOtpStatus("S");
				otpDataTablRepo.save(otpDataTabl);

			} else if (otpDataTabl.getOtpStatus().equals("S")) {
				response.setResponseCode("01");
				response.setResponseMessage("OTP Already Used");
			} else if (otpDataTabl.getOtpStatus().equals("E")) {
				response.setResponseCode("01");
				response.setResponseMessage("OTP Expired or Invalid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to Validate OTP");
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
			String mobileNumber=requestJson.getString("mobileNumber");
			
			
			String oldPassword = requestJson.getString("password");
			String newPassword = requestJson.getString("newPassword");
			// find customer login by username
			MoneyXBusiness customerLogin = moneyXBusinessRepo.findByUserNameAndPassword(username, oldPassword);
			
			if (customerLogin == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Invalid Username or Password");
				return response;
			} else {
				customerLogin.setPassword(newPassword);
				moneyXBusinessRepo.save(customerLogin);

				response.setResponseCode("00");
				response.setResponseMessage("Password Updated Successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to Update Password");
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

	public ResponseData fetchCountry() {
		ResponseData response = new ResponseData();
		try {
			List<Country> countries = new ArrayList<>();
			countryRepo.findAll().forEach(country -> {
				countries.add(country);
			});
			response.setResponseData(countries);
			response.setResponseCode("00");
			response.setResponseMessage("Countries fetched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to fetch countries");
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
			response.setResponseCode("00");
			response.setResponseMessage("Business types fetched successfully");

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to fetch business types");
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
			response.setResponseCode("00");
			response.setResponseMessage("Roles fetched successfully");
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to fetch roles");
		}

		return response;
	}

	public ResponseData uploadImage(ImageUpload imageUpload) {
		ResponseData response = new ResponseData();
		try {
			JSONObject resjson=new JSONObject();
			
			CustomerDocInfo mobCustomerDocInfo = new CustomerDocInfo();
			String docId= "PSCS"+System.nanoTime();
			mobCustomerDocInfo.setId(docId);
			mobCustomerDocInfo.setImageData(imageUpload.getFile().getBytes());
			mobCustomerDocInfo.setImageType(imageUpload.getFileType());
			mobCustomerDocInfo.setMakerId(imageUpload.getUserId());
			mobCustomerDocInfo.setMakerDttm(new Date());
			CustomerDocInfo saveresponse = customerDocInfoRepo.save(mobCustomerDocInfo);
			
			resjson.put("docUploadId", docId);
			
			if (saveresponse == null) {
				response.setResponseCode("01");
				response.setResponseMessage("Failed to upload image");
				return response;
			} else {

				response.setResponseCode("00");
				response.setResponseMessage("Image uploaded successfully");
				response.setResponseData(resjson.toMap());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to upload image: " + e.getMessage());
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
			response.setResponseCode("00");
			response.setResponseMessage("Document types fetched successfully");

		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("01");
			response.setResponseMessage("Failed to fetch document types: " + e.getMessage());
		}

		return response;
	}

}
