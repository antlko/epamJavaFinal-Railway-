package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserChangeData extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        User user = new User();
        user.setLogin(request.getParameter("loginUser"));

        try {
            if ("Save".equals(request.getParameter("updateUserInfo"))) {
                String newRole = request.getParameter("tagRole");

                UserDao.saveUserRoleByLogin(user, newRole);
            }
            if ("Delete".equals(request.getParameter("updateUserInfo"))) {
                UserDao userDao = new UserDao();
                userDao.delete(user);
            }
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);
        return "/admin";
    }
}
