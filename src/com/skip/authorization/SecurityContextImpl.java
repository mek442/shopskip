package com.skip.authorization;


import javax.ws.rs.core.SecurityContext;

import com.skip.resource.ExternalUser;

import java.security.Principal;

public class SecurityContextImpl implements SecurityContext {

    private final ExternalUser user;

    public SecurityContextImpl(ExternalUser user) {
        this.user = user;
    }

    public Principal getUserPrincipal() {
        return user;
    }

    

    public boolean isSecure() {
        return false;
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
