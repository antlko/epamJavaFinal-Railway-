package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.RouteDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(AdminAccountPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        RouteDao routeDao = new RouteDao();

        if ("Save".equals(request.getParameter("changeRouteInfo"))) {
            List<RouteStation> routeStation = new ArrayList<>();

            List<String> infoStation = Arrays.asList(request.getParameter("listInfoStation").split(";"));
            LOG.trace(infoStation);
            RouteDao.saveOneRoute(Integer.valueOf(request.getParameter("tagTrains")));

            for (String stationPrice : infoStation) {
                Integer price = Integer.valueOf(
                        stationPrice.replace(" ", "").split("\\.Price:")[1]
                );
                String stationFull = stationPrice.replace(" ", "").split("\\.")[0];

                String station = stationFull.split(",")[0];
                String time = stationFull.split(",")[1];

                String timeStart = time.split("-")[0];
                String timeEnd = time.split("-")[1];

                LOG.trace("Route adding information : station -> " + station + ", time -> " + time);

                String dateStart = "2000-01-01 " + timeStart;
                String dateEnd = "2000-01-01 " + timeEnd;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTimeStart = LocalDateTime.parse(dateStart, formatter);
                LocalDateTime dateTimeEnd = LocalDateTime.parse(dateEnd, formatter);

                RouteStation stationTemp = new RouteStation();
                stationTemp.setTimeStart(dateTimeStart);
                stationTemp.setTimeEnd(dateTimeEnd);
                stationTemp.setName(station);
                stationTemp.setIdTrain(Integer.valueOf(request.getParameter("tagTrains")));
                stationTemp.setPrice(price);

                LOG.trace("Station Temp admin panel : " + stationTemp);

                routeDao.save(stationTemp);
//                routeStation.add(stationTemp);
            }

        }
        if ("Delete".equals(request.getParameter("changeRouteInfo"))) {

        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
