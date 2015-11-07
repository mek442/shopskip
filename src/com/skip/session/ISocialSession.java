package com.skip.session;

/**
 * The <code>SocialSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface ISocialSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new social to the storage.
    *
    * @param model a data object
    * @return Social a data object with the primary key
    */
   com.skip.entity.social.Social addSocial(com.skip.entity.social.Social model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Social</code>.
    *
    * @param model the data model to store
    */
   void saveSocial(com.skip.entity.social.Social model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a social.
    *
    * @param id the unique reference for the social
    */
   void deleteSocial(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Social the data object
    */
   com.skip.entity.social.Social getSocial(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all social instances.
    *
    * @return a list of Social objects.
    */
   java.util.List<com.skip.entity.social.Social> getSocialList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all social instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of social instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getSocialListSize()</code> = last record),
    * any values greater than or equal to the total number of social instances will cause
    * the full set to be returned.
    * @return a collection of Social objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.social.Social> getSocialList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of social objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getSocialListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified socialid field.
     *
     * @param socialid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialBySocialid(java.lang.Long socialid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified providerid field.
     *
     * @param providerid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByProviderid(java.lang.String providerid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified provideruserid field.
     *
     * @param provideruserid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByProvideruserid(java.lang.String provideruserid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified accesstoken field.
     *
     * @param accesstoken the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByAccesstoken(java.lang.String accesstoken) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     *
     * @param secret the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialBySecret(java.lang.String secret) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified refreshtoken field.
     *
     * @param refreshtoken the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByRefreshtoken(java.lang.String refreshtoken) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified expirationtime field.
     *
     * @param expirationtime the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByExpirationtime(java.sql.Timestamp expirationtime) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     *
     * @param userid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.social.Social> findSocialByUserid(java.lang.Long userid) throws com.skip.exception.GenericBusinessException;


}
