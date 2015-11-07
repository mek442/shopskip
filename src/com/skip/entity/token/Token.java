package com.skip.entity.token;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.user.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


/**
 * The persistent class for the token database table.
 * 
 */
@Entity
@Table(name="token")
@NamedQueries({
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t"),
    @NamedQuery(name = "Token.findByAuthtoken", query = "SELECT t FROM Token t WHERE t.authtoken = :authtoken"),
    @NamedQuery(name = "Token.findByTokenId", query = "SELECT t FROM Token t WHERE t.tokenid = :tokenid"),
    @NamedQuery(name = "Token.findByAuthcreation", query = "SELECT t FROM Token t WHERE t.authcreation = :authcreation"),
    @NamedQuery(name = "Token.findByAuthexpiration", query = "SELECT t FROM Token t WHERE t.authexpiration = :authexpiration"),
    @NamedQuery(name = "Token.findByVerificationToken", query = "SELECT t FROM Token t WHERE t.verificationToken = :verificationToken"),
    @NamedQuery(name = "Token.findByVerexpiration", query = "SELECT t FROM Token t WHERE t.verexpiration = :verexpiration"),
    @NamedQuery(name = "Token.findByVercreation", query = "SELECT t FROM Token t WHERE t.vercreation = :vercreation"),
    @NamedQuery(name = "Token.findByVertype", query = "SELECT t FROM Token t WHERE t.vertype = :vertype")})

public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Integer DEFAULT_TIME_TO_LIVE_IN_SECONDS = (60 * 60 * 24 * 1); //1 Days
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long tokenid;

	@Column(nullable=false)
	private Timestamp authcreation;

	private Timestamp authexpiration;

	@Column(nullable=false, length=36)
	private String authtoken;

	private Timestamp vercreation;

	private Timestamp verexpiration;

	@Column(length=36)
	private String verificationToken;

	@Column(length=10)
	private String vertype;
	private String refreshtoken;
	public String getRefreshtoken() {
		return refreshtoken;
	}


	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}


	public Timestamp getRefreshexpiration() {
		return refreshexpiration;
	}


	public void setRefreshexpiration(Timestamp refreshexpiration) {
		this.refreshexpiration = refreshexpiration;
	}

	private Timestamp refreshexpiration;

	//bi-directional many-to-one association to User
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false)
	private User user;

	public Token(){
		
	}
	

	public Long getTokenId() {
		return this.tokenid;
	}

	public void setTokenId(Long tokenId) {
		this.tokenid = tokenId;
	}

	public Timestamp getAuthcreation() {
		return this.authcreation;
	}

	public void setAuthcreation(Timestamp authcreation) {
		this.authcreation = authcreation;
	}

	public Timestamp getAuthexpiration() {
		return this.authexpiration;
	}

	public void setAuthexpiration(Timestamp authexpiration) {
		this.authexpiration = authexpiration;
	}

	public String getAuthtoken() {
		return this.authtoken;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}

	public Timestamp getVercreation() {
		return this.vercreation;
	}

	public void setVercreation(Timestamp vercreation) {
		this.vercreation = vercreation;
	}

	public Timestamp getVerexpiration() {
		return this.verexpiration;
	}

	public void setVerexpiration(Timestamp verexpiration) {
		this.verexpiration = verexpiration;
	}

	public String getVerificationToken() {
		return this.verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getVertype() {
		return this.vertype;
	}

	public void setVertype(String vertype) {
		this.vertype = vertype;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean hasAuthExpired() {
		if (this.authexpiration == null) {
			return true;
		}
		long time = System.currentTimeMillis();
		if (this.authexpiration != null) {

			if (this.authcreation.after(this.authexpiration)) {
				return true;
			}
			return this.authexpiration.before(new Date(time));
		}

		return false;

	}


	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (tokenid != null ? tokenid.hashCode() : 0);
	        hash += (authtoken != null ? authtoken.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Token)) {
	            return false;
	        }
	        Token other = (Token) object;
	        if ((this.tokenid == null && other.tokenid != null) || (this.tokenid != null && !this.tokenid.equals(other.tokenid))||(this.authtoken!=null && !this.authtoken.equals(other.authtoken))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.skip.entity.Token[ tokenId=" + tokenid + " ]";
	    }
}