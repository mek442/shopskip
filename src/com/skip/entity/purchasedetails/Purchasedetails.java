package com.skip.entity.purchasedetails;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.product.Product;
import com.skip.entity.purchases.Purchases;

/**
 * The persistent class for the purchasedetails database table.
 * 
 */
@Entity
@Table(name = "purchasedetails")
@NamedQueries({
		@NamedQuery(name = "Purchasedetails.findAll", query = "SELECT p FROM Purchasedetails p"),
		@NamedQuery(name = "Purchasedetails.findByQuantity", query = "SELECT p FROM Purchasedetails p WHERE p.quantity = :quantity"),
		@NamedQuery(name = "Purchasedetails.findByPurchasedetailsid", query = "SELECT p FROM Purchasedetails p WHERE p.purchasedetailsid = :purchasedetailsid") })
public class Purchasedetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long purchasedetailsid;

	@Column(nullable = false)
	private double quantity;

	// bi-directional many-to-one association to Purchas
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaseId", nullable = false)
	private Purchases purchases;

	// bi-directional many-to-one association to Product
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	private Product product;
	
	

	public Purchasedetails() {
	}

	public Long getPurchasedetailsid() {
		return this.purchasedetailsid;
	}

	public void setPurchasedetailsid(Long purchasedetailsid) {
		this.purchasedetailsid = purchasedetailsid;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Purchases getPurchases() {
		return this.purchases;
	}

	public void setPurchases(Purchases purchas) {
		this.purchases = purchas;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Purchasedetails)) {
			return false;
		}
		Purchasedetails other = (Purchasedetails) object;
		if ((this.purchasedetailsid == null && other.purchasedetailsid != null)
				|| (this.purchasedetailsid != null && !this.purchasedetailsid
						.equals(other.purchasedetailsid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.skip.entity.Purchasedetails[ purchasedetailsid="
				+ purchasedetailsid + " ]";
	}

	

}