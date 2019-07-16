package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.db.service.RouteService;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookingSeatsAction extends Action {

    private static final Logger LOG = Logger.getLogger(BookingSeatsAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        HttpSession session = request.getSession();


        Integer indRoute = Integer.valueOf(request.getParameter("checkedRouteForUser"));
        List<RouteSearchBean> listRsb = (ArrayList<RouteSearchBean>) session.getAttribute("infoRoutes");
        RouteSearchBean rsb = listRsb.get(indRoute);
        LOG.trace("Ind Route :" + indRoute + " \n|Routes : " + listRsb + "\n| Route :" + rsb);


        String cityStart = (String) session.getAttribute("cityStart");
        String cityEnd = (String) session.getAttribute("cityEnd");
        Date date = Date.valueOf(rsb.getDateFrom());
        String type = request.getParameter("seatCheckType");
        Integer idTrain = rsb.getTrain().getNumber();

        try {
            List<SeatSearchBean> seatBeanList = RouteService.getSeatInfoByCarriageType(
                    cityStart, cityEnd, date, type, idTrain
            );

            Integer totalRoutePrice = 0;
            for (RouteStation rs : rsb.getStationList()) {
                totalRoutePrice += rs.getPrice();
            }

            session.setAttribute("checkedCarriage", seatBeanList.get(0).getNumCarriage());
            session.setAttribute("seatCheckType", type);
            session.setAttribute("serviceCarriage", seatBeanList);
            session.setAttribute("routePrice", totalRoutePrice + seatBeanList.get(0).getPriceSeat());
            session.setAttribute("serviceSeats", seatBeanList.get(0).getListSeat());
            session.setAttribute("userRoute", rsb);
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        return "WEB-INF/jsp/booking/seat_info.jsp";
    }

}
