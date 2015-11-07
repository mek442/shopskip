package com.skip.service;



import com.skip.config.ApplicationConfig;
import com.skip.config.ApplicationDevConfig;
import com.skip.entity.token.Token;
import com.skip.resource.AuthenticatedUserToken;
import com.skip.resource.CreatePayment;
import com.skip.resource.CreatePurchase;
import com.skip.resource.CreateUserRequest;
import com.skip.resource.ExternalUser;
import com.skip.resource.LoginRequest;
import com.skip.resource.OAuth2Request;
import com.skip.resource.PasswordRequest;
import com.skip.resource.PaymentResponse;
import com.skip.resource.PurchasesResponse;
import com.skip.resource.PurchasesWithDetailsResponse;
import com.skip.resource.RefreshToken;
import com.skip.resource.UpdateUserRequest;
import com.skip.rest.exception.AuthenticationException;
import com.skip.rest.exception.AuthorizationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;

@Path("/user")
@Component
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserResource {

	
    private ConnectionFactoryRegistry connectionFactoryLocator;

    @Autowired
    protected UserService userService;
  
    @Context
    protected UriInfo uriInfo;

    @Autowired
    ApplicationConfig config;

    @Autowired
    public UserResource(ConnectionFactoryRegistry connectionFactoryLocator) {
        this.connectionFactoryLocator = connectionFactoryLocator;
    }


    @PermitAll
    @POST
    public Response signupUser(CreateUserRequest request) {
    
       UserAuthToken token = userService.createUser(request);
        //verificationTokenService.sendEmailRegistrationToken(token.getUserId());
        URI location = uriInfo.getAbsolutePathBuilder().path(token.getUser().getUuid()).build();
        return Response.created(location).entity(token.getToken()).build();
    }

    @RolesAllowed("admin")
    @Path("{userId}")
    @DELETE
    public Response deleteUser(@Context SecurityContext sc, @PathParam("userId") String userId) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        userService.deleteUser(userMakingRequest, userId);
        return Response.ok().build();
    }

    @PermitAll
    @Path("login")
    @POST
    public Response login(@Context HttpHeaders httpHeaders, LoginRequest request) {
        UserAuthToken token = userService.login(httpHeaders,request);
        return getLoginResponse(token);
    }

    @PermitAll
    @Path("login/{providerId}")
    @POST
    public Response socialLogin(@PathParam("providerId") String providerId, OAuth2Request request) {
        OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
        Connection<?> connection = connectionFactory.createConnection(new AccessGrant(request.getAccessToken()));
        AuthenticatedUserToken token = userService.socialLogin(connection);
       // return getLoginResponse(token);
        return null;
    }

   // @RolesAllowed({"authenticated"})
    @Path("{userId}")
    @GET
    public Response getUser(@Context SecurityContext sc, @PathParam("userId") String userId) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        ExternalUser user =  userService.getUser(userMakingRequest, userId);
       
        return Response.ok().entity(user).build();
    }
    
    @Path("{userId}/token/")
    @POST
    public Response getUser(@PathParam("userId") String userId, RefreshToken token) {
       
       AuthenticatedUserToken token2 = userService.getToken(token, userId);
       
        return Response.ok().entity(token2).build();
    }
    
    @Path("{userId}/payment/")
    @POST
    public Response addPaymentMethod(@Context SecurityContext sc, @PathParam("userId") String userId, CreatePayment payment) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        
        
        if(!userMakingRequest.getId().equalsIgnoreCase(userId)){
        	throw new AuthenticationException();
        }
        PaymentResponse paymentResponse =  userService.createPaymentMethod(userMakingRequest, payment);
        return Response.ok().entity(paymentResponse).build();
    }
    
   
    
    @Path("{userId}/purchases/{purchaseId}")
    @GET
    public Response getPurchasesDetails(@Context SecurityContext sc, @PathParam("userId") String userId, @PathParam("purchaseId") long purchaseId, @QueryParam("type") String type) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        
       
        if(!userMakingRequest.getId().equalsIgnoreCase(userId)){
        	throw new AuthenticationException();
        }
        
        if(type== null || !type.equalsIgnoreCase("details")){
        	PurchasesResponse purchaseResponse =  userService.getPurchases(userMakingRequest, purchaseId);
            return Response.ok().entity(purchaseResponse).build();
        }
        PurchasesWithDetailsResponse purchaseResponse =  userService.getPurchasesDetails(userMakingRequest, purchaseId);
        return Response.ok().entity(purchaseResponse).build();
    }
    
    @Path("{userId}/purchases/")
    @POST
    public Response addPurchases(@Context SecurityContext sc, @PathParam("userId") String userId, CreatePurchase purchaseRequest) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        
        
        if(!userMakingRequest.getId().equalsIgnoreCase(userId)){
        	throw new AuthenticationException();
        }
        PurchasesResponse purchaseResponse =  userService.makePurchases(userMakingRequest, purchaseRequest);
        return Response.ok().entity(purchaseResponse).build();
    }

    @RolesAllowed({"authenticated"})
    @Path("{userId}")
    @PUT
    public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, UpdateUserRequest request) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        if(!userMakingRequest.getId().equals(userId)) {
            throw new AuthorizationException("User not authorized to modify this profile");
        }
        boolean sendVerificationToken = StringUtils.hasLength(request.getEmailAddress()) &&
                !request.getEmailAddress().equals(userMakingRequest.getEmailAddress());
        ExternalUser savedUser = userService.saveUser(userId, request);
        if(sendVerificationToken) {
          //  verificationTokenService.sendEmailVerificationToken(savedUser.getId());
        }
        return Response.ok().build();
    }

    private Response getLoginResponse(UserAuthToken token) {
        URI location = UriBuilder.fromPath(uriInfo.getBaseUri() + "user/" + token.getUser().getUuid()).build();
        return Response.ok().entity(token.getToken()).contentLocation(location).build();
    }

}
