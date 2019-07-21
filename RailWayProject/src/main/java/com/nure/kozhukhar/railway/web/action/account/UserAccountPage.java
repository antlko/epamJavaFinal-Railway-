package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.service.CheckService;
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
import java.util.*;

/**
 * User Account Page action.
 *
 * @author Anatol Kozhukhar
 */
public class UserAccountPage extends Action {

    private static final Logger LOG = Logger.getLogger(UserAccountPage.class);
    private static final long serialVersionUID = -876851366293387697L;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) return "/login";

        Integer idUser = user.getId();
        try {
            session.setAttribute("userChecks", CheckService.getUserTicketsById(idUser));
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_OPEN_ACCOUNT_PAGE), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_OPEN_ACCOUNT_PAGE));
        }

        return "WEB-INF/jsp/user/account.jsp";
    }
}
