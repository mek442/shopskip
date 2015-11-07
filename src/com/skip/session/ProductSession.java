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

import com.skip.entity.product.*;

/**
 * The <code>ProductSession</code> session bean, which acts as a facade to the
 * underlying entity beans.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.19 $, $Date: 2006/04/29 12:39:09 $
 *
 */
public class ProductSession implements IProductSession , LocatableService {
   /**
    * The logger object.
    */
   private static Logger log = LogService.getLogger(ProductSession.class);


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
    * Adds a new product to the database.
    *
    * @param model a data object
    * @return Product a data object with the primary key
    */
   public com.skip.entity.product.Product addProduct(com.skip.entity.product.Product model) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         hibernateTemplate.save(model);
         return getProduct(model.getProductId());
      } finally {
         log.debug("finished addProduct(com.skip.entity.product.Product model)");
      }
   }

   /**
    * Stores the <code>Product</code> in the database.
    *
    * @param model the data model to store
    */
   public void saveProduct(com.skip.entity.product.Product model) throws GenericBusinessException {
      // We have to create an object:
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // Now update the data.
         hibernateTemplate.update(model);
      } finally {
         log.debug("finished saveProduct(com.skip.entity.product.Product model)");
      }
   }

   /**
    * Removes a product.
    *
    * @param id the unique reference for the product
    */
   public void deleteProduct(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         // First get the data.
         Product bean = (Product) hibernateTemplate.get(Product.class, id);
         hibernateTemplate.delete(bean);
      } finally {
         log.debug("finished deleteProduct(java.lang.Long id)");
      }
   }

   /**
    * Retrieves a data object from the database by its primary key.
    *
    * @param id the unique reference
    * @return Product the data object
    */
   public com.skip.entity.product.Product getProduct(java.lang.Long id) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
      Product bean = (Product) hibernateTemplate.get(Product.class, id);
      return bean;
      } finally {
         log.debug("finished getProduct(java.lang.Long id)");
      }
   }

   /**
    * Returns a list of all product instances.
    *
    * @return a list of Product objects.
    */
   public List getProductList() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {

      String queryString = "from " + Product.class.getName() + " e";
      // Add a an order by on all primary keys to assure reproducable results.
      String orderByPart = "";
      orderByPart += " order by e.productid";
      queryString += orderByPart;
      Query query = hibernateTemplate.createQuery(queryString);
      List list = hibernateTemplate.list(query);

      return list;
      } finally {
         log.debug("finished getProductList");
      }
   }

   /**
    * Returns a subset of all product instances.
    *
    * @param startIndex the start index within the result set (1 = first record);
    * any zero/negative values are regarded as 1, and any values greater than or equal to
    * the total number of product instances will simply return an empty set.
    * @param endIndex the end index within the result set (<code>getProductListSize()</code> = last record),
    * any values greater than or equal to the total number of product instances will cause
    * the full set to be returned.
    * @return a list of Product objects, of size <code>(endIndex - startIndex)</code>.
    */
   public List getProductList(int startIndex, int endIndex) throws GenericBusinessException {
      if (startIndex < 1) {
         startIndex = 1;
      }
      if ( (endIndex - startIndex) < 0) {
         // Just return an empty list.
         return new ArrayList();
      }
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         query.setFirstResult(startIndex - 1);
         query.setMaxResults(endIndex - startIndex + 1);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished getProductList(int startIndex, int endIndex)");
      }
   }

   /**
    * Obtains the total number of product objects in the database.
    *
    * @return an integer value.
    */
   public int getProductListSize() throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "select count(*) from " + Product.class.getName();
         Query query = hibernateTemplate.createQuery(queryString);
         List list = hibernateTemplate.list(query);
         Integer countResult = (Integer) list.get(0);
         return countResult.intValue();
      } finally {
         log.debug("finished getProductListSize()");
      }
   }

    /**
     *
     * Retrieves a list of data object for the specified productid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param productid the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByProductid(java.lang.Long productid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.productid like :productid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "productid", productid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByProductid(java.lang.Long productid)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified name field.
     * To use a wildcard search, use a % in the query.
     *
     * @param name the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByName(java.lang.String name) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.name like :name ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "name", name);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByName(java.lang.String name)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified description field.
     * To use a wildcard search, use a % in the query.
     *
     * @param description the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByDescription(java.lang.String description) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.description like :description ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "description", description);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByDescription(java.lang.String description)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified barcode field.
     * To use a wildcard search, use a % in the query.
     *
     * @param barcode the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByBarcode(java.lang.String barcode) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.barcode like :barcode ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "barcode", barcode);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByBarcode(java.lang.String barcode)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified unitprice field.
     * To use a wildcard search, use a % in the query.
     *
     * @param unitprice the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByUnitprice(java.math.BigDecimal unitprice) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.unitprice like :unitprice ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "unitprice", unitprice);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByUnitprice(java.math.BigDecimal unitprice)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified type field.
     * To use a wildcard search, use a % in the query.
     *
     * @param type the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByType(java.lang.String type) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.type like :type ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "type", type);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByType(java.lang.String type)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified manufacturer field.
     * To use a wildcard search, use a % in the query.
     *
     * @param manufacturer the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByManufacturer(java.lang.String manufacturer) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.manufacturer like :manufacturer ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "manufacturer", manufacturer);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByManufacturer(java.lang.String manufacturer)");
      }
    }
    /**
     *
     * Retrieves a list of data object for the specified shopid field.
     * To use a wildcard search, use a % in the query.
     *
     * @param shopid the field
     * @return List of Product data objects, empty list in case no results were found.
     */
    public java.util.List findProductByShopid(java.lang.Long shopid) throws GenericBusinessException {
      com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
      try {
         String queryString = "from " + Product.class.getName() + " e where e.shopid like :shopid ";
         // Add a an order by on all primary keys to assure reproducable results.
         String orderByPart = "";
         orderByPart += " order by e.productid";
         queryString += orderByPart;
         Query query = hibernateTemplate.createQuery(queryString);
         hibernateTemplate.setQueryParameter(query, "shopid", shopid);
         List list = hibernateTemplate.list(query);
         return list;
      } finally {
         log.debug("finished findProductByShopid(java.lang.Long shopid)");
      }
    }

    public Product findProductByShopidAndBarcode(Long shopid, String barcode)throws com.skip.exception.GenericBusinessException{
    	 com.skip.HibernateQueryHelper hibernateTemplate = new com.skip.HibernateQueryHelper();
         try {
            String queryString = "from " + Product.class.getName() + " e where e.barcode like :barcode and e.shopid = :shopid";
            // Add a an order by on all primary keys to assure reproducable results.
            String orderByPart = "";
            orderByPart += " order by e.productid";
            queryString += orderByPart;
            Query query = hibernateTemplate.createQuery(queryString);
            hibernateTemplate.setQueryParameter(query, "shopid", shopid);
            hibernateTemplate.setQueryParameter(query, "barcode", barcode);
            List list = hibernateTemplate.list(query);
            if(!list.isEmpty()){
            	return (Product) list.get(0);
            }
            return null;
         } finally {
            log.debug("finished findProductByShopidAndBarcode(Long shopid, String barcode)");
         }
    }

}
