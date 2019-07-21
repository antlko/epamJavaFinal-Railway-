package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User Update action.
 *
 * @author Anatol Kozhukhar
 */
public class UserUpdatePersonal extends Action {

    private static final Logger LOG = Logger.getLogger(UserUpdatePersonal.class);
    private static final long serialVersionUID = -5142199038334962721L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();

        User newUser = new User();
        User oldUser = (User) session.getAttribute("user");

        newUser.setName(request.getParameter("Name"));
        newUser.setSurname(request.getParameter("Surname"));
        newUser.setEmail(request.getParameter("Email"));
        newUser.setLogin(oldUser.getLogin());
        newUser.setId(oldUser.getId());

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection();) {
            connection.setAutoCommit(false);

            UserDao userTempDao = new UserDao(connection);
            userTempDao.update(newUser, null);

        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_UPDATE_USER), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_UPDATE_USER));
        }
        LOG.trace("New User data : " + newUser);

        session.setAttribute("user", newUser);

        return "/account";
    }
}
