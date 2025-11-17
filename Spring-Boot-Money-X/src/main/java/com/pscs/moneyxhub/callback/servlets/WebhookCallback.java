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

@WebServlet("/v1/webhook")
public class WebhookCallback extends HttpServlet {

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
	
			
			
			if(json.has("event") && "payout".equals(json.getString("event"))) {
                JSONObject data = json.getJSONObject("data");
                String sessionId = data.optString("sessionId", null);
                String debitAccountNumber = data.getString("debitAccountNumber");
                String creditAccountNumber = data.getString("creditAccountNumber");
                String debitAccountName = data.getString("debitAccountName");
                String creditAccountName = data.getString("creditAccountName");
                double amount = data.getDouble("amount");
                String currency = data.getString("currency");
                String status = data.getString("status");
                String paymentReference = data.getString("paymentReference");
                String dateOfTransaction = data.getString("dateOfTransaction");
                System.out.println("Payout event received.");
                
                
                // Here, you can process the payout information as needed
                System.out.println("Payout Details:");
                System.out.println("Debit Account: " + debitAccountNumber + " - " + debitAccountName);
                System.out.println("Credit Account: " + creditAccountNumber + " - " + creditAccountName);
                System.out.println("Amount: " + amount + " " + currency);
                System.out.println("Status: " + status);
                System.out.println("Payment Reference: " + paymentReference);
                System.out.println("Date of Transaction: " + dateOfTransaction);
            }
			else if( json.has("event")&& "nip".equals(json.getString("nip"))) {
                JSONObject data = json.getJSONObject("data");
                String accountNumber = data.getString("accountNumber");
                String reference = data.getString("reference");
                double amount = data.getDouble("amount");
                double fee = data.getDouble("fee");
                String senderName = data.getString("senderName");
                String senderBank = data.getString("senderBank");
                String dateOfTransaction = data.getString("dateOfTransaction");
                String description = data.getString("description");
                System.out.println("NIP event received.");
                
                
                // Here, you can process the NIP information as needed
                System.out.println("NIP Details:");
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Reference: " + reference);
                System.out.println("Amount: " + amount);
                System.out.println("Fee: " + fee);
                System.out.println("Sender Name: " + senderName);
                System.out.println("Sender Bank: " + senderBank);
                System.out.println("Date of Transaction: " + dateOfTransaction);
                System.out.println("Description: " + description);
            }
			else if (json.has("event")&& "checkout.payment.success".startsWith(json.getString("checkout"))) {
	
				JSONObject data = json.getJSONObject("data");
				String transactionId = data.getString("transactionId");
				String walletId = data.getString("walletId");
				String checkoutRef = data.getString("checkoutRef");
				double amount = data.getDouble("amount");
				String status = data.getString("status");
				String message = data.getString("message");
				String senderAccountNumber = data.getString("senderAccountNumber");
				String senderName = data.getString("senderName");
				String senderBankCode = data.getString("senderBankCode");
				String recipientAccountNumber = data.getString("recipientAccountNumber");
				String recipientName = data.getString("recipientName");
				String reference = data.getString("reference");
				String createdAt = data.getString("createdAt");

				System.out.println("Checkout Payment Success event received.");

				// Here, you can process the checkout payment information as needed
				System.out.println("Checkout Payment Details:");
				System.out.println("Transaction ID: " + transactionId);
				System.out.println("Wallet ID: " + walletId);
				System.out.println("Checkout Reference: " + checkoutRef);
				System.out.println("Amount: " + amount);
				System.out.println("Status: " + status);
				System.out.println("Message: " + message);
				System.out.println("Sender Account Number: " + senderAccountNumber);
				System.out.println("Sender Name: " + senderName);
				System.out.println("Sender Bank Code: " + senderBankCode);
				System.out.println("Recipient Account Number: " + recipientAccountNumber);
				System.out.println("Recipient Name: " + recipientName);
				System.out.println("Reference: " + reference);
				System.out.println("Created At: " + createdAt);
			}
				
			
			
			
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
		response.getWriter().write("{\"message\":\"PSCS SMS callback endpoint active\"}");
	}
}