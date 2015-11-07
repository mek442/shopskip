package com.skip.session;

/**
 * The <code>PurchasesSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IPurchasesSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new purchases to the storage.
    *
    * @param model a data object
    * @return Purchases a data object with the primary key
    */
   com.skip.entity.purchases.Purchases addPurchases(com.skip.entity.purchases.Purchases model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Purchases</code>.
    *
    * @param model the data model to store
    */
   void savePurchases(com.skip.entity.purchases.Purchases model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a purchases.
    *
    * @param id the unique reference for the purchases
    */
   void deletePurchases(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Purchases the data object
    */
   com.skip.entity.purchases.Purchases getPurchases(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all purchases instances.
    *
    * @return a list of Purchases objects.
    */
   java.util.List<com.skip.entity.purchases.Purchases> getPurchasesList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all purchases instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of purchases instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPurchasesListSize()</code> = last record),
    * any values greater than or equal to the total number of purchases instances will cause
    * the full set to be returned.
    * @return a collection of Purchases objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.purchases.Purchases> getPurchasesList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of purchases objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getPurchasesListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified purchaseid field.
     *
     * @param purchaseid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByPurchaseid(java.lang.Long purchaseid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     *
     * @param userid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByUserid(java.lang.Long userid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified paymentid field.
     *
     * @param paymentid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByPaymentid(java.lang.Long paymentid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     *
     * @param shopid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByShopid(java.lang.Long shopid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified date field.
     *
     * @param date the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByDate(java.sql.Timestamp date) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified amount field.
     *
     * @param amount the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByAmount(java.lang.Double amount) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified status field.
     *
     * @param status the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByStatus(java.lang.String status) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified barcode field.
     *
     * @param barcode the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchases.Purchases> findPurchasesByBarcode(java.lang.String barcode) throws com.skip.exception.GenericBusinessException;


}
