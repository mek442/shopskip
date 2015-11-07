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

import com.skip.entity.social.*;

/**
 * The <code>SocialSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class SocialSession implements ISocialSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(SocialSession.class);


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
    * Adds a new social to the database.
    *
    * @param model a data object
    * @return Social a data object with the primary key
    */
   public com.skip.entity.social.Social addSocial(com.skip.entity.social.Social model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getSocial(model.getSocialId());
      } finally {
         log.debug("finished addSocial(com.skip.entity.social.Social model)");
      }
   }

   /**
    * Stores the <code>Social</code> in the database.
    *
    * @param model the data model to store
    */
   public void saveSocial(com.skip.entity.social.Social model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished saveSocial(com.skip.entity.social.Social model)");
      }
   }

   /**
    * Removes a social.
    *
    * @param id the unique reference for the social
    */
   public void deleteSocial(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Social bean = (Social) hibernateTemplate.get(Social.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deleteSocial(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Social the data object
    */
   public com.skip.entity.social.Social getSocial(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Social bean = (Social) hibernateTemplate.get(Social.class, id);
      return bean;
      } finally {
         log.debug("finished getSocial(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all social instances.
    *
    * @return a list of Social objects.
    */
   public List getSocialList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Social.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.socialid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getSocialList");
      }
   }

   /**
    * Returns a subset of all social instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of social instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getSocialListSize()</code> = last record),
    * any values greater than or equal to the total number of social instances will cause
    * the full set to be returned.
    * @return a list of Social objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getSocialList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getSocialList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of social objects in the database.
    *
    * @return an integer value.
    */
   public int getSocialListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Social.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getSocialListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified socialid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param socialid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialBySocialid(java.lang.Long socialid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.socialid like :socialid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "socialid", socialid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialBySocialid(java.lang.Long socialid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified providerid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param providerid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByProviderid(java.lang.String providerid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.providerid like :providerid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "providerid", providerid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByProviderid(java.lang.String providerid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified provideruserid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param provideruserid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByProvideruserid(java.lang.String provideruserid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.provideruserid like :provideruserid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "provideruserid", provideruserid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByProvideruserid(java.lang.String provideruserid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified accesstoken field.
     * To use a wildcard search, use a % in the query.
     *
     * @param accesstoken the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByAccesstoken(java.lang.String accesstoken) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.accesstoken like :accesstoken ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "accesstoken", accesstoken);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByAccesstoken(java.lang.String accesstoken)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified secret field.
     * To use a wildcard search, use a % in the query.
     *
     * @param secret the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialBySecret(java.lang.String secret) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.secret like :secret ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "secret", secret);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialBySecret(java.lang.String secret)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified refreshtoken field.
     * To use a wildcard search, use a % in the query.
     *
     * @param refreshtoken the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByRefreshtoken(java.lang.String refreshtoken) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.refreshtoken like :refreshtoken ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "refreshtoken", refreshtoken);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByRefreshtoken(java.lang.String refreshtoken)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified expirationtime field.
     * To use a wildcard search, use a % in the query.
     *
     * @param expirationtime the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByExpirationtime(java.sql.Timestamp expirationtime) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.expirationtime like :expirationtime ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "expirationtime", expirationtime);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByExpirationtime(java.sql.Timestamp expirationtime)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param userid the field
     * @return List of Social data objects, empty list in case no results were found.
     */
    public java.util.List findSocialByUserid(java.lang.Long userid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Social.class.getName() + " e where e.userid like :userid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.socialid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "userid", userid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findSocialByUserid(java.lang.Long userid)");
      }
    }


}
