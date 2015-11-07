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







import com.skip.entity.token.*;
import com.skip.entity.user.User;

/**
 * The <code>TokenSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class TokenSession implements ITokenSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(TokenSession.class);


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
    * Adds a new token to the database.
    *
    * @param model a data object
    * @return Token a data object with the primary key
    */
   public com.skip.entity.token.Token addToken(com.skip.entity.token.Token model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
    	  while (true) {
				String uuid = UUID.randomUUID().toString();
				Token token = findTokenByAuthtoken(uuid);
				if (token == null) {
					model.setAuthtoken(uuid);
					break;
				}
			}
         hibernateTemplate.save(model);
         return getToken(model.getTokenId());
      } finally {
         log.debug("finished addToken(com.skip.entity.token.Token model)");
      }
   }

   /**
    * Stores the <code>Token</code> in the database.
    *
    * @param model the data model to store
    */
   public void saveToken(com.skip.entity.token.Token model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
    	    	  
    	  
    	  while (true) {
				String uuid = UUID.randomUUID().toString();
				Token token = findTokenByAuthtoken(uuid);
				if (token == null) {
					model.setAuthtoken(uuid);
					break;
				}
			}
         hibernateTemplate.update(model);
         hibernateTemplate.evict(model);
         
         
      } finally {
         log.debug("finished saveToken(com.skip.entity.token.Token model)");
      }
   }

   /**
    * Removes a token.
    *
    * @param id the unique reference for the token
    */
   public void deleteToken(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Token bean = (Token) hibernateTemplate.get(Token.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deleteToken(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Token the data object
    */
   public com.skip.entity.token.Token getToken(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Token bean = (Token) hibernateTemplate.get(Token.class, id);
      return bean;
      } finally {
         log.debug("finished getToken(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all token instances.
    *
    * @return a list of Token objects.
    */
   public List getTokenList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Token.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.tokenid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getTokenList");
      }
   }

   /**
    * Returns a subset of all token instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of token instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getTokenListSize()</code> = last record),
    * any values greater than or equal to the total number of token instances will cause
    * the full set to be returned.
    * @return a list of Token objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getTokenList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getTokenList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of token objects in the database.
    *
    * @return an integer value.
    */
   public int getTokenListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Token.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getTokenListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified tokenid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param tokenid the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByTokenid(java.lang.Long tokenid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.tokenid like :tokenid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "tokenid", tokenid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByTokenid(java.lang.Long tokenid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified authtoken field.
     * To use a wildcard search, use a % in the query.
     *
     * @param authtoken the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public Token findTokenByAuthtoken(java.lang.String authtoken) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.authtoken like :authtoken ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "authtoken", authtoken);
         List list = hibernateTemplate.list(query);
         if(!list.isEmpty()){
        	Token t =(Token) list.get(0) ;
        	hibernateTemplate.evict(t);
        	return (Token) hibernateTemplate.get(Token.class, t.getTokenId());
         }
         return null;
      } finally {
         log.debug("finished findTokenByAuthtoken(java.lang.String authtoken)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified userid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param userid the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByUserid(java.lang.Long userid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.userid like :userid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "userid", userid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByUserid(java.lang.Long userid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified authcreation field.
     * To use a wildcard search, use a % in the query.
     *
     * @param authcreation the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByAuthcreation(java.sql.Timestamp authcreation) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.authcreation like :authcreation ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "authcreation", authcreation);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByAuthcreation(java.sql.Timestamp authcreation)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified authexpiration field.
     * To use a wildcard search, use a % in the query.
     *
     * @param authexpiration the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByAuthexpiration(java.sql.Timestamp authexpiration) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.authexpiration like :authexpiration ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "authexpiration", authexpiration);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByAuthexpiration(java.sql.Timestamp authexpiration)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified verificationtoken field.
     * To use a wildcard search, use a % in the query.
     *
     * @param verificationtoken the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByVerificationtoken(java.lang.String verificationtoken) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.verificationtoken like :verificationtoken ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "verificationtoken", verificationtoken);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByVerificationtoken(java.lang.String verificationtoken)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified verexpiration field.
     * To use a wildcard search, use a % in the query.
     *
     * @param verexpiration the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByVerexpiration(java.sql.Timestamp verexpiration) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.verexpiration like :verexpiration ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "verexpiration", verexpiration);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByVerexpiration(java.sql.Timestamp verexpiration)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified vercreation field.
     * To use a wildcard search, use a % in the query.
     *
     * @param vercreation the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByVercreation(java.sql.Timestamp vercreation) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.vercreation like :vercreation ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "vercreation", vercreation);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByVercreation(java.sql.Timestamp vercreation)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified vertype field.
     * To use a wildcard search, use a % in the query.
     *
     * @param vertype the field
     * @return List of Token data objects, empty list in case no results were found.
     */
    public java.util.List findTokenByVertype(java.lang.String vertype) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Token.class.getName() + " e where e.vertype like :vertype ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.tokenid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "vertype", vertype);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findTokenByVertype(java.lang.String vertype)");
      }
    }


}
