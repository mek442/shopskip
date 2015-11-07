package com.skip.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class CreatePurchase {
	
	private long shopid;
	private String paymentid;
	private double amount;
	
	@XmlElement
	List<CreatePurchaseDetails> purchaseDetails;
	
	public CreatePurchase() {
		// TODO Auto-generated constructor stub
	}

	public long getShopid() {
		return shopid;
	}

	public void setShopid(long shopid) {
		this.shopid = shopid;
	}

	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public List<CreatePurchaseDetails> getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(List<CreatePurchaseDetails> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	

}
