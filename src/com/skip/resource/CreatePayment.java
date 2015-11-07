package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreatePayment {
	   private String creditholdername;
	   private String creditcardnumber;
	   private String expiration;
	   private String company;
	   private String secret;
	   
	   public CreatePayment() {
		// TODO Auto-generated constructor stub
	}

	public String getCreditholdername() {
		return creditholdername;
	}

	public void setCreditholdername(String creditholdername) {
		this.creditholdername = creditholdername;
	}

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	   
	   
}
