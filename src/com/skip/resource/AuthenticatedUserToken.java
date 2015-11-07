package com.skip.resource;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticatedUserToken {

    private String userId;
    private String token;
    private String refreshtoken;

    public AuthenticatedUserToken(){}

    public AuthenticatedUserToken(String userId, String sessionToken, String refreshtoken) {
        this.userId = userId;
        this.token = sessionToken;
        this.refreshtoken = refreshtoken;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getRefreshtoken() {
		return refreshtoken;
	}
    
    public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}
}
