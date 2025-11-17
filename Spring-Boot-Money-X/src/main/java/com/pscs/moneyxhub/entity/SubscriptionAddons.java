/**
 * @author Dipak
 */

package com.pscs.moneyxhub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription_addons")
public class SubscriptionAddons {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String addonName;
	private String addonDescription;
	private String addonPrice;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddonName() {
		return addonName;
	}

	public void setAddonName(String addonName) {
		this.addonName = addonName;
	}

	public String getAddonDescription() {
		return addonDescription;
	}

	public void setAddonDescription(String addonDescription) {
		this.addonDescription = addonDescription;
	}

	public String getAddonPrice() {
		return addonPrice;
	}

	public void setAddonPrice(String addonPrice) {
		this.addonPrice = addonPrice;
	}
		
	
	
}
