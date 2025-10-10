package com.pscs.moneyxhub.services.post;

import java.util.ResourceBundle;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailAndSMSPostingService {

	private ResourceBundle bundle = ResourceBundle.getBundle("sms");

	public JSONObject sendPostRequest(String jsonBody,String urlType) {
		JSONObject responseJson = new JSONObject();
		try {
			String apiKey = bundle.getString("sms.apiKey");
			String url = bundle.getString(urlType);

			System.out.println("SMS URL" + url + "\n Api Key" + apiKey + "\n Request " + jsonBody);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "App " + apiKey);

			HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

			ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//	        response status code 
			HttpStatusCode statusCode = response.getStatusCode();
			System.out.println(statusCode);
			int value = statusCode.value();
			if (value == 200) {
				System.out.println(response.getBody());
				responseJson.put("respsode", value + "");
				responseJson.put("respmsg", "SUCCESS");
				responseJson.put("data", response.getBody());

			} else {
				responseJson.put("respsode", value + "");
				responseJson.put("respmsg", "Failed");
				responseJson.put("data", response.getBody());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseJson;

	}

	public String sendPostRequest(String url, String apiKey, Object requestBody) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "App " + apiKey);

		HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		return response.getBody(); // Return the response body as a String
	}

	public static void main(String[] args) {
		System.out.println("Hello, World!");
		String request = "{\"messages\": [{\"sender\": \"InfoSMS\", \"destinations\": [{\"to\": \"916301655736\"}],\"content\": {\"text\": \"This is a sample message\"}}]}";

		// Example usage
		JSONObject sendPostRequest = new EmailAndSMSPostingService().sendPostRequest(request,"sms.url");

		System.out.println(sendPostRequest);

	}

}
