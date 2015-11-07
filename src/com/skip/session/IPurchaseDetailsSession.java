package com.skip.session;

/**
 * The <code>PurchaseDetailsSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IPurchaseDetailsSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new purchasedetails to the storage.
    *
    * @param model a data object
    * @return Purchasedetails a data object with the primary key
    */
   com.skip.entity.purchasedetails.Purchasedetails addPurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Purchasedetails</code>.
    *
    * @param model the data model to store
    */
   void savePurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a purchasedetails.
    *
    * @param id the unique reference for the purchasedetails
    */
   void deletePurchasedetails(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Purchasedetails the data object
    */
   com.skip.entity.purchasedetails.Purchasedetails getPurchasedetails(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all purchasedetails instances.
    *
    * @return a list of Purchasedetails objects.
    */
   java.util.List<com.skip.entity.purchasedetails.Purchasedetails> getPurchasedetailsList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all purchasedetails instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of purchasedetails instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPurchasedetailsListSize()</code> = last record),
    * any values greater than or equal to the total number of purchasedetails instances will cause
    * the full set to be returned.
    * @return a collection of Purchasedetails objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.purchasedetails.Purchasedetails> getPurchasedetailsList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of purchasedetails objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getPurchasedetailsListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified purchasedetailsid field.
     *
     * @param purchasedetailsid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchasedetails.Purchasedetails> findPurchasedetailsByPurchasedetailsid(java.lang.Long purchasedetailsid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified purchaseid field.
     *
     * @param purchaseid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchasedetails.Purchasedetails> findPurchasedetailsByPurchaseid(java.lang.Long purchaseid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified productid field.
     *
     * @param productid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchasedetails.Purchasedetails> findPurchasedetailsByProductid(java.lang.Long productid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified quantity field.
     *
     * @param quantity the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.purchasedetails.Purchasedetails> findPurchasedetailsByQuantity(java.lang.Double quantity) throws com.skip.exception.GenericBusinessException;


}
