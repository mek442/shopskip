package com.skip.servicelocator;

import com.skip.exception.*;
import com.skip.session.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.skip.util.log.LogService;
import com.skip.util.log.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.stereotype.Component;


/**
 * The Service Locator maps an interface to an implementation.
 * See:
 * http://java.sun.com/blueprints/corej2eepatterns/Patterns/ServiceLocator.html
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.3 $, $Date: 2006/01/29 21:26:04 $
 */
@Component
public class ServiceLocator {
    /**
     * the logger.
     */
    private static Logger log = LogService.getLogger(ServiceLocator.class);

    /**
     * The DatabaseSessionService name.
     */
    public static final String DATABASE_SESSION_SERVICE = "DATABASE_SESSION_SERVICE";



    /**
     * The UserSessionService name.
     */
    public static final String USERSESSION_SERVICE = "USERSESSION_SERVICE";

    /**
     * The TokenSessionService name.
     */
    public static final String TOKENSESSION_SERVICE = "TOKENSESSION_SERVICE";

    /**
     * The SocialSessionService name.
     */
    public static final String SOCIALSESSION_SERVICE = "SOCIALSESSION_SERVICE";

    /**
     * The ShopSessionService name.
     */
    public static final String SHOPSESSION_SERVICE = "SHOPSESSION_SERVICE";

    /**
     * The PurchasesSessionService name.
     */
    public static final String PURCHASESSESSION_SERVICE = "PURCHASESSESSION_SERVICE";

    /**
     * The PurchaseDetailsSessionService name.
     */
    public static final String PURCHASEDETAILSSESSION_SERVICE = "PURCHASEDETAILSSESSION_SERVICE";

    /**
     * The ProductSessionService name.
     */
    public static final String PRODUCTSESSION_SERVICE = "PRODUCTSESSION_SERVICE";

    /**
     * The PaymentSessionService name.
     */
    public static final String PAYMENTSESSION_SERVICE = "PAYMENTSESSION_SERVICE";

    /**
     * Contains the class constants
     */
    private static HashMap map;

    /**
     * Contains the classname of the implementation class.
     */
    private static Map serviceInfoMap;

    /**
     * Contains the interface name of the service.
     */
    private static Map serviceInterfaceMap;

    /**
     * Contains the instantiated services.
     */
    private static Map serviceMap;

    /**
     * property.
     */
    static Properties props = null;

    /**
     * Read the service properties.
     */
    static {
        props = ServicePropertyReader.getProperties();
        serviceInfoMap = new HashMap();
        serviceMap = new HashMap();
        serviceInterfaceMap = new HashMap();

        serviceInfoMap.put(DATABASE_SESSION_SERVICE,
                           props.getProperty(DATABASE_SESSION_SERVICE));
        serviceInfoMap.put(USERSESSION_SERVICE,
                           props.getProperty(USERSESSION_SERVICE));

        serviceInfoMap.put(TOKENSESSION_SERVICE,
                           props.getProperty(TOKENSESSION_SERVICE));

        serviceInfoMap.put(SOCIALSESSION_SERVICE,
                           props.getProperty(SOCIALSESSION_SERVICE));

        serviceInfoMap.put(SHOPSESSION_SERVICE,
                           props.getProperty(SHOPSESSION_SERVICE));

        serviceInfoMap.put(PURCHASESSESSION_SERVICE,
                           props.getProperty(PURCHASESSESSION_SERVICE));

        serviceInfoMap.put(PURCHASEDETAILSSESSION_SERVICE,
                           props.getProperty(PURCHASEDETAILSSESSION_SERVICE));

        serviceInfoMap.put(PRODUCTSESSION_SERVICE,
                           props.getProperty(PRODUCTSESSION_SERVICE));

        serviceInfoMap.put(PAYMENTSESSION_SERVICE,
                           props.getProperty(PAYMENTSESSION_SERVICE));

        serviceInterfaceMap.put(DATABASE_SESSION_SERVICE,
                           "com.skip.DatabaseSessionService");

        serviceInterfaceMap.put(USERSESSION_SERVICE,
                           "com.skip.session.IUserSession");
        serviceInterfaceMap.put(TOKENSESSION_SERVICE,
                           "com.skip.session.ITokenSession");
        serviceInterfaceMap.put(SOCIALSESSION_SERVICE,
                           "com.skip.session.ISocialSession");
        serviceInterfaceMap.put(SHOPSESSION_SERVICE,
                           "com.skip.session.IShopSession");
        serviceInterfaceMap.put(PURCHASESSESSION_SERVICE,
                           "com.skip.session.IPurchasesSession");
        serviceInterfaceMap.put(PURCHASEDETAILSSESSION_SERVICE,
                           "com.skip.session.IPurchaseDetailsSession");
        serviceInterfaceMap.put(PRODUCTSESSION_SERVICE,
                           "com.skip.session.IProductSession");
        serviceInterfaceMap.put(PAYMENTSESSION_SERVICE,
                           "com.skip.session.IPaymentSession");

    }

