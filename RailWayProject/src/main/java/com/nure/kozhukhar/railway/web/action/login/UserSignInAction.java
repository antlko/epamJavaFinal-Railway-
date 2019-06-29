package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserSignInAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserSignInAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = UserDao.getByLogin(login);

        LOG.debug("User view : " + login + ", " + password);
        LOG.debug("User query : " + user);

        if (!(login.equals(user.getLogin()) && password.equals(user.getPassword()))) {
            return "WEB-INF/jsp/error.jsp";
        }

        String role  = Role.USER.getName();
        if(UserDao.getUserRolesByLogin(user.getLogin()).contains(Role.ADMIN.getName())) {
            role = Role.ADMIN.getName();
        }
        session.setAttribute("userRoles",
                role
        );
        LOG.trace("User roles : " + UserDao.getUserRolesByLogin(user.getLogin()));
        session.setAttribute("user", user);

        LOG.debug("User in session" + session.getAttribute("user"));

        return "/account";
    }
}
