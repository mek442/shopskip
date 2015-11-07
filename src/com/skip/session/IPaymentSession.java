package com.skip.session;

/**
 * The <code>PaymentSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IPaymentSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new payment to the storage.
    *
    * @param model a data object
    * @return Payment a data object with the primary key
    */
   com.skip.entity.payment.Payment addPayment(com.skip.entity.payment.Payment model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Payment</code>.
    *
    * @param model the data model to store
    */
   void savePayment(com.skip.entity.payment.Payment model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a payment.
    *
    * @param id the unique reference for the payment
    */
   void deletePayment(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Payment the data object
    */
   com.skip.entity.payment.Payment getPayment(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all payment instances.
    *
    * @return a list of Payment objects.
    */
   java.util.List<com.skip.entity.payment.Payment> getPaymentList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all payment instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of payment instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPaymentListSize()</code> = last record),
    * any values greater than or equal to the total number of payment instances will cause
    * the full set to be returned.
    * @return a collection of Payment objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.payment.Payment> getPaymentList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of payment objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getPaymentListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified paymentid field.
     *
     * @param paymentid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByPaymentid(java.lang.Long paymentid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     *
     * @param userid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByUserid(java.lang.Long userid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified creditholdername field.
     *
     * @param creditholdername the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByCreditholdername(java.lang.String creditholdername) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified creditcardnumber field.
     *
     * @param creditcardnumber the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByCreditcardnumber(java.lang.String creditcardnumber) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified expiration field.
     *
     * @param expiration the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByExpiration(java.sql.Date expiration) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified company field.
     *
     * @param company the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentByCompany(java.lang.String company) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     *
     * @param secret the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.payment.Payment> findPaymentBySecret(java.lang.String secret) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified uuid field.
     *
     * @param uuid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    com.skip.entity.payment.Payment findPaymentByUuid(java.lang.String uuid) throws com.skip.exception.GenericBusinessException;


}