    /**
     * Locate a Service implementation by passing the Name of the service. The
     * getService() method will initalize an implementation of the service.
     *
     * @param serviceName de naam van de service
     * @return Een Implementatie van de gespecificeerde service.
     * @throws UnknownServiceException de exceptie
     * @throws ServiceInstantiationException de exceptie
     */
    public static LocatableService getService(String serviceName)
                                       throws UnknownServiceException,
                                              ServiceInstantiationException {
        log.debug("Locating service for " + serviceName);

        //see if there is a initialized
        LocatableService service = (LocatableService) serviceMap.get(serviceName);

        if (service != null) {
            return service;
        } else {
            //get service for the first time
            String className = (String) serviceInfoMap.get(serviceName);
            log.debug("Found service " + className);
            String interfaceName = (String) serviceInterfaceMap.get(serviceName);
            log.debug("Found service " + interfaceName);

            if (className != null) {
                try {
                    log.debug("Use classloader to find class: " + interfaceName);
                    Class theServiceInterface = Class.forName(interfaceName);
                    log.debug("Use classloader to find class: " + className);                    
                    Class theDelegateClass = Class.forName(className);
   					  log.debug("Create a new instance of the loaded class.");
						  try {
                        log.debug("Create a new instance of the loaded class.");
                        service = (LocatableService) theDelegateClass.newInstance();
                    } catch (Exception e) {
                        log.debug("Create a dynamic proxy for the loaded class.");
                        service = (LocatableService) Proxy.newProxyInstance(theServiceInterface.getClassLoader(), new Class[]{theServiceInterface, LocatableService.class}, (InvocationHandler) theDelegateClass.newInstance());
                    }
                    service.init();
                    log.debug("The service was initialized");
                    serviceMap.put(serviceName, service);
                    log.debug("The service was put in the map");

                    return service;
                } catch (Exception e) {
                    log.error("Error instantiating the service", e);
                    throw new ServiceInstantiationException(e);
                }
            }

            throw new UnknownServiceException("Service not found:" + serviceName);
        }
    }
   /**
    * Determines a list of all constants using reflection and put them in a hashmap.
    *
    * @return HashMap with the names of all constants and their String values.
    */
   public synchronized static HashMap getConstants() {
      if (map != null) {
         return map;
      }
      map = new HashMap();
      Field fields[] = null;

      try {
         fields = ServiceLocator.class.getDeclaredFields();
      }
      catch (SecurityException e) {
         e.printStackTrace();
         return new HashMap();
      }
      if (fields != null) {
         if (fields.length != 0) {
            for (int i = 0; i < fields.length; i++) {
               if (Modifier.isPublic(fields[i].getModifiers())
                  && Modifier.isFinal(fields[i].getModifiers())
                  && Modifier.isStatic(fields[i].getModifiers())
               ) {
                  // It's a constant!
                  try {
                     map.put(fields[i].getName(), fields[i].get(null));
                  }
                  catch (Exception e) {
                     e.printStackTrace();
                     return new HashMap();
                  }
               }
            }
         }
      }
      return map;
   }
}
