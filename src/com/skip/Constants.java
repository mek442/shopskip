package com.skip;

import java.text.SimpleDateFormat;

/**
 * Manifest constants for the skip application.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.5 $, $Date: 2006/01/20 13:10:18 $
 */
public final class Constants {

   /**
    * This value is a threshold number, over which value a foreign-key relation field will not be displayed for
    * input as a select list.
    * <p>
    * In a "create/edit entity" JSP, a foreign-key relation field may input by means of a select drop-down list
    * of all possible foreign-key values.  However, if there are a very large number of such values it is not
    * reasonable to expect the user to scroll through a drop-down list with, say, 15 thousand options.
    */
   public static final String FOREIGNKEY_AS_SELECTLIST_THRESHOLD = "250";

   /**
    * the empty select options string
    */
   public static final String EMPTY_SELECT_OPTION = "[selecteer]";

   /**
    * The request parameter name to be used for form submit buttons that require server-side validation
    * to be skipped, e.g. cancel buttons.  With Struts Validation Framework (1.1) this can be used in conjunction
    * with the JavaScript variable 'bCancel', which when set to 'true', skips client-side validation also.
    */
   public static final String SUBMIT_NO_VALIDATION = "cancel";

   /**
    * Determines the format for displaying dates that originate as JDBC type DATE (i.e. dates that have NO
    * additional hours, minutes, seconds, nanos information).
    * See {@link java.text.SimpleDateFormat} for an explanation of the syntax.
    */
   public static final String DATE_DISPLAY_FORMAT = "dd/MM/yyyy";

   /**
    * Determines the format for displaying dates that originate as JDBC type TIMESTAMP (i.e. dates that DO have
    * additional hours, minutes, seconds, nanos information).
    * See {@link java.text.SimpleDateFormat} for an explanation of the syntax.
    */
   public static final String TIMESTAMP_DISPLAY_FORMAT = "dd/MM/yyyy HH:mm:ss";

   /**
    * Timestamp formatter.
    */
   public static final SimpleDateFormat TIMESTAMP_FORMATTER =
         new SimpleDateFormat(Constants.TIMESTAMP_DISPLAY_FORMAT);

   /**
    * Date formatter.
    */
   public static final SimpleDateFormat DATE_FORMATTER =
         new SimpleDateFormat(Constants.DATE_DISPLAY_FORMAT);

}
