package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.EncryptUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Check user sign in action.
 *
 * @author Anatol Kozhukhar
 */
public class UserSignInAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserSignInAction.class);
    private static final long serialVersionUID = 8755470787136309281L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();
        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection();) {
            connection.setAutoCommit(false);

            UserDao userDao = new UserDao(connection);
            String login = "";
            String password = "";
            User user = null;

            try {
                login = request.getParameter("login");
                password = EncryptUtil.hash(request.getParameter("password"));
                user = userDao.getByLogin(login);
            } catch (DBException | NoSuchAlgorithmException e) {
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

            if (userDao.getUserRolesByLogin(user.getLogin()).contains(Role.ADMIN.getName())) {
                LOG.trace("Show available roles : " + userDao.getUserRolesByLogin(user.getLogin()));
                role = Role.ADMIN.getName();
            }

            session.setAttribute("userRoles",
                    role
            );
            LOG.trace("User roles : " + userDao.getUserRolesByLogin(user.getLogin()));
            session.setAttribute("user", user);

            LOG.debug("User in session" + session.getAttribute("user"));
        } catch (DBException | ClassNotFoundException | SQLException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        return "/account";
    }
}
