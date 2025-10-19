package com.pscs.moneyxhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CORP_CUSTOMER_MASTER_TBL")
public class CorporateCustomer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column(name = "ORGANIZATION_ID", length = 36)
    private String organizationId;

    @Column(name = "CUSTOMER_TYPE_ID", length = 36)
    private String customerTypeId;

    @Column(name = "CUSTOMER_TYPE", length = 50)
    private String customerType;

    @Column(name = "CITY", length = 100)
    private String city;

    @Column(name = "RC_NUMBER", length = 50)
    private String rcNumber;

    @Column(name = "FULL_BUSINESS_NAME", length = 200)
    private String fullBusinessName;

    @Column(name = "WALLET_PREFERRED_NAME", length = 200)
    private String walletPreferredName;

    @Column(name = "TIN", length = 50)
    private String tin;

    @Column(name = "BUSINESS_ADDRESS", length = 300)
    private String businessAddress;

    @Column(name = "COUNTRY_ID", length = 36)
    private String countryId;

    @Column(name = "CUNTRY", length = 100)  // as per your JSON key typo
    private String cuntry;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "USER_NAME", length = 100)
    private String userName;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "TXN_PIN", length = 10)
    private String txnPin;

    @Column(name = "AUTH_TYPE", length = 20)
    private String authType;

    @Column(name = "AUTH_VALUE", length = 20)
    private String authValue;

    @Column(name = "MOBILE_NUMBER", length = 20)
    private String mobileNumber;
    
	private int retryLoginAttempt;
	private String isLocked;
	private String isActive;
	private String lastLoginTime;
	@Column(name = "retry_login_active")
	private String isLoginAttemptActive;
	
	private String accountNumber;
	private String accountType;
	private String currency;
	private String customerId;
	private String  country;
	
	
	

    // ===== Getters and Setters =====
	

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getRetryLoginAttempt() {
		return retryLoginAttempt;
	}

	public void setRetryLoginAttempt(int retryLoginAttempt) {
		this.retryLoginAttempt = retryLoginAttempt;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getIsLoginAttemptActive() {
		return isLoginAttemptActive;
	}

	public void setIsLoginAttemptActive(String isLoginAttemptActive) {
		this.isLoginAttemptActive = isLoginAttemptActive;
	}

	

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRcNumber() {
        return rcNumber;
    }

    public void setRcNumber(String rcNumber) {
        this.rcNumber = rcNumber;
    }

    public String getFullBusinessName() {
        return fullBusinessName;
    }

    public void setFullBusinessName(String fullBusinessName) {
        this.fullBusinessName = fullBusinessName;
    }

    public String getWalletPreferredName() {
        return walletPreferredName;
    }

    public void setWalletPreferredName(String walletPreferredName) {
        this.walletPreferredName = walletPreferredName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCuntry() {
        return cuntry;
    }

    public void setCuntry(String cuntry) {
        this.cuntry = cuntry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTxnPin() {
        return txnPin;
    }

    public void setTxnPin(String txnPin) {
        this.txnPin = txnPin;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthValue() {
        return authValue;
    }

    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

	@Override
	public String toString() {
		return "CorporateCustomer [id=" + id + ", organizationId=" + organizationId + ", customerTypeId="
				+ customerTypeId + ", customerType=" + customerType + ", city=" + city + ", rcNumber=" + rcNumber
				+ ", fullBusinessName=" + fullBusinessName + ", walletPreferredName=" + walletPreferredName + ", tin="
				+ tin + ", businessAddress=" + businessAddress + ", countryId=" + countryId + ", cuntry=" + cuntry
				+ ", email=" + email + ", userName=" + userName + ", password=" + password + ", txnPin=" + txnPin
				+ ", authType=" + authType + ", authValue=" + authValue + ", mobileNumber=" + mobileNumber
				+ ", retryLoginAttempt=" + retryLoginAttempt + ", isLocked=" + isLocked + ", isActive=" + isActive
				+ ", lastLoginTime=" + lastLoginTime + ", isLoginAttemptActive=" + isLoginAttemptActive
				+ ", accountNumber=" + accountNumber + ", accountType=" + accountType + ", currency=" + currency
				+ ", customerId=" + customerId + "]";
	}

}
