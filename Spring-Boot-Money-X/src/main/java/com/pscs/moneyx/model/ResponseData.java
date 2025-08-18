package com.pscs.moneyx.model;

public class ResponseData {
	private String responseCode;
	private String responseMessage;
	private Object responseData;
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
	public Object getResponseData() {
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	@Override
	public String toString() {
		return "ResponseData [responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", responseData="
				+ responseData + "]";
	}

	
}
