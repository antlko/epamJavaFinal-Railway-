package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserUpdatePersonal extends Action {

    private static final Logger LOG = Logger.getLogger(UserUpdatePersonal.class);

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

        UserDao userTempDao = new UserDao();
        try {
            userTempDao.update(newUser, null);
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }
        LOG.trace("New User data : " + newUser);

        session.setAttribute("user", newUser);

        return "/account";
    }
}
