/**
 * 
 */
package com.pscs.moneyxhub.helper;

import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
public class ConvertRequestUtils {
	private static ResourceBundle bundle = ResourceBundle.getBundle("sms");

	public static <T> T convertValue(Object source, Class<T> targetType) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(source, targetType);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Failed to convert object to type: " + targetType.getName(), e);
		}
	}

	public static String getJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject convertRequestDataToJson(Object requestData) {
		try {
			// Convert RequestData to Map using ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> requestDataMap = objectMapper.convertValue(requestData, Map.class);

			// Convert Map to JSONObject
			return new JSONObject(requestDataMap);
		} catch (Exception e) {
			System.err.println("Error converting RequestData to JSONObject: " + e.getMessage());
			e.printStackTrace();
			return new JSONObject(); // Return an empty JSONObject in case of error
		}
	}

	//
	public static JSONArray generateSMSJson(String mobileNumber, String smsType,String otp) {
		JSONArray messages = new JSONArray();
		try {
			
			String text = "Dear customer this is one time password MoneyXpay service "+otp;
			if (smsType == null || smsType.isEmpty()) {
				text = "TEXT";
			} else {
				text = "Dear customer this is one time password MoneyXpay service "+otp;
			}
			
			messages.put(new JSONObject().put("sender", bundle.getString("sms.sender"))
					.put("destinations", new JSONArray().put(new JSONObject().put("to", mobileNumber)))
					.put("content", new JSONObject().put("text", text)));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONArray();
		}
		return messages;
	}

	public static JSONArray generateEmailJson(String email, String subject, String body) {
		JSONArray messages = new JSONArray();
		try {

			String emailSubject = (subject == null || subject.isEmpty()) ? bundle.getString("email.subject") : subject;
			String emailBody = (body == null || body.isEmpty()) ? "MoneyXPay Test" : body;

			messages.put(new JSONObject().put("sender", bundle.getString("email.sender"))
					.put("destinations",
							new JSONArray().put(new JSONObject().put("to",
									new JSONArray().put(new JSONObject().put("destination", email)))))
					.put("content", new JSONObject().put("subject", emailSubject).put("text", emailBody)));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONArray();
		}
		return messages;
	}
	
}
