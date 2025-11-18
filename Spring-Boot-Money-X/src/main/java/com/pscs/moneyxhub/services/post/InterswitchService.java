package com.pscs.moneyxhub.services.post;

import java.util.ResourceBundle;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InterswitchService {


	private ResourceBundle bundle = ResourceBundle.getBundle("interswitchng");
	

	public JSONObject getTransaction(String merchantCode, String reference, String amount) {
		JSONObject responseJson = new JSONObject();
		try {

			String url = bundle.getString("QA_BASE_URL")+ "?merchantcode=" + merchantCode
					+ "&transactionreference=" + reference + "&amount=" + amount;

			 RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

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
}
