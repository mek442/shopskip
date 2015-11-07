package com.skip.service;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.social.connect.Connection;

import com.skip.entity.token.Token;
import com.skip.entity.user.User;
import com.skip.resource.AuthenticatedUserToken;
import com.skip.resource.CreatePayment;
import com.skip.resource.CreatePurchase;
import com.skip.resource.CreateUserRequest;
import com.skip.resource.ExternalUser;
import com.skip.resource.LoginRequest;
import com.skip.resource.PaymentResponse;
import com.skip.resource.PurchasesResponse;
import com.skip.resource.PurchasesWithDetailsResponse;
import com.skip.resource.RefreshToken;
import com.skip.resource.UpdateUserRequest;


public interface UserService {


    /**
     * Create a new User with the given role
     *
     * @param request
     * @param role
     * @return AuthenticatedUserToken
     */
    public  UserAuthToken createUser(CreateUserRequest request);


   

    /**
     * Login a User
     *
     * @param request
     * @return AuthenticatedUserToken
     */
    public  UserAuthToken login(HttpHeaders headers,LoginRequest request);

    /**
     * Log in a User using Connection details from an authorized request from the User's supported Social provider
     * encapsulated in the {@link org.springframework.social.connect.Connection} parameter
     *
     * @param connection containing the details of the authorized user account form the Social provider
     * @return the User account linked to the {@link com.porterhead.rest.user.domain.SocialUser} account
     */
    public AuthenticatedUserToken socialLogin(Connection<?> connection);

    /**
     * Get a User based on a unique identifier
     *
     * Identifiers supported are uuid, emailAddress
     *
     * @param userIdentifier
     * @return  User
     */
    public ExternalUser getUser(ExternalUser requestingUser, String userIdentifier);

    /**
     * Delete user, only authenticated user accounts can be deleted
     *
     * @param userMakingRequest the user authorized to delete the user
     * @param userId the id of the user to delete
     */
    public void deleteUser(ExternalUser userMakingRequest, String userId);

    
	public AuthenticatedUserToken getToken(RefreshToken refToken,
			String userIdentifier);
    /**
     * Save User
     *
     * @param userId
     * @param request
     */
    public ExternalUser saveUser(String userId, UpdateUserRequest request);

    /**
     * Create an AuthorizationToken for the User
     *
     * @return
     */
    public Token createAuthorizationToken(User user);




	public PaymentResponse createPaymentMethod(ExternalUser userMakingRequest,
			CreatePayment payment);




	public PurchasesResponse makePurchases(ExternalUser userMakingRequest,
			CreatePurchase purchaseRequest);




	public PurchasesResponse getPurchases(ExternalUser userMakingRequest,
			long purchaseId);




	public PurchasesWithDetailsResponse getPurchasesDetails(
			ExternalUser userMakingRequest, long purchaseId);
	
	
	

       
	
}
