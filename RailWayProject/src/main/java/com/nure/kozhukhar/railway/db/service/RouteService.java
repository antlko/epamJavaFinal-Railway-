package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.dao.RouteDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.route.Route;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RouteService {

    public static List<RouteSearchBean> getRouteInfoByCityDate(String cityStart, String cityEnd, Date date) {
        List<Route> routes = RouteDao.getIdRouteOnDate(cityStart, cityEnd, date);
        List<RouteSearchBean> routesBean = new ArrayList<>();
        for (Route rt : routes) {
            RouteSearchBean routeInfo = new RouteSearchBean();
            routeInfo.setIdRoute(rt.getId());
            routeInfo.setTrain(rt.getTrain());
            routeInfo.setStationList(StationDao.getAllStationByRoute(cityStart, cityEnd, date, rt.getId()));

            String from = String.valueOf(routeInfo.getStationList().get(0).getTimeEnd());
            String to = String.valueOf(routeInfo.getStationList().get(
                    routeInfo.getStationList().size() - 1).getTimeEnd()
            );
            try {
                long diff = ChronoUnit.SECONDS.between(LocalDateTime.parse(from), LocalDateTime.parse(to));
                routeInfo.setTravelTime(formatterToHours(diff));
                routesBean.add(routeInfo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return routesBean;
    }

    private static String formatterToHours(long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;

        return hours + " : " + minutes;
    }

}
