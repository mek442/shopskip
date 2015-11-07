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



import com.skip.entity.payment.*;

/**
 * The <code>PaymentSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class PaymentSession implements IPaymentSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(PaymentSession.class);


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
    * Adds a new payment to the database.
    *
    * @param model a data object
    * @return Payment a data object with the primary key
    */
   public com.skip.entity.payment.Payment addPayment(com.skip.entity.payment.Payment model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getPayment(model.getPaymentid());
      } finally {
         log.debug("finished addPayment(com.skip.entity.payment.Payment model)");
      }
   }

   /**
    * Stores the <code>Payment</code> in the database.
    *
    * @param model the data model to store
    */
   public void savePayment(com.skip.entity.payment.Payment model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished savePayment(com.skip.entity.payment.Payment model)");
      }
   }

   /**
    * Removes a payment.
    *
    * @param id the unique reference for the payment
    */
   public void deletePayment(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Payment bean = (Payment) hibernateTemplate.get(Payment.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deletePayment(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Payment the data object
    */
   public com.skip.entity.payment.Payment getPayment(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Payment bean = (Payment) hibernateTemplate.get(Payment.class, id);
      return bean;
      } finally {
         log.debug("finished getPayment(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all payment instances.
    *
    * @return a list of Payment objects.
    */
   public List getPaymentList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Payment.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.paymentid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getPaymentList");
      }
   }

   /**
    * Returns a subset of all payment instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of payment instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getPaymentListSize()</code> = last record),
    * any values greater than or equal to the total number of payment instances will cause
    * the full set to be returned.
    * @return a list of Payment objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getPaymentList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getPaymentList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of payment objects in the database.
    *
    * @return an integer value.
    */
   public int getPaymentListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Payment.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getPaymentListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified paymentid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param paymentid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByPaymentid(java.lang.Long paymentid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.paymentid like :paymentid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "paymentid", paymentid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByPaymentid(java.lang.Long paymentid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param userid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByUserid(java.lang.Long userid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.userid like :userid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "userid", userid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByUserid(java.lang.Long userid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified creditholdername field.
     * To use a wildcard search, use a % in the query.
     *
     * @param creditholdername the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByCreditholdername(java.lang.String creditholdername) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.creditholdername like :creditholdername ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "creditholdername", creditholdername);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByCreditholdername(java.lang.String creditholdername)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified creditcardnumber field.
     * To use a wildcard search, use a % in the query.
     *
     * @param creditcardnumber the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByCreditcardnumber(java.lang.String creditcardnumber) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.creditcardnumber like :creditcardnumber ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "creditcardnumber", creditcardnumber);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByCreditcardnumber(java.lang.String creditcardnumber)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified expiration field.
     * To use a wildcard search, use a % in the query.
     *
     * @param expiration the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByExpiration(java.sql.Date expiration) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.expiration like :expiration ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "expiration", expiration);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByExpiration(java.sql.Date expiration)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified company field.
     * To use a wildcard search, use a % in the query.
     *
     * @param company the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentByCompany(java.lang.String company) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.company like :company ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "company", company);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentByCompany(java.lang.String company)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     * To use a wildcard search, use a % in the query.
     *
     * @param secret the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public java.util.List findPaymentBySecret(java.lang.String secret) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.secret like :secret ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "secret", secret);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findPaymentBySecret(java.lang.String secret)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified uuid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param uuid the field
     * @return List of Payment data objects, empty list in case no results were found.
     */
    public Payment findPaymentByUuid(java.lang.String uuid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Payment.class.getName() + " e where e.uuid like :uuid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.paymentid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "uuid", uuid);
         List list = hibernateTemplate.list(query);
         if(!list.isEmpty()){
        	return (Payment) list.get(0) ;
         }
        
      } finally {
         log.debug("finished findPaymentByUuid(java.lang.String uuid)");
      }
      return null;
    }


}
