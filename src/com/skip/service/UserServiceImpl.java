package com.skip.service;

import com.skip.authorization.AuthorizationRequestContext;
import com.skip.config.ApplicationConfig;
import com.skip.encryption.AESSkipEncryption;
import com.skip.entity.payment.Payment;
import com.skip.entity.purchasedetails.Purchasedetails;
import com.skip.entity.purchases.Purchases;
import com.skip.entity.token.Token;
import com.skip.entity.user.User;
import com.skip.exception.GenericBusinessException;
import com.skip.exception.ServiceInstantiationException;
import com.skip.exception.UnknownServiceException;
import com.skip.resource.AuthenticatedUserToken;
import com.skip.resource.CreatePayment;
import com.skip.resource.CreatePurchase;
import com.skip.resource.CreatePurchaseDetails;
import com.skip.resource.CreateUserRequest;
import com.skip.resource.ExternalUser;
import com.skip.resource.LoginRequest;
import com.skip.resource.PaymentResponse;
import com.skip.resource.PurchaseDetailsResponse;
import com.skip.resource.PurchasesResponse;
import com.skip.resource.PurchasesWithDetailsResponse;
import com.skip.resource.RefreshToken;
import com.skip.resource.UpdateUserRequest;
import com.skip.rest.exception.AuthenticationException;
import com.skip.rest.exception.AuthorizationException;
import com.skip.rest.exception.DuplicateUserException;
import com.skip.rest.exception.ErrorResponse;
import com.skip.rest.exception.InternalServerException;
import com.skip.rest.exception.UserNotFoundException;
import com.skip.servicelocator.ServiceLocator;
import com.skip.session.IPaymentSession;
import com.skip.session.IProductSession;
import com.skip.session.IPurchaseDetailsSession;
import com.skip.session.IPurchasesSession;
import com.skip.session.IShopSession;
import com.skip.session.IUserSession;
import com.skip.util.DateUtil;
import com.sun.jersey.spi.container.ContainerRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

	/**
	 * For Social API handling
	 */
	private ServiceLocator serviceLocator;

	private IUserSession userRepository;
	private IPaymentSession paymentRepository;
	private IPurchasesSession purchaseRepository;
	private IPurchaseDetailsSession purchaseDetailsRepository;
	private IProductSession productRepository;
	private IShopSession shopRepository;

	private ApplicationConfig applicationConfig;

	private AESSkipEncryption encryption;

	public UserServiceImpl() {
		// super(validator);
	}

	@Autowired
	public UserServiceImpl(ServiceLocator serviceLocator,
			ApplicationConfig applicationConfig, AESSkipEncryption encryption)
			throws UnknownServiceException, ServiceInstantiationException {
		// this(validator);
		this.userRepository = (IUserSession) serviceLocator
				.getService(ServiceLocator.USERSESSION_SERVICE);
		this.paymentRepository = (IPaymentSession) serviceLocator
				.getService(ServiceLocator.PAYMENTSESSION_SERVICE);
		this.purchaseRepository = (IPurchasesSession) serviceLocator
				.getService(ServiceLocator.PURCHASESSESSION_SERVICE);
		this.productRepository = (IProductSession) serviceLocator
				.getService(ServiceLocator.PRODUCTSESSION_SERVICE);
		this.shopRepository = (IShopSession) serviceLocator
				.getService(ServiceLocator.SHOPSESSION_SERVICE);
		this.purchaseDetailsRepository = (IPurchaseDetailsSession) serviceLocator
				.getService(ServiceLocator.PURCHASEDETAILSSESSION_SERVICE);
		this.applicationConfig = applicationConfig;
		this.encryption = encryption;
	}

	private static final Logger LOG = LoggerFactory
			.getLogger(UserServiceImpl.class);

	/**
	 * {@inheritDoc}
	 *
	 * This method creates a User with the given Role. A check is made to see if
	 * the username already exists and a duplication check is made on the email
	 * address if it is present in the request.
	 * <P>
	 * </P>
	 * The password is hashed and a AuthorizationToken generated for subsequent
	 * authorization of role-protected requests.
	 *
	 */

	public UserAuthToken createUser(CreateUserRequest request)throws WebApplicationException{
		// validate(request);
		try{
			User searchedForUser = userRepository.findUserByEmail(request.getEmailAddress());
			if (searchedForUser != null) {
				throw new DuplicateUserException();
			}
		
			User newUser = createNewUser(request);
			User addUser = userRepository.addUser(newUser);
			 Token createAuthorizationToken = createAuthorizationToken(addUser);
			AuthenticatedUserToken token = new AuthenticatedUserToken(addUser
					.getUuid().toString(),
					createAuthorizationToken.getAuthtoken(),createAuthorizationToken.getRefreshtoken());
			UserAuthToken userAuthToken = new UserAuthToken();
			userAuthToken.setUser(addUser);
			userAuthToken.setToken(token);
			return userAuthToken;
		} catch (WebApplicationException ex) {
			ex.printStackTrace();
			throw ex;
		}catch ( GenericBusinessException ex) {
			ex.printStackTrace();
			throw new InternalServerException();
		}
		
	}

	/**
	 * {@inheritDoc}
	 *
	 * Login supports authentication against an email attribute. If a User is
	 * retrieved that matches, the password in the request is hashed and
	 * compared to the persisted password for the User account.
	 */

	public UserAuthToken login(HttpHeaders headers, LoginRequest request) {
		// validate(request);
		User user = null;
		try {
			user = userRepository.findUserByEmail(request.getUsername());
		} catch (GenericBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			throw new AuthenticationException();
		}

		if (user.isMatchPassWord(request.getPassword(), user.getPassword())) {
			UserAuthToken userAuthToken = new UserAuthToken();
			userAuthToken.setUser(user);
			userAuthToken.setToken(updateAuthorizationToken(user, headers));
			
			return userAuthToken;
		} else {
			throw new AuthenticationException();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Associate a Connection with a User account. If one does not exist a new
	 * User is created and linked to the
	 * {@link com.porterhead.rest.user.domain.SocialUser} represented in the
	 * Connection details.
	 *
	 * <P>
	 * </P>
	 *
	 * A AuthorizationToken is generated and any Profile data that can be
	 * collected from the Social account is propagated to the User object.
	 *
	 */

	/**
	 * Allow user to get their own profile or a user with administrator role to
	 * get any profile
	 *
	 * @param requestingUser
	 * @param userIdentifier
	 * @return user
	 */

	public ExternalUser getUser(ExternalUser requestingUser,
			String userIdentifier) {
		Assert.notNull(requestingUser);
		Assert.notNull(userIdentifier);
		User user = ensureUserIsLoaded(userIdentifier);
		if (!requestingUser.getId().equals(user.getUuid().toString())) {
			throw new AuthorizationException(
					"User not authorized to load profile");
		}
		return new ExternalUser(user);
	}
	
	public AuthenticatedUserToken getToken(RefreshToken refToken,
			String userIdentifier){
		Assert.notNull(refToken);
		
		Assert.notNull(userIdentifier);
		User user = ensureUserIsLoaded(userIdentifier);
		
		if(user==null){
			throw new AuthenticationException();
		}
		String decryptedToken;
		try{
			decryptedToken = encryption.decrypt(refToken.getRefreshToken());	
		}catch(Exception ex){
			throw new InternalServerException();
		}
		
		String userid = decryptedToken.split(":")[0];
		String token = decryptedToken.split(":")[1];
		if(!userIdentifier.equalsIgnoreCase(userid)){
			throw new AuthorizationException(
					"User not authorized to load profile");
		}
		if (!user.getToken().getRefreshtoken().equalsIgnoreCase(token)) {
			throw new AuthorizationException(
					"User not authorized to load profile");
		}
		
		AuthenticatedUserToken updateAuthorizationToken = updateAuthorizationToken(user, null);
		return updateAuthorizationToken;
	}

	/*
	 * @Transactional public void deleteUser(ExternalUser userMakingRequest,
	 * String userId) { Assert.notNull(userMakingRequest);
	 * Assert.notNull(userId); User userToDelete = ensureUserIsLoaded(userId);
	 * if
	 * (userMakingRequest.getRole().equalsIgnoreCase(Role.administrator.toString
	 * ()) && (userToDelete.hasRole(Role.anonymous) ||
	 * userToDelete.hasRole(Role.authenticated))) {
	 * userRepository.delete(userToDelete); } else { throw new
	 * AuthorizationException(
	 * "User cannot be deleted. Only users with anonymous or authenticated role can be deleted."
	 * ); } }
	 */

	public ExternalUser saveUser(String userId, UpdateUserRequest request) {
		// validate(request);
		User user = ensureUserIsLoaded(userId);
		if (request.getFirstName() != null) {
			user.setFirstName(request.getFirstName());
		}
		if (request.getLastName() != null) {
			user.setLastName(request.getLastName());
		}
		if (request.getEmailAddress() != null) {
			if (!request.getEmailAddress().equals(user.getEmail())) {
				user.setEmail(request.getEmailAddress());
				// user.setVerified(false);
			}
		}
		try {
			userRepository.saveUser(user);
		} catch (GenericBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ExternalUser(user);
	}

	@Override
	public Token createAuthorizationToken(User user) {
		if (user.getToken() == null || user.getToken().hasAuthExpired()) {
			// Token token = createToken(user,
			// applicationConfig.getAuthorizationExpiryTimeInSeconds());
			Token token = createToken(user, 60);
			try {
				return userRepository.addToken(token);
			} catch (GenericBusinessException e) {
				return null;
			}
		}
		return user.getToken();
	}

	public AuthenticatedUserToken updateAuthorizationToken(User user,
			HttpHeaders headers) {

		Token token = user.getToken();
		updateToken(token, 500);
		try {
			Token newtoken = userRepository.saveToken(token);
			// String encoded = composeUnEncodedRequest(headers);
			String authToken = encryption.encrypt(user.getUuid() + ":"
					+ newtoken.getAuthtoken());
			String refToken = encryption.encrypt(user.getUuid() + ":"
					+ newtoken.getRefreshtoken());
			return new AuthenticatedUserToken(user.getUuid(), authToken,refToken);

		} catch (Exception e) {
			throw new AuthenticationException();
		}

	}

	public AuthenticatedUserToken socialLogin(Connection<?> connection) {

		return null;
	}

	private Token createToken(User user, int authorizationExpiryTimeInSeconds) {
		Token token = new Token();
		token.setAuthtoken(UUID.randomUUID().toString());
		token.setRefreshtoken(UUID.randomUUID().toString());
		token.setUser(user);
		long time = System.currentTimeMillis();
		token.setAuthcreation(new Timestamp(time));
		token.setAuthexpiration(new Timestamp(time
				+ (authorizationExpiryTimeInSeconds * 1000L)));
		token.setRefreshexpiration(new Timestamp(time
				+ (20 *60 * 1000L)));
		return token;
	}

	private void updateToken(Token token, int authorizationExpiryTimeInSeconds) {

		token.setAuthtoken(UUID.randomUUID().toString());

		long time = System.currentTimeMillis();
		token.setAuthcreation(new Timestamp(time));
		token.setAuthexpiration(new Timestamp(time
				+ (authorizationExpiryTimeInSeconds * 1000L)));

	}

	private User createNewUser(CreateUserRequest request) {
		User userToSave = new User();
		userToSave.setEmail(request.getEmailAddress());
		userToSave.setFirstName(request.getFirstName());
		userToSave.setLastName(request.getLastName());
		userToSave.setGender(request.getGender());
		userToSave.setStatus("Active");

		try {
			userToSave.setPassword(userToSave.hashPassword(request
					.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userToSave;
	}

	public PaymentResponse createPaymentMethod(ExternalUser userMakingRequest,
			CreatePayment payment) {
		Payment savePaymentMethod = savePaymentMethod(payment);
		User user;
		try {
			user = userRepository.findUserByUuid(userMakingRequest.getId());
			savePaymentMethod.setUser(user);
			Payment addedPayment = paymentRepository
					.addPayment(savePaymentMethod);
			PaymentResponse paymentResponse = new PaymentResponse();
			paymentResponse.setId(addedPayment.getUuid());
			paymentResponse.setCompany(addedPayment.getCompany());
			//String number = encryption.decrypt(addedPayment
			//		.getCreditCardNumber());
			//paymentResponse
				//	.setLast4digit(number.substring(number.length() - 4));
			return paymentResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;

	}

	private Payment savePaymentMethod(CreatePayment payment) {

		try {
			Payment paymentTosave = new Payment();
			/*paymentTosave.setCreditCardNumber(encryption.encrypt(payment
					.getCreditcardnumber()));
			paymentTosave.setCreditHolderName(encryption.encrypt(payment
					.getCreditholdername()));
			paymentTosave.setCompany(payment.getCompany());
			paymentTosave.setExpiration(DateUtil.getDateASPattern(
					payment.getExpiration(), DateUtil.DATEPATTERN));
			paymentTosave.setUuid(UUID.randomUUID().toString());
			paymentTosave.setSecret(encryption.encrypt(payment.getSecret()));
*/
			return paymentTosave;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PurchasesResponse makePurchases(ExternalUser userMakingRequest,
			CreatePurchase purchaseRequest) {
		try {
			Purchases purchase = new Purchases();
			User user = userRepository
					.findUserByUuid(userMakingRequest.getId());
			purchase.setUser(user);
			purchase.setShop(shopRepository.getShop(purchaseRequest.getShopid()));
			purchase.setAmount(purchaseRequest.getAmount());
			purchase.setBarcode(UUID.randomUUID().toString().substring(15));

			purchase.setDate(new Timestamp(System.currentTimeMillis()));
			Payment payment = paymentRepository
					.findPaymentByUuid(purchaseRequest.getPaymentid());
			purchase.setPayment(payment);
			purchase.setStatus("Success");
			Purchases addPurchases = purchaseRepository.addPurchases(purchase);
			List<Purchasedetails> purchaseDetailsList = getPurchaseDetailsList(
					purchaseRequest, addPurchases);

			for (Purchasedetails purchasedetails : purchaseDetailsList) {

				purchaseDetailsRepository.addPurchasedetails(purchasedetails);
			}

			PurchasesResponse purchaseResponse = new PurchasesResponse();
			purchaseResponse
					.setAmount(String.valueOf(addPurchases.getAmount()));
			purchaseResponse.setDate(DateUtil
					.getDateDateAsIso8061String(new DateTime(purchase.getDate()
							.getTime())));
			purchaseResponse.setPurchaseid(addPurchases.getPurchaseId()
					.toString());
			purchaseResponse.setBarcode(addPurchases.getBarcode());
			return purchaseResponse;
		} catch (Exception ex) {

		}

		return null;
	}

	public PurchasesResponse getPurchases(ExternalUser userMakingRequest,
			long purchaseId) {
		try {
			Purchases purchases = purchaseRepository.getPurchases(purchaseId);
			PurchasesResponse purchasesResponse = new PurchasesResponse();

			PurchasesResponse purchaseResponse = new PurchasesResponse();
			purchaseResponse.setAmount(String.valueOf(purchases.getAmount()));
			purchaseResponse.setDate(DateUtil
					.getDateDateAsIso8061String(new DateTime(purchases
							.getDate().getTime())));
			purchaseResponse
					.setPurchaseid(purchases.getPurchaseId().toString());
			purchaseResponse.setBarcode(purchases.getBarcode());
			purchaseResponse.setShopname(purchases.getShop().getName());
			purchaseResponse.setShopAddress(purchases.getShop().getAddress());

			return purchaseResponse;
		} catch (Exception ex) {
			return null;
		}

	}

	public PurchasesWithDetailsResponse getPurchasesDetails(
			ExternalUser userMakingRequest, long purchaseId) {
		try {
			Purchases purchases = purchaseRepository.getPurchases(purchaseId);

			PurchasesWithDetailsResponse purchaseResponse = new PurchasesWithDetailsResponse();
			purchaseResponse.setAmount(String.valueOf(purchases.getAmount()));
			purchaseResponse.setDate(DateUtil
					.getDateDateAsIso8061String(new DateTime(purchases
							.getDate().getTime())));
			purchaseResponse
					.setPurchaseid(purchases.getPurchaseId().toString());
			purchaseResponse.setBarcode(purchases.getBarcode());
			purchaseResponse.setShopname(purchases.getShop().getName());
			purchaseResponse.setShopAddress(purchases.getShop().getAddress());

			purchaseResponse.setDetailsList(getPurchaseDetailsList(purchases));

			return purchaseResponse;
		} catch (Exception ex) {
			return null;
		}

	}

	/*
	 * private void updateUserFromProfile(Connection<?> connection, User user) {
	 * UserProfile profile = connection.fetchUserProfile();
	 * user.setEmailAddress(profile.getEmail());
	 * user.setFirstName(profile.getFirstName());
	 * user.setLastName(profile.getLastName()); //users logging in from social
	 * network are already verified user.setVerified(true);
	 * if(user.hasRole(Role.anonymous)) { user.setRole(Role.authenticated); }
	 * userRepository.save(user); }
	 */

	private List<Purchasedetails> getPurchaseDetailsList(
			CreatePurchase purchaseRequest, Purchases addPurchases)
			throws Exception {
		List<CreatePurchaseDetails> purchaseDetails = purchaseRequest
				.getPurchaseDetails();
		List<Purchasedetails> details = new ArrayList<Purchasedetails>();
		for (CreatePurchaseDetails createPurchaseDetails : purchaseDetails) {

			Purchasedetails pDetails = new Purchasedetails();
			pDetails.setQuantity(createPurchaseDetails.getQuantity());
			pDetails.setProduct(productRepository
					.getProduct(createPurchaseDetails.getProductid()));
			pDetails.setPurchases(addPurchases);
			details.add(pDetails);
		}

		return details;
	}

	private List<PurchaseDetailsResponse> getPurchaseDetailsList(
			Purchases purchases) throws Exception {
		List<Purchasedetails> purchaseDetails = purchases.getPurchasedetails();
		List<PurchaseDetailsResponse> details = new ArrayList<PurchaseDetailsResponse>();
		for (Purchasedetails purchaseDetail : purchaseDetails) {

			PurchaseDetailsResponse pDetails = new PurchaseDetailsResponse();
			pDetails.setQuantity(purchaseDetail.getQuantity());
			pDetails.setName(purchaseDetail.getProduct().getName());
			pDetails.setUnitprice(purchaseDetail.getProduct().getUnitPrice()
					.doubleValue());
			pDetails.setDescription(purchaseDetail.getProduct()
					.getDescription());
			details.add(pDetails);
		}

		return details;
	}

	private User ensureUserIsLoaded(String userIdentifier) {
		User user = null;
		if (com.skip.util.StringUtil.isValidUuid(userIdentifier)) {
			try {
				user = userRepository.findUserByUuid(userIdentifier);
			} catch (GenericBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				user = userRepository.findUserByEmail(userIdentifier);
			} catch (GenericBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public void deleteUser(ExternalUser userMakingRequest, String userId) {
		// TODO Auto-generated method stub

	}

	/**
	 * Encode the token by prefixing it with the User's Session Token
	 *
	 * @param token
	 * @return encoded token
	 */
	private String encodeAuthToken(String token, String unencodedRequest) {
		byte[] digest = DigestUtils.sha256(token + ":" + unencodedRequest);
		return new String(Base64.encodeBase64(digest));

	}

	/**
	 * The recipe to compose a signed request
	 *
	 * @param authRequest
	 * @return the string value to hash
	 */
	private String composeUnEncodedRequest(HttpHeaders headers) {
		StringBuilder sb = new StringBuilder();
		sb.append(headers.getRequestHeaders().get("nonce"));
		sb.append(',').append(headers.getRequestHeaders().get("skip-date"));
		return sb.toString();

	}

}
