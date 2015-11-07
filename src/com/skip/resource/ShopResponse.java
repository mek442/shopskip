package com.skip.resource;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ShopResponse {
	private String name;
	private String address;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Long shopId;
	
	
	

	public ShopResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getShopId() {
		return shopId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public void setShopId(Long shopid) {
		this.shopId = shopid;
		
	}
	
	
}
