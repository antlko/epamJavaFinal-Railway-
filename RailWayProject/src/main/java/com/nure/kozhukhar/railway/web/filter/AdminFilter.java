package com.nure.kozhukhar.railway.web.filter;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Security account filter.
 * checks whether the user has ADMIN role
 *
 * @author Anatol Kozhukhar
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AdminFilter.class);

    public void destroy() {
        LOG.debug("Admin filter was destroyed.");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String roles = (String) session.getAttribute("userRoles");
            LOG.trace("User id : " + user.getId() + ", Roles : " + roles);
            if (roles.contains(Role.ADMIN.getName())) {
                chain.doFilter(req, resp);
            } else {
                LOG.debug("User is not admin");
                HttpServletResponse response = (HttpServletResponse) resp;
                response.sendRedirect(request.getContextPath() + "/account");
            }
        } else {
            LOG.debug("Account not in session.");
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {
        LOG.debug("Admin filter was started.");
    }

}
