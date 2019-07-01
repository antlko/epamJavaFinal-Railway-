package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.dao.RouteDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.route.Route;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            routeInfo.setDateFrom(String.valueOf(
                    routeInfo.getStationList().get(0).getTimeEnd().format(formatter))
            );
//            SimpleDateFormat formatter = new SimpleDateFormat(
//                    "dd/MM/yyyy");
//            try {
//                routeInfo.setDateFrom(String.valueOf(
//                        formatter.parse(formatter.format(routeInfo.getStationList().get(0).getTimeEnd())))
//                );
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

            routeInfo.setTimeFrom(routeInfo.getStationList().get(0).getTimeEnd().getHour()
                    + " : " + routeInfo.getStationList().get(0).getTimeEnd().getMinute());
            long diff = ChronoUnit.SECONDS.between(LocalDateTime.parse(from), LocalDateTime.parse(to));
            routeInfo.setTravelTime(formatterToHours(diff));
            routesBean.add(routeInfo);
        }
        return routesBean;
    }

    private static String formatterToHours(long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;

        return hours + " : " + minutes;
    }

}
