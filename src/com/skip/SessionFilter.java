package com.skip;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.skip.servicelocator.*;


 /**
  * Make sure that after every request, the database session is closed.
  *
 * @author  Mostafa  SkipTechnology
  * @version $Revision: 1.2 $, $Date: 2006/01/20 13:10:17 $
  */
public class SessionFilter implements Filter {
    /**
     * Unused.
     */
    public void init(FilterConfig config) {
    }

    /**
     * Unused.
     */
    public void destroy() {
    }

    /**
     * process the chain and close the database session.
     *
     * @param request the servlet request
     * @param response the servlet response
     * @param chain the chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       try {
          chain.doFilter(request, response);
       }
       finally {
         try {
             DatabaseSessionService service = (DatabaseSessionService) ServiceLocator.getService(ServiceLocator.DATABASE_SESSION_SERVICE);
             service.close();
          } catch (Exception e) {
             e.printStackTrace();
         }
       }
    }
}
