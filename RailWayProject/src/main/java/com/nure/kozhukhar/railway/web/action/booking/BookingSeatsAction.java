package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.service.RouteService;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class BookingSeatsAction extends Action {

    private static final Logger LOG = Logger.getLogger(BookingSeatsAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        LOG.debug("Post parameters: [" + request.getParameter("idTrain") + " " +
                request.getParameter("seatCheckType") + " " +
                session.getAttribute("cityStart") + " " +
                session.getAttribute("cityEnd") + " " +
                request.getParameter("dateFrom") + "]");

        session.setAttribute("idTrain", request.getParameter("idTrain"));
        session.setAttribute("seatCheckType", request.getParameter("seatCheckType"));
        session.setAttribute("dateFrom", request.getParameter("dateFrom"));

        String cityStart = (String) session.getAttribute("cityStart");
        String cityEnd = (String) session.getAttribute("cityEnd");
        Date date = Date.valueOf(request.getParameter("dateFrom"));
        String type = request.getParameter("seatCheckType");
        Integer idTrain = Integer.valueOf(request.getParameter("idTrain"));

        List<SeatSearchBean> seatBeanList = RouteService.getSeatInfoByCarriageType(
            cityStart, cityEnd,date,type,idTrain
        );

        session.setAttribute("serviceCarriage", seatBeanList);
        session.setAttribute("serviceSeats", seatBeanList.get(0).getListSeat());

        return "WEB-INF/jsp/booking/order.jsp";
    }

}
