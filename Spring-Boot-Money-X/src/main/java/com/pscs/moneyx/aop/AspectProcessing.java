/**
 * 
 */
package com.pscs.moneyx.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.pscs.moneyx.model.RequestData;
import com.pscs.moneyx.model.ResponseData;
import com.pscs.moneyx.services.CustomerBusinessService;
import com.pscs.moneyx.services.RequestLogService;

/**
 * 
 */

@Component
@Aspect
public class AspectProcessing {
	private static final Logger logger = Logger.getLogger(AspectProcessing.class);

	@Autowired
	private RequestLogService requestLogService;

	@Pointcut("execution(* com.pscs.moneyx.controller.*.*(..))")
	public void executeAspects() {
	};

	@Before("executeAspects()")
	public void losbefore() {
		logger.error("Logging before advice method");
	}

	@After("executeAspects()")
	public void logsAfter() {
		logger.error("Logging after advice method");
	}

	@Around("executeAspects()")
	public Object around(ProceedingJoinPoint process) throws Throwable {
		System.err.println("Logging before around  advice method");

		Object[] args = process.getArgs();

		
		Object proceed = process.proceed();

		
		try {
	        if (args.length > 0) {
	            System.err.println("Argument type: " + args[0].getClass().getName());
	        } else {
	            System.err.println("No arguments passed to the method.");
	        }
	        System.err.println("Return type: " + (proceed != null ? proceed.getClass().getName() : "null"));

	        if (args.length > 0 && args[0] instanceof RequestData && proceed instanceof ResponseEntity) {
	            RequestData requestData = (RequestData) args[0];
	            ResponseEntity<?> responseEntity = (ResponseEntity<?>) proceed;

	            if (responseEntity.getBody() instanceof ResponseData) {
	                ResponseData responseData = (ResponseData) responseEntity.getBody();
	                System.err.println("Logging arguments: " + requestData.toString() + ", " + responseData.toString());
	                requestLogService.writeLog(requestData, responseData);
	            } else {
	                System.err.println("Response body is not of type ResponseData");
	            }
	        } else {
	            System.err.println("Invalid argument types for logging");
	        }
	    } catch (Exception e) {
	        System.err.println("Error logging arguments: " + e.getMessage());
	    }
		System.err.println("After around " + proceed.toString());
		return proceed;
	}
}
