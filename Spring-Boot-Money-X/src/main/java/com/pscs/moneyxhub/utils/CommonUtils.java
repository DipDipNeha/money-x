package com.pscs.moneyxhub.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class CommonUtils {
	
	public final static Long OTP_VALIDITY_DURATION = 300000L; // 5 minutes in milliseconds

	public final static String createRandomNumber(long len) {

		if (len > 18)
			throw new IllegalStateException("To many digits");
		long tLen = (long) Math.pow(10, len - 1) * 9;

		long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

		String tVal = number + "";

		if (tVal.length() != len) {
			throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
		}

		return tVal;

	}

	public static String getMobileNo(String mobno) {
		if (mobno == null) {
			return "";
		}
		mobno = mobno.trim();
		int len = mobno.length();

		switch (len) {
		case 10: {
			mobno = CommonConstants.COUNTRY_MOBILE_CODE + mobno;
		}
			break;
		case 11: {
			mobno = CommonConstants.COUNTRY_MOBILE_CODE + mobno.substring(1);
		}
			break;
		case 14: {
			mobno = CommonConstants.COUNTRY_MOBILE_CODE + mobno.substring(4);
		}
			break;
		case 13: {
			mobno = CommonConstants.COUNTRY_MOBILE_CODE + mobno.substring(3);
		}
			break;

		default:
			break;
		}

		return mobno;
	}

	public static String getSHA256(String data) {

		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	public static String b64_sha256(String data) {
		String outputString = "";
		if (data != null) {
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-256");
				md.update(data.getBytes());
				byte byteData[] = md.digest();
				outputString = Base64.getEncoder().encodeToString(byteData);
				outputString = outputString.substring(0, outputString.length() - 2);
				System.out.println("b64_sha256 outputString::" + outputString);
			} catch (NoSuchAlgorithmException e) {

				e.printStackTrace();
			}
		} else {
			System.out.println("Input String Missing for b64_sha256");
		}
		return outputString;

	}

	public static void main(String args[]) {

		System.out.println(b64_sha256("221231"));
		System.out.println(getSHA256("12345"));

		System.out.println(getMobileNo("2349177796541"));
		;
	}
	
	
	public static  Date parseDateFromStr(String input) {
		
        LocalDate localDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Convert LocalDate to java.util.Date (if needed)
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println("LocalDate: " + localDate);
        System.out.println("java.util.Date: " + date);
        
        return date;
        
	}

}
