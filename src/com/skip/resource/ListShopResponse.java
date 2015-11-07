package com.skip.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ListShopResponse {
	

	public ListShopResponse() {
		// TODO Auto-generated constructor stub
	}

	@XmlElement
	List<ShopResponse> shops;

	public List<ShopResponse> getShops() {
		return shops;
	}

	public void setShops(List<ShopResponse> shops) {
		this.shops = shops;
	}
	
	
}
