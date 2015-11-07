package com.skip.service;

import com.skip.entity.user.User;
import com.skip.resource.AuthenticatedUserToken;

public class UserAuthToken {
	private AuthenticatedUserToken token =null;
	private User user = null;
	public AuthenticatedUserToken getToken() {
		return token;
	}
	public void setToken(AuthenticatedUserToken token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
