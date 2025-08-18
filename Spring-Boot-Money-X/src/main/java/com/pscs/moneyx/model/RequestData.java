/**
 * 
 */
package com.pscs.moneyx.model;

/**
 * 
 */

public class RequestData {
	private String responseCode;
	private String responseMessage;
	private Object jbody;
	private Object jheader;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Object getJbody() {
		return jbody;
	}
	public void setJbody(Object jbody) {
		this.jbody = jbody;
	}
	public Object getJheader() {
		return jheader;
	}
	public void setJheader(Object jheader) {
		this.jheader = jheader;
	}
	@Override
	public String toString() {
		return "RequestData [responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", jbody=" + jbody
				+ ", jheader=" + jheader + "]";
	}
	
	
	
}
