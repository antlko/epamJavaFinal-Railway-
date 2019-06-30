package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingStartPageAction extends Action {

    private static final Logger LOG = Logger.getLogger(BookingStartPageAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        session.removeAttribute("listInfoBookingMessage");

        StationDao stationDao = new StationDao();
        List<Station> stationList = stationDao.getAll();

        if (stationList.size() > 0) {
            request.setAttribute("listStation", stationList);
            LOG.trace("list size : " + stationList.size() + " : " + stationList);
        }

        return "WEB-INF/jsp/booking/booking.jsp";
    }
}
