package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductResponse {
	private String productid;
	   private String name;
	   private String description;
	   private String barcode;
	   private String unitprice;
	   private String type;
	   private String manufacturer;
	   private String shopid;

	 
	   public ProductResponse() {
		
	}

	   /**
	    * Returns the value of the <code>productid</code> property.
	    *
	    * @return the value of the <code>productid</code> property.
	    */
	   public String getProductid() {
	      return productid;
	   }

	   /**
	    * Sets the value of the <code>productid</code> property.
	    *
	    * @param productid a value for <code>productid</code>.
	    */
	   public void setProductid(String productid) {
	      this.productid = productid;
	   }

	   /**
	    * Returns the value of the <code>name</code> property.
	    *
	    * @return the value of the <code>name</code> property.
	    */
	   public String getName() {
	      return name;
	   }

	   /**
	    * Sets the value of the <code>name</code> property.
	    *
	    * @param name a value for <code>name</code>.
	    */
	   public void setName(String name) {
	      this.name = name;
	   }

	   /**
	    * Returns the value of the <code>description</code> property.
	    *
	    * @return the value of the <code>description</code> property.
	    */
	   public String getDescription() {
	      return description;
	   }

	   /**
	    * Sets the value of the <code>description</code> property.
	    *
	    * @param description a value for <code>description</code>.
	    */
	   public void setDescription(String description) {
	      this.description = description;
	   }

	   /**
	    * Returns the value of the <code>barcode</code> property.
	    *
	    * @return the value of the <code>barcode</code> property.
	    */
	   public String getBarcode() {
	      return barcode;
	   }

	   /**
	    * Sets the value of the <code>barcode</code> property.
	    *
	    * @param barcode a value for <code>barcode</code>.
	    */
	   public void setBarcode(String barcode) {
	      this.barcode = barcode;
	   }

	   /**
	    * Returns the value of the <code>unitprice</code> property.
	    *
	    * @return the value of the <code>unitprice</code> property.
	    */
	   public String getUnitprice() {
	      return unitprice;
	   }

	   /**
	    * Sets the value of the <code>unitprice</code> property.
	    *
	    * @param unitprice a value for <code>unitprice</code>.
	    */
	   public void setUnitprice(String unitprice) {
	      this.unitprice = unitprice;
	   }

	   /**
	    * Returns the value of the <code>type</code> property.
	    *
	    * @return the value of the <code>type</code> property.
	    */
	   public String getType() {
	      return type;
	   }

	   /**
	    * Sets the value of the <code>type</code> property.
	    *
	    * @param type a value for <code>type</code>.
	    */
	   public void setType(String type) {
	      this.type = type;
	   }

	   /**
	    * Returns the value of the <code>manufacturer</code> property.
	    *
	    * @return the value of the <code>manufacturer</code> property.
	    */
	   public String getManufacturer() {
	      return manufacturer;
	   }

	   /**
	    * Sets the value of the <code>manufacturer</code> property.
	    *
	    * @param manufacturer a value for <code>manufacturer</code>.
	    */
	   public void setManufacturer(String manufacturer) {
	      this.manufacturer = manufacturer;
	   }

	   /**
	    * Returns the value of the <code>shopid</code> property.
	    *
	    * @return the value of the <code>shopid</code> property.
	    */
	   public String getShopid() {
	      return shopid;
	   }

	   /**
	    * Sets the value of the <code>shopid</code> property.
	    *
	    * @param shopid a value for <code>shopid</code>.
	    */
	   public void setShopid(String shopid) {
	      this.shopid = shopid;
	   }
}
