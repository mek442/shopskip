package com.skip.authorization;

import com.skip.resource.ExternalUser;


public interface AuthorizationService {

    /**
     * Given an AuthorizationRequestContext validate and authorize a User
     *
     * @param authorizationRequestContext the context required to authorize a user for a particular request
     * @return ExternalUser
     */
    public ExternalUser authorize(AuthorizationRequestContext authorizationRequestContext);
}
