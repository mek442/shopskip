package com.skip.session;

import java.math.BigDecimal;

import com.skip.entity.shop.Shop;
import com.skip.exception.GenericBusinessException;

/**
 * The <code>ShopSessionService</code> bean exposes the business methods in the interface.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.11 $, $Date: 2006/04/29 12:39:09 $
 */
public interface IShopSession {

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/

   /**
    * Adds a new shop to the storage.
    *
    * @param model a data object
    * @return Shop a data object with the primary key
    */
   com.skip.entity.shop.Shop addShop(com.skip.entity.shop.Shop model) throws com.skip.exception.GenericBusinessException;

   /**
    * Stores the <code>Shop</code>.
    *
    * @param model the data model to store
    */
   void saveShop(com.skip.entity.shop.Shop model) throws com.skip.exception.GenericBusinessException;

   /**
    * Removes a shop.
    *
    * @param id the unique reference for the shop
    */
   void deleteShop(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Retrieves a data object from the storage by its primary key.
    *
    * @param id the unique reference
    * @return Shop the data object
    */
   com.skip.entity.shop.Shop getShop(java.lang.Long id) throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a list of all shop instances.
    *
    * @return a list of Shop objects.
    */
   java.util.List<com.skip.entity.shop.Shop> getShopList() throws com.skip.exception.GenericBusinessException;

   /**
    * Returns a subset of all shop instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of shop instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getShopListSize()</code> = last record),
    * any values greater than or equal to the total number of shop instances will cause
    * the full set to be returned.
    * @return a collection of Shop objects, of size <code>(endIndex - startIndex)</code>.
    * throws GenericBusinessException if the chosen underlying data-retrieval mechanism does not support returning partial result sets.
    */
   java.util.List<com.skip.entity.shop.Shop> getShopList(int startIndex, int endIndex) throws com.skip.exception.GenericBusinessException;

   /**
    * Obtains the total number of shop objects in the storage.
    * <b>NOTE:</b>If this session façade is configured <b>not</b> to use the FastLaneReader,
    * this call will be very computationally wasteful as it will first have to retrieve all entities from
    * the entity bean's <code>findAll</code> method.
    *
    * @return an integer value.
    */
   int getShopListSize() throws com.skip.exception.GenericBusinessException;

    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     *
     * @param shopid the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByShopid(java.lang.Long shopid) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified name field.
     *
     * @param name the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByName(java.lang.String name) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified address field.
     *
     * @param address the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByAddress(java.lang.String address) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified apikey field.
     *
     * @param apikey the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByApikey(java.lang.String apikey) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     *
     * @param secret the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopBySecret(java.lang.String secret) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified accessurl field.
     *
     * @param accessurl the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByAccessurl(java.lang.String accessurl) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified latitude field.
     *
     * @param latitude the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByLatitude(java.lang.String latitude) throws com.skip.exception.GenericBusinessException;
    /**
     *
     * Retrieves a list of data object for the specified longitude field.
     *
     * @param longitude the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    java.util.List<com.skip.entity.shop.Shop> findShopByLongitude(java.lang.String longitude) throws com.skip.exception.GenericBusinessException;

    java.util.List<com.skip.entity.shop.Shop> findShopByLongitudeAndLatitude(BigDecimal bigDecimal,
			BigDecimal bigDecimal2) throws GenericBusinessException;


}
