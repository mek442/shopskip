package com.skip.entity.social;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.user.User;

import java.sql.Timestamp;


/**
 * The persistent class for the social database table.
 * 
 */
@Entity
@Table(name="social")
@NamedQueries({
    @NamedQuery(name = "Social.findAll", query = "SELECT s FROM Social s"),
    @NamedQuery(name = "Social.findByProviderId", query = "SELECT s FROM Social s WHERE s.providerId = :providerId"),
    @NamedQuery(name = "Social.findByProviderUserId", query = "SELECT s FROM Social s WHERE s.providerUserId = :providerUserId"),
    @NamedQuery(name = "Social.findByAccessToken", query = "SELECT s FROM Social s WHERE s.accessToken = :accessToken"),
    @NamedQuery(name = "Social.findBySecret", query = "SELECT s FROM Social s WHERE s.secret = :secret"),
    @NamedQuery(name = "Social.findByRefreshToken", query = "SELECT s FROM Social s WHERE s.refreshToken = :refreshToken"),
    @NamedQuery(name = "Social.findByExpirationTime", query = "SELECT s FROM Social s WHERE s.expirationTime = :expirationTime"),
    @NamedQuery(name = "Social.findBySocialId", query = "SELECT s FROM Social s WHERE s.socialId = :socialId")})

public class Social implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long socialId;

	@Column(length=200)
	private String accessToken;

	private Timestamp expirationTime;

	@Column(nullable=false, length=36)
	private String providerId;

	@Column(length=45)
	private String providerUserId;

	@Column(length=100)
	private String refreshToken;

	@Column(length=45)
	private String secret;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId", nullable=false)
	private User user;

	public Social() {
	}

	public Long getSocialId() {
		return this.socialId;
	}

	public void setSocialId(Long socialId) {
		this.socialId = socialId;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Timestamp getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return this.providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	  @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (socialId != null ? socialId.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Social)) {
	            return false;
	        }
	        Social other = (Social) object;
	        if ((this.socialId == null && other.socialId != null) || (this.socialId != null && !this.socialId.equals(other.socialId))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.skip.entity.social.Social[ socialId=" + socialId + " ]";
	    }
}