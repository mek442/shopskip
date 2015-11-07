package com.skip.entity.payment;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.purchases.Purchases;
import com.skip.entity.user.User;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@Table(name="payment")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findByPaymentId", query = "SELECT p FROM Payment p WHERE p.paymentid = :paymentid"),
    @NamedQuery(name = "Payment.findByCustId", query = "SELECT p FROM Payment p WHERE p.custId = :custId"),
    @NamedQuery(name = "Payment.findByCardId", query = "SELECT p FROM Payment p WHERE p.cardId = :cardId"),
    @NamedQuery(name = "Payment.findByCompany", query = "SELECT p FROM Payment p WHERE p.company = :company"),
    @NamedQuery(name = "Payment.findByUuid", query = "SELECT p FROM Payment p WHERE p.uuid = :uuid")})

public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long paymentid;
	private String cardId;
	private String company;
	private String custId;
	private String uuid;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false)
	private User user;
	

	public Payment() {
	}


	
	public Long getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(Long paymentId) {
		this.paymentid = paymentId;
	}


	@Column(length=45)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}



	public String getCardId() {
		return cardId;
	}



	public void setCardId(String cardId) {
		this.cardId = cardId;
	}



	public String getCustId() {
		return custId;
	}



	public void setCustId(String custId) {
		this.custId = custId;
	}



	@Column(nullable=false, length=36)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	//bi-directional many-to-one association to User
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	

	
	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (paymentid != null ? paymentid.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Payment)) {
	            return false;
	        }
	        Payment other = (Payment) object;
	        if ((this.paymentid == null && other.paymentid != null) || (this.paymentid != null && !this.paymentid.equals(other.paymentid))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.skip.entity.Payment[ paymentId=" + paymentid + " ]";
	    }
}