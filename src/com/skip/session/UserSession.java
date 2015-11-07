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

import com.skip.entity.token.Token;
import com.skip.entity.user.*;

/**
 * The <code>UserSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class UserSession implements IUserSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(UserSession.class);

   private ITokenSession tokenSession = new TokenSession();

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
    * Adds a new user to the database.
    *
    * @param model a data object
    * @return User a data object with the primary key
    */
   public com.skip.entity.user.User addUser(com.skip.entity.user.User model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
			while (true) {
				String uuid = UUID.randomUUID().toString();
				User user = findUserByUuid(uuid);
				if (user == null) {
					model.setUuid(uuid);
					break;
				}
			}
         hibernateTemplate.save(model);
         return getUser(model.getUserId());
      } finally {
         log.debug("finished addUser(com.skip.entity.user.User model)");
      }
   }

   
   /**
    * Stores the <code>User</code> in the database.
    *
    * @param model the data model to store
    */
   public void saveUser(com.skip.entity.user.User model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished saveUser(com.skip.entity.user.User model)");
      }
   }

   /**
    * Removes a user.
    *
    * @param id the unique reference for the user
    */
   public void deleteUser(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         User bean = (User) hibernateTemplate.get(User.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deleteUser(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return User the data object
    */
   public com.skip.entity.user.User getUser(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      User bean = (User) hibernateTemplate.get(User.class, id);
      return bean;
      } finally {
         log.debug("finished getUser(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all user instances.
    *
    * @return a list of User objects.
    */
   public List getUserList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + User.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.userid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getUserList");
      }
   }

   /**
    * Returns a subset of all user instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of user instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getUserListSize()</code> = last record),
    * any values greater than or equal to the total number of user instances will cause
    * the full set to be returned.
    * @return a list of User objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getUserList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getUserList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of user objects in the database.
    *
    * @return an integer value.
    */
   public int getUserListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + User.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getUserListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified userid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param userid the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByUserid(java.lang.Long userid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.userid like :userid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "userid", userid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByUserid(java.lang.Long userid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified firstname field.
     * To use a wildcard search, use a % in the query.
     *
     * @param firstname the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByFirstname(java.lang.String firstname) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.firstname like :firstname ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "firstname", firstname);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByFirstname(java.lang.String firstname)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified lastname field.
     * To use a wildcard search, use a % in the query.
     *
     * @param lastname the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByLastname(java.lang.String lastname) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.lastname like :lastname ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "lastname", lastname);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByLastname(java.lang.String lastname)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified email field.
     * To use a wildcard search, use a % in the query.
     *
     * @param email the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public User findUserByEmail(java.lang.String email) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.email like :email ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "email", email);
         List list = hibernateTemplate.list(query);
         if(!list.isEmpty()){
        	 return (User) list.get(0);
         }
         return null;
      } finally {
         log.debug("finished findUserByEmail(java.lang.String email)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified password field.
     * To use a wildcard search, use a % in the query.
     *
     * @param password the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByPassword(java.lang.String password) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.password like :password ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "password", password);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByPassword(java.lang.String password)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified mobile field.
     * To use a wildcard search, use a % in the query.
     *
     * @param mobile the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByMobile(java.lang.String mobile) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.mobile like :mobile ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "mobile", mobile);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByMobile(java.lang.String mobile)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified gender field.
     * To use a wildcard search, use a % in the query.
     *
     * @param gender the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByGender(java.lang.String gender) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.gender like :gender ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "gender", gender);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByGender(java.lang.String gender)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified status field.
     * To use a wildcard search, use a % in the query.
     *
     * @param status the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public java.util.List findUserByStatus(java.lang.String status) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.status like :status ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "status", status);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findUserByStatus(java.lang.String status)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified uuid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param uuid the field
     * @return List of User data objects, empty list in case no results were found.
     */
    public User findUserByUuid(java.lang.String uuid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + User.class.getName() + " e where e.uuid like :uuid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.userid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "uuid", uuid);
         List list = hibernateTemplate.list(query);
         if(!list.isEmpty()){
        	 return (User) list.get(0);
         }
         return null;
      } finally {
         log.debug("finished findUserByUuid(java.lang.String uuid)");
      }
    }
    
    /**
     * Adds a new token to the database.
     *
     * @param model a data object
     * @return Token a data object with the primary key
     */
    public com.skip.entity.token.Token addToken(com.skip.entity.token.Token model) throws GenericBusinessException {
     return tokenSession.addToken(model);
    }

    /**
     * Stores the <code>Token</code> in the database.
     *
     * @param model the data model to store
     */
    public Token saveToken(com.skip.entity.token.Token model) throws GenericBusinessException {
       tokenSession.saveToken(model);
       return model;
      
    }



}
