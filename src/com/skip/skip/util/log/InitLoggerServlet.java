package com.skip.skip.util.log;

import org.apache.log4j.xml.*;
import javax.servlet.http.HttpServlet;
import java.net.URL;

/**
 * This class handles initializing log4j and expects the log4j.xml file
 * in the web-inf/classes directory.
 * another file name can be used by specifying the "propertiesFilename".
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.3 $, $Date: 2006/01/20 13:10:18 $
 */
public class InitLoggerServlet extends HttpServlet {

   /**
    * The log dir property.
    */
   public static final String LOG_DIR_PROPERTY = "com.finalist.logDir";
   /**
    * The log4J file name.
    */
   public static final String LOG4J_FILENAME = "log4jfinalist-skip.xml";


   /**
    * Init method called on startup from a servlet.
    * This method will try to read the log4j.xml file from the web-inf/classes directory.
    */
   public void init() {
      System.out.println("-- Trying to initialize the logger from the servlet --.");
      this.initLog4j();
   }


   /**
    * Initializes log4j.
    */
   private void initLog4j() {
      try {
         // set System-prop to tell log4j where to put log-files

         String logDir = System.getProperty(LOG_DIR_PROPERTY);
         if (logDir == null) {
            System.out.println("Warning. No logdir set. Please set the com.finalist.logDir property.");
            System.out.println("Logging to the working directory.");
            logDir = ".";
            System.setProperty(LOG_DIR_PROPERTY, logDir);
         }
         else {
            System.out.println("---Logging to directory---: " + logDir);
         }
         System.out.println("Try to read the XML file: " + LOG4J_FILENAME);
         URL url = com.skip.util.PropertyReader.getPropertiesURL(LOG4J_FILENAME);
         System.out.println("Got the following URL: " + url);
         // load config
         DOMConfigurator.configure(url);
         System.out.println("Log4j initialized.");
      }
      catch (Throwable e) {
         System.out.println("Error: Can not init logging (log4j).");
         e.printStackTrace();
      }
   }

}
