package com.skip.session;

import com.skip.entity.product.Product;

/**
 * The <code>ProductSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IProductSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new product to the storage.
    *
    * @param model a data object
    * @return Product a data object with the primary key
    */
   com.skip.entity.product.Product addProduct(com.skip.entity.product.Product model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Product</code>.
    *
    * @param model the data model to store
    */
   void saveProduct(com.skip.entity.product.Product model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a product.
    *
    * @param id the unique reference for the product
    */
   void deleteProduct(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Product the data object
    */
   com.skip.entity.product.Product getProduct(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all product instances.
    *
    * @return a list of Product objects.
    */
   java.util.List<com.skip.entity.product.Product> getProductList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all product instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of product instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getProductListSize()</code> = last record),
    * any values greater than or equal to the total number of product instances will cause
    * the full set to be returned.
    * @return a collection of Product objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.product.Product> getProductList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of product objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getProductListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified productid field.
     *
     * @param productid the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByProductid(java.lang.Long productid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified name field.
     *
     * @param name the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByName(java.lang.String name) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified description field.
     *
     * @param description the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByDescription(java.lang.String description) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified barcode field.
     *
     * @param barcode the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByBarcode(java.lang.String barcode) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified unitprice field.
     *
     * @param unitprice the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByUnitprice(java.math.BigDecimal unitprice) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified type field.
     *
     * @param type the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByType(java.lang.String type) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified manufacturer field.
     *
     * @param manufacturer the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByManufacturer(java.lang.String manufacturer) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     *
     * @param shopid the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.product.Product> findProductByShopid(java.lang.Long shopid) throws com.skip.exception.GenericBusinessException;

	Product findProductByShopidAndBarcode(Long shopId, String barcode)throws com.skip.exception.GenericBusinessException;;


}
