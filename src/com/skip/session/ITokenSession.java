package com.skip.session;

/**
 * The <code>TokenSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface ITokenSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new token to the storage.
    *
    * @param model a data object
    * @return Token a data object with the primary key
    */
   com.skip.entity.token.Token addToken(com.skip.entity.token.Token model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Token</code>.
    *
    * @param model the data model to store
    */
   void saveToken(com.skip.entity.token.Token model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a token.
    *
    * @param id the unique reference for the token
    */
   void deleteToken(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Token the data object
    */
   com.skip.entity.token.Token getToken(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all token instances.
    *
    * @return a list of Token objects.
    */
   java.util.List<com.skip.entity.token.Token> getTokenList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all token instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of token instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getTokenListSize()</code> = last record),
    * any values greater than or equal to the total number of token instances will cause
    * the full set to be returned.
    * @return a collection of Token objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.token.Token> getTokenList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of token objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getTokenListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified tokenid field.
     *
     * @param tokenid the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByTokenid(java.lang.Long tokenid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified authtoken field.
     *
     * @param authtoken the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    com.skip.entity.token.Token findTokenByAuthtoken(java.lang.String authtoken) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     *
     * @param userid the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByUserid(java.lang.Long userid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified authcreation field.
     *
     * @param authcreation the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByAuthcreation(java.sql.Timestamp authcreation) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified authexpiration field.
     *
     * @param authexpiration the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByAuthexpiration(java.sql.Timestamp authexpiration) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified verificationtoken field.
     *
     * @param verificationtoken the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByVerificationtoken(java.lang.String verificationtoken) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified verexpiration field.
     *
     * @param verexpiration the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByVerexpiration(java.sql.Timestamp verexpiration) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified vercreation field.
     *
     * @param vercreation the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByVercreation(java.sql.Timestamp vercreation) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified vertype field.
     *
     * @param vertype the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.token.Token> findTokenByVertype(java.lang.String vertype) throws com.skip.exception.GenericBusinessException;


}
