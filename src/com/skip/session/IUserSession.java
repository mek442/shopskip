package com.skip.session;

import com.skip.exception.GenericBusinessException;

/**
 * The <code>UserSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IUserSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new user to the storage.
    *
    * @param model a data object
    * @return User a data object with the primary key
    */
   com.skip.entity.user.User addUser(com.skip.entity.user.User model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>User</code>.
    *
    * @param model the data model to store
    */
   void saveUser(com.skip.entity.user.User model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a user.
    *
    * @param id the unique reference for the user
    */
   void deleteUser(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return User the data object
    */
   com.skip.entity.user.User getUser(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all user instances.
    *
    * @return a list of User objects.
    */
   java.util.List<com.skip.entity.user.User> getUserList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all user instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of user instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getUserListSize()</code> = last record),
    * any values greater than or equal to the total number of user instances will cause
    * the full set to be returned.
    * @return a collection of User objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.user.User> getUserList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of user objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getUserListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified userid field.
     *
     * @param userid the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByUserid(java.lang.Long userid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified firstname field.
     *
     * @param firstname the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByFirstname(java.lang.String firstname) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified lastname field.
     *
     * @param lastname the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByLastname(java.lang.String lastname) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified email field.
     *
     * @param email the field
     * @return List of User data objects, empty list in case no results were found.
     */
    com.skip.entity.user.User findUserByEmail(java.lang.String email) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified password field.
     *
     * @param password the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByPassword(java.lang.String password) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified mobile field.
     *
     * @param mobile the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByMobile(java.lang.String mobile) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified gender field.
     *
     * @param gender the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByGender(java.lang.String gender) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified status field.
     *
     * @param status the field
     * @return List of User data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.user.User> findUserByStatus(java.lang.String status) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified uuid field.
     *
     * @param uuid the field
     * @return List of User data objects, empty list in case no results were found.
     */
    com.skip.entity.user.User findUserByUuid(java.lang.String uuid) throws com.skip.exception.GenericBusinessException;


    public com.skip.entity.token.Token addToken(com.skip.entity.token.Token model) throws GenericBusinessException; 
       /**
        * Stores the <code>Token</code> in the database.
        *
        * @param model the data model to store
        */
    public com.skip.entity.token.Token saveToken(com.skip.entity.token.Token model) throws GenericBusinessException;
}
