/**
 * 
 */
package com.pscs.moneyx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestData {
	private String responseCode;
	private String responseMessage;
	private Object jbody;
	private Object jheader;
	
}
