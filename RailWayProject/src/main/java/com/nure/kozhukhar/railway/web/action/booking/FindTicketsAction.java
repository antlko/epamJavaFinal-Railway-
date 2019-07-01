package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.service.RouteService;
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

public class FindTicketsAction extends Action {

    private static final Logger LOG = Logger.getLogger(FindTicketsAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("infoBookingMessage", "");
        request.removeAttribute("infoRoutes");

        Date date = Date.valueOf(request.getParameter("date"));
        String cityStart = request.getParameter("cityStart");
        String cityEnd = request.getParameter("cityEnd");

        LOG.trace("Booking Date : " + date);

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
            session.setAttribute("infoBookingMessage",
                    "Routes not found. Try again later or change some parameters.");
        }

        return "/booking";
    }
}
