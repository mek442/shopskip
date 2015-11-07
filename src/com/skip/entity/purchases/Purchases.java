package com.skip.entity.purchases;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.payment.Payment;
import com.skip.entity.purchasedetails.Purchasedetails;
import com.skip.entity.shop.Shop;
import com.skip.entity.user.User;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the purchases database table.
 * 
 */
@Entity
@Table(name="purchases")
@NamedQueries({
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p"),
    @NamedQuery(name = "Purchases.findByPurchaseId", query = "SELECT p FROM Purchases p WHERE p.purchaseId = :purchaseId"),
    @NamedQuery(name = "Purchases.findByDate", query = "SELECT p FROM Purchases p WHERE p.date = :date"),
    @NamedQuery(name = "Purchases.findByAmount", query = "SELECT p FROM Purchases p WHERE p.amount = :amount"),
    @NamedQuery(name = "Purchases.findByStatus", query = "SELECT p FROM Purchases p WHERE p.status = :status"),
    @NamedQuery(name = "Purchases.findByBarcode", query = "SELECT p FROM Purchases p WHERE p.barcode = :barcode")})

public class Purchases implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long purchaseId;

	private double amount;

	@Column(length=15)
	private String barcode;

	@Column(nullable=false)
	private Timestamp date;

	@Column(length=7)
	private String status;

	//bi-directional many-to-one association to Purchasedetail
	@OneToMany(targetEntity=Purchasedetails.class,mappedBy="purchases")
	private List<Purchasedetails> purchasedetails;

	//bi-directional many-to-one association to Payment
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="paymentId", nullable=false)
	private Payment payment;

	//bi-directional many-to-one association to Shop
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shopId", nullable=false)
	private Shop shop;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false)
	private User user;

	

	public Purchases() {
	}

	public Long getPurchaseId() {
		return this.purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Purchasedetails> getPurchasedetails() {
		return this.purchasedetails;
	}

	public void setPurchasedetails(List<Purchasedetails> purchasedetails) {
		this.purchasedetails = purchasedetails;
	}

	public Purchasedetails addPurchasedetail(Purchasedetails purchasedetail) {
		getPurchasedetails().add(purchasedetail);
		purchasedetail.setPurchases(this);

		return purchasedetail;
	}

	public Purchasedetails removePurchasedetail(Purchasedetails purchasedetail) {
		getPurchasedetails().remove(purchasedetail);
		purchasedetail.setPurchases(null);

		return purchasedetail;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}