package com.pscs.moneyxhub.model;


import java.time.LocalDate;

public class LicenseRequest {
	private String email;
	private String productId;
	private LocalDate expiryDate;

	public String getEmail() { 
		return email; 
		}
	public void setEmail(String email) {
		this.email = email; 
		}

	public String getProductId() { 
		return productId; 
		}
	public void setProductId(String productId) { 
		this.productId = productId; 
		}

	public LocalDate getExpiryDate() { 
		return expiryDate; 
		}
	public void setExpiryDate(LocalDate expiryDate) { 
		this.expiryDate = expiryDate; 
			}
}
