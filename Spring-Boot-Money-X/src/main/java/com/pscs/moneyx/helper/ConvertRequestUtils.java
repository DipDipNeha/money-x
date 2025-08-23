/**
 * 
 */
package com.pscs.moneyx.helper;

import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
public class ConvertRequestUtils {
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
}
