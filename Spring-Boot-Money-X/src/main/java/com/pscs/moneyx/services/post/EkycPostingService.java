package com.pscs.moneyx.services.post;

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
public class EkycPostingService {

	private ResourceBundle bundle = ResourceBundle.getBundle("ekyc");
	
	public JSONObject sendPostRequest(String jsonBody) {
        JSONObject responseJson = new JSONObject();
        try {
            String apiKey = bundle.getString("ekyc.apiKey");
            String url = bundle.getString("ekyc.url");

            System.out.println("EKYC URL" + url + "\n Api Key" + apiKey + "\n Request " + jsonBody);
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
	//GET Method Request 
	public JSONObject sendGetRequest(String jsonBody,String ekycType,String ekycNumber) {
		 JSONObject responseJson = new JSONObject();
		 try {
			 String apiKey = bundle.getString("ekyc.apiKey");
	            String url = bundle.getString("ekyc.url").concat("?bvn=").concat(ekycNumber);

	            System.out.println("EKYC URL" + url + "\n Api Key" + apiKey + "\n Request " + jsonBody);
	            RestTemplate restTemplate = new RestTemplate();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            headers.set("Authorization", "App " + apiKey);
	            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
	            
	            
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

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
			 
		 }
		 catch (Exception e) {
			e.printStackTrace();
		}
		 
		 return responseJson;
		
	}
}
