package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.EncryptUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCreateAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {


        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection();) {
            connection.setAutoCommit(false);
            User newUser = new User();
            newUser.setLogin(request.getParameter("login"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setEmail(request.getParameter("email"));

            if (newUser.getLogin().length() < 5) {
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_REGISTER_DATA_LOGIN));
            }
            if (newUser.getPassword().length() < 5) {
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_REGISTER_DATA_PASSWORD));
            }
            if (!isValidEmail(newUser.getEmail())) {
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_REGISTER_DATA_EMAIL));
            }

            UserDao userTempDao = new UserDao(connection);
            newUser.setPassword(EncryptUtil.hash(newUser.getPassword()));
            userTempDao.save(newUser);
        } catch (DBException | ClassNotFoundException | SQLException ex) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, ex.getMessage()));
        } catch (NoSuchAlgorithmException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_REGISTER_DATA_PASSWORD));
        }


        return "/login";
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
