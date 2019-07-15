package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class UserSignInAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserSignInAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;
        try {
            try {
                user = UserDao.getByLogin(login);
            } catch (DBException e) {
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_USER_LOGIN_OR_PASSWORD));
            }

            LOG.debug("User view : " + login + ", " + password);
            LOG.debug("User query : " + user);

            if ((!login.equals(user.getLogin()) || !password.equals(user.getPassword()))) {
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_USER_LOGIN_OR_PASSWORD));
            }

            String role = Role.USER.getName();

            if (UserDao.getUserRolesByLogin(user.getLogin()).contains(Role.ADMIN.getName())) {
                LOG.trace("Show available roles : " + UserDao.getUserRolesByLogin(user.getLogin()));
                role = Role.ADMIN.getName();
            }

            session.setAttribute("userRoles",
                    role
            );
            LOG.trace("User roles : " + UserDao.getUserRolesByLogin(user.getLogin()));
            session.setAttribute("user", user);

            LOG.debug("User in session" + session.getAttribute("user"));
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        return "/account";
    }
}
