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


import com.skip.entity.purchasedetails.*;

/**
 * The <code>PurchaseDetailsSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class PurchaseDetailsSession implements IPurchaseDetailsSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(PurchaseDetailsSession.class);


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
    * Adds a new purchasedetails to the database.
    *
    * @param model a data object
    * @return Purchasedetails a data object with the primary key
    */
   public com.skip.entity.purchasedetails.Purchasedetails addPurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getPurchasedetails(model.getPurchasedetailsid());
      } finally {
         log.debug("finished addPurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model)");
      }
   }

   /**
    * Stores the <code>Purchasedetails</code> in the database.
    *
    * @param model the data model to store
    */
   public void savePurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished savePurchasedetails(com.skip.entity.purchasedetails.Purchasedetails model)");
      }
   }

   /**
    * Removes a purchasedetails.
    *
    * @param id the unique reference for the purchasedetails
    */
   public void deletePurchasedetails(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Purchasedetails bean = (Purchasedetails) hibernateTemplate.get(Purchasedetails.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deletePurchasedetails(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Purchasedetails the data object
    */
   public com.skip.entity.purchasedetails.Purchasedetails getPurchasedetails(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Purchasedetails bean = (Purchasedetails) hibernateTemplate.get(Purchasedetails.class, id);
      return bean;
      } finally {
         log.debug("finished getPurchasedetails(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all purchasedetails instances.
    *
    * @return a list of Purchasedetails objects.
    */
   public List getPurchasedetailsList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Purchasedetails.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.purchasedetailsid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getPurchasedetailsList");
      }
   }

   /**
    * Returns a subset of all purchasedetails instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of purchasedetails instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPurchasedetailsListSize()</code> = last record),
    * any values greater than or equal to the total number of purchasedetails instances will cause
    * the full set to be returned.
    * @return a list of Purchasedetails objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getPurchasedetailsList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchasedetails.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchasedetailsid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getPurchasedetailsList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of purchasedetails objects in the database.
    *
    * @return an integer value.
    */
   public int getPurchasedetailsListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Purchasedetails.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getPurchasedetailsListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified purchasedetailsid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param purchasedetailsid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasedetailsByPurchasedetailsid(java.lang.Long purchasedetailsid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchasedetails.class.getName() + " e where e.purchasedetailsid like :purchasedetailsid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchasedetailsid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "purchasedetailsid", purchasedetailsid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasedetailsByPurchasedetailsid(java.lang.Long purchasedetailsid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified purchaseid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param purchaseid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasedetailsByPurchaseid(java.lang.Long purchaseid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchasedetails.class.getName() + " e where e.purchaseid like :purchaseid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchasedetailsid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "purchaseid", purchaseid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasedetailsByPurchaseid(java.lang.Long purchaseid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified productid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param productid the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasedetailsByProductid(java.lang.Long productid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchasedetails.class.getName() + " e where e.productid like :productid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchasedetailsid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "productid", productid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasedetailsByProductid(java.lang.Long productid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified quantity field.
     * To use a wildcard search, use a % in the query.
     *
     * @param quantity the field
     * @return List of Purchasedetails data objects, empty list in case no results were found.
     */
    public java.util.List findPurchasedetailsByQuantity(java.lang.Double quantity) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Purchasedetails.class.getName() + " e where e.quantity like :quantity ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.purchasedetailsid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "quantity", quantity);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPurchasedetailsByQuantity(java.lang.Double quantity)");
      }
    }


}
