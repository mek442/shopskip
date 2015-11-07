package com.skip.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchasesWithDetailsResponse {

	private String purchaseid;
	private String shopname;
	private String shopAddress;
	private String date;
	private String amount;
	private String status;
	private String barcode;
	
	@XmlElement
	private List<PurchaseDetailsResponse> detailsList;

	public PurchasesWithDetailsResponse() {

	}

	public String getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public List<PurchaseDetailsResponse> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<PurchaseDetailsResponse> detailsList) {
		this.detailsList = detailsList;
	}

}
