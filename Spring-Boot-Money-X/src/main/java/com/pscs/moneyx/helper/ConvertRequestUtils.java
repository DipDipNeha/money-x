/**
 * 
 */
package com.pscs.moneyx.helper;

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
}
