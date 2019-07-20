package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.service.RouteService;
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
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

/**
 * Find Ticket action.
 *
 * @author Anatol Kozhukhar
 */
public class FindTicketsAction extends Action {

    private static final Logger LOG = Logger.getLogger(FindTicketsAction.class);
    private static final long serialVersionUID = 4879439555532315808L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        request.setAttribute("infoBookingMessage", "");

        String cityStart = "";
        String cityEnd = "";

        Date date = Date.valueOf(request.getParameter("date"));
        cityStart = request.getParameter("cityStart");
        cityEnd = request.getParameter("cityEnd");

        if ("".equals(cityStart) || "".equals(cityEnd)
                || cityStart.equals(cityEnd) || date == null) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_FIND_ANY_ROUTE));
        }

        LOG.trace("Booking Date : " + date);

        try {
            List<RouteSearchBean> routes = null;
            routes = RouteService.getRouteInfoByCityDate(cityStart, cityEnd, date);

            LOG.trace("Booking Route : " + routes);

            HttpSession session = request.getSession();
            session.setAttribute("cityStart", cityStart);
            session.setAttribute("cityEnd", cityEnd);
            session.setAttribute("date", date);

            if (routes.size() > 0) {
                session.setAttribute("infoRoutes", routes);
                session.removeAttribute("infoBookingMessage");
            } else {
                session.removeAttribute("infoRoutes");
                throw new AppException(LocaleMessageUtil
                        .getMessageWithLocale(request, Messages.ERR_CANNOT_FIND_ANY_ROUTE));
            }
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        return "/booking";
    }
}
