package com.nure.kozhukhar.railway.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cookie Locale Filter
 * <p>
 *     Checks locale in cookies.
 * </p>
 *
 * @author Anatol Kozhukhar
 */
@WebFilter(filterName = "CookieLocaleFilter", urlPatterns = { "/*" })
public class CookieLocaleFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CookieLocaleFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getParameter("pageLocale") != null) {
            Cookie cookie = new Cookie("localize", req.getParameter("pageLocale"));
            res.addCookie(cookie);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {}

    public void init(FilterConfig arg0) throws ServletException {}

}
