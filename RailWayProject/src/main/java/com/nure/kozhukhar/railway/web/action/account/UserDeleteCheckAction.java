package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.dao.CheckDao;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User Delete check action.
 *
 * @author Anatol Kozhukhar
 */
public class UserDeleteCheckAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserDeleteCheckAction.class);
    private static final long serialVersionUID = -1217118784989852859L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        Integer checkInt = Integer.valueOf(request.getParameter("checkInd"));
        List<UserCheckBean> userCheckBeans = (ArrayList<UserCheckBean>) request.getSession().getAttribute("userChecks");
        UserCheckBean userCheck = userCheckBeans.get(checkInt);

        LOG.trace("Check for delete : " + userCheck);

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            CheckDao checkDao = new CheckDao(connection);
            checkDao.delete(userCheck);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_DELETE_USER_CHECK), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_DELETE_USER_CHECK));
        }


        return "/account";
    }
}
