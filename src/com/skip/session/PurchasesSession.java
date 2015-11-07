package com.skip.session;

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


import com.skip.entity.purchases.*;

/**
 * The <code>PurchasesSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class PurchasesSession implements IPurchasesSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(PurchasesSession.class);


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
    * Adds a new purchases to the database.
    *
    * @param model a data object
    * @return Purchases a data object with the primary key
    */
   public com.skip.entity.purchases.Purchases addPurchases(com.skip.entity.purchases.Purchases model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getPurchases(model.getPurchaseId());
      } finally {
         log.debug("finished addPurchases(com.skip.entity.purchases.Purchases model)");
      }
   }

   /**
    * Stores the <code>Purchases</code> in the database.
    *
    * @param model the data model to store
    */
   public void savePurchases(com.skip.entity.purchases.Purchases model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished savePurchases(com.skip.entity.purchases.Purchases model)");
      }
   }

   /**
    * Removes a purchases.
    *
    * @param id the unique reference for the purchases
    */
   public void deletePurchases(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Purchases bean = (Purchases) hibernateTemplate.get(Purchases.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deletePurchases(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Purchases the data object
    */
   public com.skip.entity.purchases.Purchases getPurchases(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Purchases bean = (Purchases) hibernateTemplate.get(Purchases.class, id);
      return bean;
      } finally {
         log.debug("finished getPurchases(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all purchases instances.
    *
    * @return a list of Purchases objects.
    */
   public List getPurchasesList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Purchases.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.purchaseid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getPurchasesList");
      }
   }

   /**
    * Returns a subset of all purchases instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of purchases instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPurchasesListSize()</code> = last record),
    * any values greater than or equal to the total number of purchases instances will cause
    * the full set to be returned.
    * @return a list of Purchases objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getPurchasesList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getPurchasesList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of purchases objects in the database.
    *
    * @return an integer value.
    */
   public int getPurchasesListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Purchases.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getPurchasesListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified purchaseid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param purchaseid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByPurchaseid(java.lang.Long purchaseid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.purchaseid like :purchaseid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "purchaseid", purchaseid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByPurchaseid(java.lang.Long purchaseid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param userid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByUserid(java.lang.Long userid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.userid like :userid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "userid", userid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByUserid(java.lang.Long userid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified paymentid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param paymentid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByPaymentid(java.lang.Long paymentid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.paymentid like :paymentid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "paymentid", paymentid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByPaymentid(java.lang.Long paymentid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param shopid the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByShopid(java.lang.Long shopid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.shopid like :shopid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "shopid", shopid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByShopid(java.lang.Long shopid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified date field.
     * To use a wildcard search, use a % in the query.
     *
     * @param date the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByDate(java.sql.Timestamp date) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.date like :date ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "date", date);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByDate(java.sql.Timestamp date)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified amount field.
     * To use a wildcard search, use a % in the query.
     *
     * @param amount the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByAmount(java.lang.Double amount) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.amount like :amount ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "amount", amount);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByAmount(java.lang.Double amount)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified status field.
     * To use a wildcard search, use a % in the query.
     *
     * @param status the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByStatus(java.lang.String status) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.status like :status ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "status", status);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByStatus(java.lang.String status)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified barcode field.
     * To use a wildcard search, use a % in the query.
     *
     * @param barcode the field
     * @return List of Purchases data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasesByBarcode(java.lang.String barcode) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchases.class.getName() + " e where e.barcode like :barcode ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchaseid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "barcode", barcode);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasesByBarcode(java.lang.String barcode)");
      }
    }


}
