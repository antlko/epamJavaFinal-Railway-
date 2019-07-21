package com.nure.kozhukhar.railway.web.action.account;

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

/**
 * User Show Check action.
 *
 * @author Anatol Kozhukhar
 */
public class UserShowCheckAction extends Action {
    private static final Logger LOG = Logger.getLogger(UserShowCheckAction.class);
    private static final long serialVersionUID = -1398601593746077942L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();
        Integer idUser = ((User) session.getAttribute("user")).getId();
        try {
            request.setAttribute("checkInfo", CheckService.getUserTicketsById(idUser).get(
                    Integer.valueOf(request.getParameter("checkInd"))
            ));
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SHOW_DOCUMENT), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SHOW_DOCUMENT));
        }

        return "WEB-INF/jsp/user/print_doc.jsp";
    }
}
