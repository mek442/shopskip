package com.skip.authorization;

import org.springframework.beans.factory.annotation.Autowired;

import com.skip.encryption.AESSkipEncryption;
import com.skip.entity.token.Token;
import com.skip.entity.user.User;
import com.skip.exception.GenericBusinessException;
import com.skip.exception.ServiceInstantiationException;
import com.skip.exception.UnknownServiceException;
import com.skip.resource.ExternalUser;
import com.skip.rest.exception.AuthorizationException;
import com.skip.servicelocator.ServiceLocator;
import com.skip.session.ITokenSession;
import com.skip.session.IUserSession;

public class SessionTokenAuthorizationService implements AuthorizationService {

	/**
	 * directly access user objects
	 */
	private final ITokenSession tokenRepository;

	private AESSkipEncryption encryption;

	public SessionTokenAuthorizationService(ServiceLocator serviceLocator,
			AESSkipEncryption encryption) throws Exception,
			ServiceInstantiationException {
		this.tokenRepository = (ITokenSession) serviceLocator
				.getService(ServiceLocator.TOKENSESSION_SERVICE);
		this.encryption = encryption;
	}

	public ExternalUser authorize(AuthorizationRequestContext securityContext) {
		String token = securityContext.getAuthorizationToken();
		ExternalUser externalUser = null;
		if (token == null) {
			System.out.println(token );
			return externalUser;
		}
		Token authorizationToken;
		String userid;
		try {
			String decryptedToken = encryption.decrypt(token);
			userid = decryptedToken.split(":")[0];
			token = decryptedToken.split(":")[1];
			authorizationToken = tokenRepository.findTokenByAuthtoken(token);
			
			System.out.println(token +" "+authorizationToken.getAuthtoken());
			
			
		} catch (Exception e) {
			throw new AuthorizationException("Session token not valid");
		}
		if (authorizationToken == null) {
			throw new AuthorizationException("Session token not valid");
		}

		if (authorizationToken.hasAuthExpired()) {
			throw new AuthorizationException("Session token not valid");
		}
		if (!authorizationToken.getUser().getUuid().equals(userid)) {
			throw new AuthorizationException("Session token not valid");
		}
		if (!authorizationToken.getAuthtoken().equals(token)) {
			throw new AuthorizationException("Session token not valid");
		
		}
		if (authorizationToken.getAuthtoken().equals(token)) {
			externalUser = new ExternalUser(authorizationToken.getUser());
		}
		return externalUser;
	}
}
