package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchaseDetailsResponse {
	    private String name;
	    private String description;
	    private String barcode;
	    private double unitprice;
	    private double quantity;
	    
	    public PurchaseDetailsResponse() {
			// TODO Auto-generated constructor stub
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public double getUnitprice() {
			return unitprice;
		}

		public void setUnitprice(double unitprice) {
			this.unitprice = unitprice;
		}

		public double getQuantity() {
			return quantity;
		}

		public void setQuantity(double quantity) {
			this.quantity = quantity;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	    
	    
}
