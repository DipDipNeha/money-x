package com.pscs.moneyx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
	private String responseCode;
	private String responseMessage;
	private Object responseData;

}
