package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreatePurchaseDetails {
	private long productid;
	private int quantity;

	public CreatePurchaseDetails() {
		// TODO Auto-generated constructor stub
	}

	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
