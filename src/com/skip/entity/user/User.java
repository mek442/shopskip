package com.skip.entity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.payment.Payment;
import com.skip.entity.purchases.Purchases;
import com.skip.entity.social.Social;
import com.skip.entity.token.Token;
import com.skip.util.HashUtil;

import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user",uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"email"}),
	    @UniqueConstraint(columnNames = {"uuid"})})

	@NamedQueries({
	    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userid = :userid"),
	    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
	    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
	    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
	    @NamedQuery(name = "User.findByMobile", query = "SELECT u FROM User u WHERE u.mobile = :mobile"),
	    @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
	    @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status"),
	    @NamedQuery(name = "User.findByUuid", query = "SELECT u FROM User u WHERE u.uuid = :uuid")})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long userid;

	@Column(nullable=false, length=100)
	private String email;

	@Column(length=45)
	private String firstName;

	@Column(length=1)
	private String gender;

	@Column(length=45)
	private String lastName;

	@Column(length=15)
	private String mobile;

	@Column(nullable=false, length=120)
	private String password;

	@Column(length=10)
	private String status;

	@Column(nullable=false, length=36)
	private String uuid;

	//bi-directional many-to-one association to Payment
	@OneToMany(targetEntity=Payment.class,mappedBy="user")
	private List<Payment> payments;

	//bi-directional many-to-one association to Purchas
	@OneToMany(targetEntity=Purchases.class,mappedBy="user")
	private List<Purchases> purchases;

	//bi-directional many-to-one association to Social
	@OneToMany(targetEntity=Social.class,mappedBy="user")
	private List<Social> socials;

	//bi-directional many-to-one association to Token
	@OneToOne(targetEntity=Token.class,mappedBy="user")
	private Token token;

	public User() {
	}

	public Long getUserId() {
		return this.userid;
	}

	public void setUserId(Long userId) {
		this.userid = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setUser(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setUser(null);

		return payment;
	}

	public List<Purchases> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchases> purchases) {
		this.purchases = purchases;
	}

	public Purchases addPurchas(Purchases purchas) {
		getPurchases().add(purchas);
		purchas.setUser(this);

		return purchas;
	}

	public Purchases removePurchas(Purchases purchas) {
		getPurchases().remove(purchas);
		purchas.setUser(null);

		return purchas;
	}

	public List<Social> getSocials() {
		return this.socials;
	}

	public void setSocials(List<Social> socials) {
		this.socials = socials;
	}

	public Social addSocial(Social social) {
		getSocials().add(social);
		social.setUser(this);

		return social;
	}

	public Social removeSocial(Social social) {
		getSocials().remove(social);
		social.setUser(null);

		return social;
	}

	public Token getToken() {
		return this.token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	 public String hashPassword(String passwordToHash) throws Exception {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        return hashToken(encoder.encode(passwordToHash) );
	    }


	    private String hashToken(String token) throws Exception {
	        return HashUtil.byteToBase64(token.getBytes());
	    }

	   public boolean isMatchPassWord(String rawpassword, String hashPassword){
		   try {
			byte[] base64ToByte = HashUtil.base64ToByte(hashPassword);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(rawpassword, new String(base64ToByte));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   return false;
		   
	   }

}