package com.skip;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;

import com.skip.entity.payment.*;
import com.skip.entity.product.*;
import com.skip.entity.purchasedetails.*;
import com.skip.entity.purchases.*;
import com.skip.entity.shop.*;
import com.skip.entity.user.*;
import com.skip.util.log.*;


/**
 * This class is a Helper for the Hibernate Sessions.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.5 $, $Date: 2006/01/20 13:10:17 $
 */
public class HibernateSessionHelper {

    /**
     * The logger
     */
    private static Logger log = LogService.getLogger(HibernateSessionHelper.class);

    private static int count = 0;

   /**
    * The Hibernate configuration.
    */
   private static AnnotationConfiguration cfg;

   /**
    * The Hibernate session factory.
    */
   private static SessionFactory sf;

   /**
    * Use a thread local session, to allow JNDI lookup from tomcat.
    */
   public static final ThreadLocal sessionLocal = new ThreadLocal();

    static {
       try {
        cfg = new AnnotationConfiguration();
        cfg.configure();
        sf = cfg.buildSessionFactory();
       } catch (Exception e) {
          log.fatal("Error while configuring the hibernate beans", e);
       }
    }

   /**
    * Get a hibernate session and put it in the local thread.
    */
   public static Session getHibernateSession() throws Exception {
      Session session = (Session) sessionLocal.get();
      if (session == null) {
         count++;
         log.debug("Opened the session: " + count);
         session = sf.openSession();
         sessionLocal.set(session);
      }
      return session;
   }

   /**
    * Close a hibernate session in the local thread.
    */
   public static void close() throws Exception {
      Session session = (Session) sessionLocal.get();
      sessionLocal.set(null);
      if (session != null) {
         count--;
         log.debug("Closed the session: " + count);
         session.close();
      }
   }

}
