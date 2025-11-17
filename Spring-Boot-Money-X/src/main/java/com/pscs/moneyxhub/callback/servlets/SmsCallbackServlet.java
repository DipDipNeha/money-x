package com.pscs.moneyxhub.callback.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sms/callback")
public class SmsCallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuilder sb = new StringBuilder();
		String line;

		try (BufferedReader reader = request.getReader()) {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}

		String requestBody = sb.toString();
		System.out.println("Received callback: " + requestBody);

		try {
			JSONObject json = new JSONObject(requestBody);

			String messageId = json.optString("message_id");
			String status = json.optString("status");
			String statusCode = json.optString("status_code");
			String doneDate = json.optString("done_date");
			String operator = json.optString("operator");
			String length = json.optString("length");
			String page = json.optString("page");
			String cost = json.optString("cost");

			// Log all values
			System.out.println("---- Parsed Callback Data ----");
			System.out.println("Message ID : " + messageId);
			System.out.println("Status     : " + status);
			System.out.println("StatusCode : " + statusCode);
			System.out.println("Done Date  : " + doneDate);
			System.out.println("Operator   : " + operator);
			System.out.println("Length     : " + length);
			System.out.println("Page       : " + page);
			System.out.println("Cost       : " + cost);
			System.out.println("--------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"Invalid JSON payload\"}");
			return;

		}

		// Respond with 200 OK and a simple message
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print("{\"status\":\"ok\"}");
		out.flush();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.getWriter().write("{\"message\":\"PSCS WebHook callback endpoint active\"}");
	}
}