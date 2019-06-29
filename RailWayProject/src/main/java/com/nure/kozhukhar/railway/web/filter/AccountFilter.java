package com.nure.kozhukhar.railway.web.filter;

import com.nure.kozhukhar.railway.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AccountFilter", urlPatterns = "/account/*")
public class AccountFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AccountFilter.class);

    public void destroy() {
        LOG.debug("Account filter was destroyed.");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user != null) {
            chain.doFilter(req, resp);
        } else {
            LOG.debug("Account not in session.");
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
