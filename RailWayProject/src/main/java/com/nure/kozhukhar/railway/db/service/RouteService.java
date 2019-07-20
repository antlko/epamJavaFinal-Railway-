package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.dao.CheckDao;
import com.nure.kozhukhar.railway.db.dao.RouteDao;
import com.nure.kozhukhar.railway.db.dao.SeatDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Route Service
 * <p>
 *     This class would be used in order to show route by user query.
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public class RouteService {

    private static final Logger LOG = Logger.getLogger(RouteService.class);

    /**
     * This method is used for possible show to user
     * route information
     *
     * @param cityStart city from
     * @param cityEnd city destination
     * @param date date depart
     * @return list of route information
     * @throws DBException
     */
    public static List<RouteSearchBean> getRouteInfoByCityDate(String cityStart, String cityEnd, Date date) throws DBException {

        List<RouteSearchBean> routesBean = null;

        // Getting connection by DBUtil and then transfer it to Dao constructors
        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            // Initialisation dao objects
            RouteDao routeDao = new RouteDao(connection);
            SeatDao seatDao = new SeatDao(connection);
            StationDao stationDao = new StationDao(connection);

            // Getting routes from query by variables
            List<Route> routes = routeDao.getIdRouteOnDate(cityStart, cityEnd, date);
            routesBean = new ArrayList<>();

            LOG.trace("Route-id list for loop -> " + routes);

            for (Route rt : routes) {
                RouteSearchBean routeInfo = new RouteSearchBean();
                routeInfo.setIdRoute(rt.getId());
                routeInfo.setTrain(rt.getTrain());
                routeInfo.setStationList(stationDao.getAllStationByRoute(
                        cityStart, cityEnd, date, rt.getId()));

                LOG.debug("List stations in routeInfo -> " + routeInfo.getStationList());
                if (routeInfo.getStationList().size() == 0) {
                    continue;
                }
                List<String> routeStations = routeDao.getAllStationsByRouteId(routeInfo.getIdRoute());
                List<String> routeStatByQuery = new ArrayList<>();

                LOG.debug("Route stations list is -> " + routeStations);
                for (RouteStation rts : routeInfo.getStationList()) {
                    routeStatByQuery.add(rts.getName());
                }

                // Checking if all stations is existing in right sequences
                // in the found route
                if (containsInList(routeStations, routeStatByQuery)) {
                    LOG.debug("List is true. Done!");
                    String from = String.valueOf(routeInfo.getStationList().get(0).getTimeEnd());
                    String to = String.valueOf(routeInfo.getStationList().get(
                            routeInfo.getStationList().size() - 1).getTimeEnd()
                    );

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    routeInfo.setDateFrom(routeInfo.getStationList().get(0).getTimeEnd().format(formatter));

                    LOG.trace("Date depart : " + routeInfo.getStationList().get(0).getTimeEnd());

                    routeInfo.setTimeFrom(routeInfo.getStationList().get(0).getTimeEnd().getHour()
                            + " : " + routeInfo.getStationList().get(0).getTimeEnd().getMinute());
                    long diff = ChronoUnit.SECONDS.between(LocalDateTime.parse(from), LocalDateTime.parse(to));
                    routeInfo.setTravelTime(TimeUtil.formatterToHours(diff));
                    routesBean.add(routeInfo);

                    routeInfo.setSeatList(seatDao.getSeatCountInfo(cityStart, cityEnd, date, rt.getId()));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_GET_ROUTE, e);
        }
        return routesBean;
    }

    /**
     * This method is used for getting information about
     * seat by user query.
     *
     * @param cityStart city from
     * @param cityEnd city destination
     * @param date date depart
     * @param type type of the carriage
     * @param idTrain ID train
     * @return list of SeatSearchBean
     * @throws DBException
     */
    public static List<SeatSearchBean> getSeatInfoByCarriageType(
            String cityStart, String cityEnd, Date date, String type, Integer idTrain
    ) throws DBException {
        List<SeatSearchBean> seatSearchBeans;

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            SeatDao seatDao = new SeatDao(connection);

            seatSearchBeans = seatDao.getAllSeatsByCarriageType(
                    cityStart, cityEnd, type, date, idTrain);

            for (SeatSearchBean searchBean : seatSearchBeans) {
                searchBean.setListSeat(seatDao.getAllSeatsByCarriageTypeAndNum(
                        cityStart, cityEnd, type, date, idTrain, searchBean.getNumCarriage())
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SEAT, e);
        }

        LOG.trace("Service Seats : " + seatSearchBeans);
        return seatSearchBeans;
    }

    /**
     * Method for comparing sequences attributes in lists.
     * @param str first list
     * @param check second list
     * @return contains or not (true/false)
     */
    public static boolean containsInList(List<String> str, List<String> check) {
        String main = "";
        String query = "";
        main = appendStringList(str);
        query = appendStringList(check);
        if (main.contains(query)) {
            return true;
        }
        return false;
    }

    /**
     * Translate list of strings to one string
     * by using string builder.
     *
     * @param str list of strings
     * @return
     */
    private static String appendStringList(List<String> str) {
        StringBuilder elem = new StringBuilder();
        for (String st : str) {
            elem.append(st);
        }
        return elem.toString();
    }

}
