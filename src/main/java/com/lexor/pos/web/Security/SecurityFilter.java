/*
 https://codenotfound.com/jsf-login-servlet-filter-example.html

 */
package com.lexor.pos.web.Security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vinh
 */
@WebFilter(servletNames = "Faces Servlet")
public class SecurityFilter implements Filter {

    public static final String LOGIN_PAGE = "/login.xhtml";
    
    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest
                = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse
                = (HttpServletResponse) servletResponse;

        final String requestURI = httpServletRequest.getRequestURI();
        
        if(requestURI.matches(".*(css|jpg|png|gif|js)")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if(requestURI.indexOf("/secured") == -1){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if(requestURI.indexOf("/template/") == -1){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // managed bean name is exactly the session attribute name
        UserManager userManager = (UserManager) httpServletRequest
                .getSession().getAttribute("userManager");

        if (userManager != null) {
            if (userManager.isLoggedIn()) {

                // user is logged in, continue request
                filterChain.doFilter(servletRequest, servletResponse);
            } else {

                // user is not logged in, redirect to login page
                httpServletResponse.sendRedirect(
                        httpServletRequest.getContextPath() + LOGIN_PAGE);
            }
        } else {

            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(
                    httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void destroy() {
        // close resources
    }
}
