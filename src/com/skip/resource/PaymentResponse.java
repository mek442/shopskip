package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentResponse {
	private String company;
	private String last4digit;
	private String expiration;
	private String id;
	
	public PaymentResponse() {
		// TODO Auto-generated constructor stub
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLast4digit() {
		return last4digit;
	}
	public void setLast4digit(String last4digit) {
		this.last4digit = last4digit;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
