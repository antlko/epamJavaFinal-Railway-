package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.login.UserSignInAction;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Change user action.
 *
 * @author Anatol Kozhukhar
 */
public class UserChangeData extends Action {
    private static final Logger LOG = Logger.getLogger(UserChangeData.class);
    private static final long serialVersionUID = 2157016170904898520L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        User user = new User();
        user.setLogin(request.getParameter("loginUser"));

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection();) {
            connection.setAutoCommit(false);
            UserDao userDao = new UserDao(connection);

            if ("Save".equals(request.getParameter("updateUserInfo"))) {
                String newRole = request.getParameter("tagRole");

                userDao.saveUserRoleByLogin(user, newRole);
            }
            if ("Delete".equals(request.getParameter("updateUserInfo"))) {
                userDao.delete(user);
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_USER_DATA), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_USER_DATA));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);
        return "/admin";
    }
}
