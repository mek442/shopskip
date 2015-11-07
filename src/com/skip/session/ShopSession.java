package com.skip.session;

import java.math.BigDecimal;
import java.util.*;

import com.skip.*;
import com.skip.servicelocator.LocatableService;
import com.skip.util.log.LogService;
import com.skip.util.log.Logger;
import com.skip.exception.GenericBusinessException;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



import com.skip.entity.shop.*;

/**
 * The <code>ShopSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class ShopSession implements IShopSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(ShopSession.class);


   /**
    * Default implementation for the Locatable Service interface.
    */
    public void init() {
    }

   /**
    * Default implementation for the Locatable Service interface.
    */
    public void destroy() {
    }

   /*******************************************************************************************************************
    *                                  B U S I N E S S   M E T H O D S
    *******************************************************************************************************************/


   /*******************************************************************************************************************
    *                                  P E R S I S T E N C E    M E T H O D S
    *******************************************************************************************************************/



   /**
    * Adds a new shop to the database.
    *
    * @param model a data object
    * @return Shop a data object with the primary key
    */
   public com.skip.entity.shop.Shop addShop(com.skip.entity.shop.Shop model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getShop(model.getShopid());
      } finally {
         log.debug("finished addShop(com.skip.entity.shop.Shop model)");
      }
   }

   /**
    * Stores the <code>Shop</code> in the database.
    *
    * @param model the data model to store
    */
   public void saveShop(com.skip.entity.shop.Shop model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished saveShop(com.skip.entity.shop.Shop model)");
      }
   }

   /**
    * Removes a shop.
    *
    * @param id the unique reference for the shop
    */
   public void deleteShop(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Shop bean = (Shop) hibernateTemplate.get(Shop.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deleteShop(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Shop the data object
    */
   public com.skip.entity.shop.Shop getShop(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Shop bean = (Shop) hibernateTemplate.get(Shop.class, id);
      return bean;
      } finally {
         log.debug("finished getShop(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all shop instances.
    *
    * @return a list of Shop objects.
    */
   public List getShopList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Shop.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.shopid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getShopList");
      }
   }

   /**
    * Returns a subset of all shop instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of shop instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getShopListSize()</code> = last record),
    * any values greater than or equal to the total number of shop instances will cause
    * the full set to be returned.
    * @return a list of Shop objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getShopList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getShopList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of shop objects in the database.
    *
    * @return an integer value.
    */
   public int getShopListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Shop.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getShopListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param shopid the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByShopid(java.lang.Long shopid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.shopid like :shopid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "shopid", shopid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByShopid(java.lang.Long shopid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified name field.
     * To use a wildcard search, use a % in the query.
     *
     * @param name the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByName(java.lang.String name) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.name like :name ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "name", name);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByName(java.lang.String name)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified address field.
     * To use a wildcard search, use a % in the query.
     *
     * @param address the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByAddress(java.lang.String address) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.address like :address ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "address", address);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByAddress(java.lang.String address)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified apikey field.
     * To use a wildcard search, use a % in the query.
     *
     * @param apikey the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByApikey(java.lang.String apikey) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.apikey like :apikey ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "apikey", apikey);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByApikey(java.lang.String apikey)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     * To use a wildcard search, use a % in the query.
     *
     * @param secret the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopBySecret(java.lang.String secret) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.secret like :secret ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "secret", secret);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopBySecret(java.lang.String secret)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified accessurl field.
     * To use a wildcard search, use a % in the query.
     *
     * @param accessurl the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByAccessurl(java.lang.String accessurl) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.accessurl like :accessurl ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "accessurl", accessurl);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByAccessurl(java.lang.String accessurl)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified latitude field.
     * To use a wildcard search, use a % in the query.
     *
     * @param latitude the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByLatitude(java.lang.String latitude) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.latitude like :latitude ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "latitude", latitude);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByLatitude(java.lang.String latitude)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified longitude field.
     * To use a wildcard search, use a % in the query.
     *
     * @param longitude the field
     * @return List of Shop data objects, empty list in case no results were found.
     */
    public java.util.List findShopByLongitude(java.lang.String longitude) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Shop.class.getName() + " e where e.longitude like :longitude ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.shopid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "longitude", longitude);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findShopByLongitude(java.lang.String longitude)");
      }
    }

   public List<com.skip.entity.shop.Shop> findShopByLongitudeAndLatitude(BigDecimal lat,
			BigDecimal lon) throws GenericBusinessException{
	   com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
    	 try {
             String queryString = "from " + Shop.class.getName() + " e where  ( 111.045 * acos( cos( radians(:latitude) ) * cos( radians( e.latitude ) ) * cos( radians( e.longitude ) - radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( e.latitude ) ) ) )*1000 <= 250 ";
             // Add a an order by on all primary keys to assure reproducable results.
            
             Query query = hibernateTemplate.createQuery(queryString);
             hibernateTemplate.setQueryParameter(query, "longitude", lon);
             hibernateTemplate.setQueryParameter(query, "latitude", lat);
             List list = hibernateTemplate.list(query);
             return list;
          } finally {
             log.debug("finished findShopByLongitudeAndLatitude(java.lang.String longitude)");
          }
    }

}
