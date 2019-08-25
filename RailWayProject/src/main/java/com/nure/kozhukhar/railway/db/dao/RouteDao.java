package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.bean.UserStatisticBean;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DAO Object which operate Route information from DB
 * Have a {@link RouteDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class RouteDao implements Dao<RouteStation> {

    private static final Logger LOG = Logger.getLogger(RouteDao.class);


    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public RouteDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method is used for getting ID station by name
     *
     * @param name Station name
     * @return ID station
     * @throws DBException
     */
    public Integer getIdStationByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idStation = null;
        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_ID_STATION_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idStation = rs.getInt("id");
            }
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_STATION, e);
            throw new DBException(Messages.ERR_GET_STATION, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return idStation;
    }

    /**
     * This method is used for saving new stations into route.
     *
     * @param idRoute   Route index
     * @param dateStart Date destination
     * @param dateEnd   Date departure
     * @throws DBException
     */
    public void saveStationByRouteId(Integer idRoute, LocalDateTime dateStart, LocalDateTime dateEnd) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<RouteStation> stationList = new ArrayList<>();

        // Code block which execute query/ies to DB.
        // If operation failed -> we must throw new DBException to the layer upper.
        try {
            pstmt = conn.prepareStatement(Queries.SQL_FIND_STATIONS_BY_ROUTE_ID);
            pstmt.setInt(1, idRoute);
            rs = pstmt.executeQuery();

            // Processing all found stations by route ID
            while (rs.next()) {
                // Station information will be contains in
                // routeStationTemp. There we getting parameters from every stations.
                RouteStation routeStationTemp = new RouteStation();
                routeStationTemp.setIdTrain(rs.getInt("id_train"));
                routeStationTemp.setIdRoute(rs.getInt("id_route"));
                routeStationTemp.setIdStation(rs.getInt("id_station"));

                // Getting and processing by locale date parameters for station
                String date = rs.getObject("time_start").toString().split("\\.")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                routeStationTemp.setTimeStart(dateTime.minusHours(2));

                // Set date-time parameters
                date = rs.getObject("time_end").toString().split("\\.")[0];
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(date, formatter);
                routeStationTemp.setTimeEnd(dateTime.minusHours(2));

                // Adding to station list new Station with info
                stationList.add(routeStationTemp);

                LOG.trace("Time with time zone for adding new route -> " + routeStationTemp.getTimeEnd());
                LOG.trace("Time without time zone for adding new route -> "
                        + rs.getTimestamp("time_end").toLocalDateTime());
            }

            pstmt = conn.prepareStatement(Queries.SQL_SAVE_STATION_BY_ROUTE_ID_INSERT);

            List<String> newDateStart = new ArrayList<>();
            List<String> newDateEnd = new ArrayList<>();

            int daysElapsed = 0;
            Period period = Period.between(dateStart.toLocalDate(), dateEnd.toLocalDate());
            int countOfDays = period.getDays();
            int atr = 1;

            LOG.trace("countOfDays = " + countOfDays);

            // This code will save station in the necessary time span.
            if (stationList.size() > 0) {
                for (int j = 0; j <= countOfDays; ++j) {
                    // Save first station
                    daysElapsed = j;
                    newDateStart.add(dateStart.toLocalDate().plusDays(daysElapsed)
                            + " " + stationList.get(0).getTimeStart().toLocalTime()
                    );
                    newDateEnd.add(dateStart.toLocalDate().plusDays(daysElapsed)
                            + " " + stationList.get(0).getTimeEnd().toLocalTime()
                    );
                    pstmt.setString(atr++, newDateEnd.get(0));
                    pstmt.setInt(atr++, stationList.get(0).getIdTrain());
                    pstmt.setInt(atr++, idRoute);
                    pstmt.setInt(atr++, stationList.get(0).getIdStation());
                    pstmt.setString(atr++, newDateStart.get(0));
                    pstmt.setString(atr, newDateEnd.get(0));
                    pstmt.executeUpdate();
                    atr = 1;
                    for (int i = 1; i < stationList.size(); ++i) {
                        // Save other stations
                        LocalDate localDateStart = stationList.get(i).getTimeStart().toLocalDate();
                        LocalDate localDateEnd = stationList.get(i).getTimeEnd().toLocalDate();

                        period = Period.between(stationList.get(i - 1).getTimeStart().toLocalDate(), localDateStart);
                        daysElapsed += period.getDays();
                        newDateStart.add(dateStart.toLocalDate().plusDays(daysElapsed)
                                + " " + stationList.get(i).getTimeStart().toLocalTime()
                        );
                        newDateEnd.add(dateStart.toLocalDate().plusDays(daysElapsed)
                                + " " + stationList.get(i).getTimeEnd().toLocalTime()
                        );

                        pstmt.setString(atr++, newDateEnd.get(i));
                        pstmt.setInt(atr++, stationList.get(i).getIdTrain());
                        pstmt.setInt(atr++, idRoute);
                        pstmt.setInt(atr++, stationList.get(i).getIdStation());
                        pstmt.setString(atr++, newDateStart.get(i));
                        pstmt.setString(atr, newDateEnd.get(i));
                        pstmt.executeUpdate();
                        LOG.trace("LocalDateStart[i] : " + newDateStart.get(i)
                                + ", LocalDateEnd[i] : " + newDateEnd.get(i)
                        );
                        atr = 1;
                    }

                    LOG.trace("New inserted date start is --> " + newDateStart);
                    LOG.trace("New inserted date end is --> " + newDateEnd);
                    newDateStart.clear();
                    newDateEnd.clear();
                }
            } else {
                throw new SQLException();
            }
            conn.commit();

            LOG.debug("Route on date saved!");
        } catch (SQLException e) {
            // Rollback changes in DB, inform server using Logger,
            // and throw new DBException.
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
            throw new DBException(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
        } finally {
            // Close all opened resources.
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for getting all routes
     *
     * @return RouteSearchBean which contains info
     * about route and list stations
     * @throws DBException
     */
    public List<RouteSearchBean> getAllRoute() throws DBException {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rsStat = null;

        List<RouteSearchBean> routes = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Queries.SQL_SELECT_ALL_ROUTES_BY_ID);
            while (rs.next()) {
                int idRoute = rs.getInt("id");
                pstmt = conn.prepareStatement(Queries.SQL_SELECT_NAME_FROM_ROUTE_STATIONS);
                pstmt.setInt(1, idRoute);
                rsStat = pstmt.executeQuery();

                RouteSearchBean routeTemp = new RouteSearchBean();
                routeTemp.setIdRoute(idRoute);

                List<RouteStation> stations = new ArrayList<>();
                while (rsStat.next()) {
                    RouteStation stationTemp = new RouteStation();
                    stationTemp.setName(rsStat.getString("name"));
                    stations.add(stationTemp);
                }
                routeTemp.setStationList(stations);
                routes.add(routeTemp);
            }

            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_GET_ROUTE, e);
            throw new DBException(Messages.ERR_CANNOT_GET_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return routes;
    }

    /**
     * This method is used for getting ID route on date by
     * minimum set of data for searching.
     *
     * @param cityStart City from
     * @param cityEnd   city destination
     * @param date      date depart
     * @return list of routes
     * @throws DBException
     */
    public List<Route> getIdRouteOnDate(String cityStart, String cityEnd, Date date) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Route> routes = new ArrayList<>();
        Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        if (date.toLocalDate().compareTo(sqlDate.toLocalDate().minusDays(1)) > 0) {

            try {
                pstmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_ID);

                int atr = 1;
                pstmt.setString(atr++, String.valueOf(date));
                pstmt.setString(atr++, cityStart);
                pstmt.setString(atr, cityEnd);
                rs = pstmt.executeQuery();

                Route routeTemp = null;
                Train trainTemp = null;
                Integer commonPrice = 0;
                while (rs.next()) {
                    routeTemp = new Route();
                    trainTemp = new Train();

                    trainTemp.setId(rs.getInt("id"));
                    trainTemp.setNumber(rs.getInt("number"));
                    routeTemp.setId(rs.getInt("id_route"));
                    routeTemp.setTrain(trainTemp);
                    routes.add(routeTemp);
                }
                conn.commit();

            } catch (SQLException e) {
                DBUtil.rollback(conn);
                e.printStackTrace();
                throw new DBException(Messages.ERR_GET_ROUTE_ON_DATE, e);
            } finally {
                DBUtil.close(rs);
                DBUtil.close(pstmt);
            }
        }
        return routes;
    }

    /**
     * This method is used for getting all stations by Route ID
     *
     * @param idRoute
     * @return
     * @throws DBException
     */
    public List<String> getAllStationsByRouteId(Integer idRoute) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<String> stations = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_ALL_STATIONS_BY_ROUTE_ID);
            pstmt.setInt(1, idRoute);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                stations.add(rs.getString("name"));
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_GET_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_GET_STATION, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        LOG.trace("List stations by Id -> " + stations);
        return stations;
    }

    @Override
    public RouteStation get(long id) {
        return null;
    }

    @Override
    public List<RouteStation> getAll() {
        return null;
    }

    /**
     * This method is used for saving route with train
     *
     * @param trainNumber train Number
     * @throws DBException
     */
    public void saveOneRoute(Integer trainNumber) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_FIND_COUNT_ROUTES);

            int atr = 1;
            Integer idRoute = 1;

            TrainDao trainDao = new TrainDao(conn);
            Integer idTrain = trainDao.getIdTrainByNumber(trainNumber);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute += rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_ROUTE);
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr, idTrain);
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_ROUTE_WAS_NOT_SAVED, e);
            throw new DBException(Messages.ERR_ROUTE_WAS_NOT_SAVED, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }


    /**
     * This method is used for saving route with stations
     *
     * @param routeStation object, which contains info about stations
     * @throws DBException
     */
    @Override
    public void save(RouteStation routeStation) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idTrain = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_FIND_COUNT_ROUTES);

            int atr = 1;
            int idRoute = 0;

            TrainDao trainDao = new TrainDao(conn);
            idTrain = trainDao.getIdTrainByNumber(routeStation.getIdTrain());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute = rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_ROUTE_STATIONS);

            LOG.trace("Before timestamp date : " + routeStation.getTimeEnd());
            String dateStart = Timestamp.valueOf(routeStation.getTimeStart()).toString().split("\\.")[0];
            dateStart = dateStart.substring(0, dateStart.length() - 3);
            String dateEnd = Timestamp.valueOf(routeStation.getTimeEnd()).toString().split("\\.")[0];
            dateEnd = dateEnd.substring(0, dateEnd.length() - 3);
            LOG.trace("After timestamp date : " + dateEnd);

            pstmt.setInt(atr++, idTrain);
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr++, getIdStationByName(routeStation.getName()));
            pstmt.setString(atr++, dateStart);
            pstmt.setString(atr++, dateEnd);
            pstmt.setInt(atr, routeStation.getPrice());

            pstmt.executeUpdate();
            conn.commit();

            LOG.trace("Route was saved!");
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
            throw new DBException(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(RouteStation routeStation, String[] params) {

    }

    /**
     * This method is used for deleting route from DB
     *
     * @param routeStation object, which contains info about stations
     * @throws DBException
     */
    @Override
    public void delete(RouteStation routeStation) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_ROUTE);

            pstmt.setInt(1, routeStation.getIdRoute());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_DELETE_ROUTE, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_ROUTE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    public List<UserStatisticBean> getAllUserInfo() throws DBException {
        Statement stmt = null;

        List<UserStatisticBean> usBeanList = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.SQL_SELECT_ALL_USER_INFO);

            while (rs.next()) {
                UserStatisticBean usTemp = new UserStatisticBean();
                usTemp.setLogin(rs.getString("login"));
                usTemp.setCountTicket(rs.getString("countTicket"));
                usBeanList.add(usTemp);
            }

            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            LOG.error(Messages.ERR_CANNOT_SHOW_USER_INFO, e);
            throw new DBException(Messages.ERR_CANNOT_SHOW_USER_INFO, e);
        } finally {
            DBUtil.close(stmt);
        }
        return usBeanList;
    }
}
