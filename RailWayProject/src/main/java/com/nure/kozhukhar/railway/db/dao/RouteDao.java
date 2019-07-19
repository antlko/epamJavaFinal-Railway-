package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteOnDate;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RouteDao implements Dao<RouteStation> {

    private Connection conn;

    public RouteDao(Connection conn) {
        this.conn = conn;
    }

    private static final Logger LOG = Logger.getLogger(RouteDao.class);


    public Integer getIdStationByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idStation = null;
        try {
            pstmt = conn.prepareStatement("SELECT id FROM stations WHERE name = ?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idStation = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_STATION, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return idStation;
    }

    public void saveStationByRouteId(Integer idRoute, LocalDateTime dateStart, LocalDateTime dateEnd) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<RouteStation> stationList = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement("SELECT * FROM routes_station WHERE id_route = ?");
            pstmt.setInt(1, idRoute);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RouteStation routeStationTemp = new RouteStation();
                routeStationTemp.setIdTrain(rs.getInt("id_train"));
                routeStationTemp.setIdRoute(rs.getInt("id_route"));
                routeStationTemp.setIdStation(rs.getInt("id_station"));

                String date = rs.getObject("time_start").toString().split("\\.")[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                routeStationTemp.setTimeStart(dateTime.minusHours(2));

                date = rs.getObject("time_end").toString().split("\\.")[0];
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dateTime = LocalDateTime.parse(date, formatter);
                routeStationTemp.setTimeEnd(dateTime.minusHours(2));

                stationList.add(routeStationTemp);

                LOG.trace("Time with time zone for adding new route -> " + routeStationTemp.getTimeEnd());
                LOG.trace("Time without time zone for adding new route -> "
                        + rs.getTimestamp("time_end").toLocalDateTime());
            }

            pstmt = conn.prepareStatement("" +
                    "INSERT INTO routes_on_date(date_end, id_train, id_route, id_station, time_date_start, time_date_end) " +
                    "VALUES(?,?,?,?, ?, ?);");

            List<String> newDateStart = new ArrayList<>();
            List<String> newDateEnd = new ArrayList<>();

            int daysElapsed = 0;
            Period period = Period.between(dateStart.toLocalDate(), dateEnd.toLocalDate());
            int countOfDays = period.getDays();
            int atr = 1;

            LOG.trace("countOfDays = " + countOfDays);
            if(stationList.size() > 0) {
                for (int j = 0; j <= countOfDays; ++j) {
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
            } else  {
                throw new SQLException();
            }
            conn.commit();

            LOG.debug("Route on date saved!");
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    public List<RouteSearchBean> getAllRoute() throws DBException {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rsStat = null;

        List<RouteSearchBean> routes = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT id FROM routes;");
            while (rs.next()) {
                int idRoute = rs.getInt("id");
                pstmt = conn.prepareStatement("SELECT name from routes_station RS INNER JOIN Stations S ON RS.id_station = S.id WHERE id_route = ? ORDER BY time_end ");
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
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return routes;
    }


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

    public List<String> getAllStationsByRouteId(Integer idRoute) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<String> stations = new ArrayList<>();

        try {
            pstmt = conn.prepareStatement("SELECT * FROM routes_station RS INNER JOIN stations S ON RS.id_station = S.id WHERE id_route = ? ORDER BY time_end");
            pstmt.setInt(1, idRoute);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                stations.add(rs.getString("name"));
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
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

    public void saveOneRoute(Integer trainNumber) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement("SELECT Count(DISTINCT id) as idRoute FROM routes");

            int atr = 1;
            Integer idRoute = 1;

            TrainDao trainDao = new TrainDao(conn);
            Integer idTrain = trainDao.getIdTrainByNumber(trainNumber);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute += rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement("INSERT INTO routes(id, id_train) VALUES (?,?)");
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr, idTrain);
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_ROUTE_WAS_NOT_SAVED, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void save(RouteStation routeStation) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idTrain = null;

        try {
            pstmt = conn.prepareStatement("SELECT Count(DISTINCT id) as idRoute FROM routes");

            int atr = 1;
            Integer idRoute = 0;

            TrainDao trainDao = new TrainDao(conn);
            idTrain = trainDao.getIdTrainByNumber(routeStation.getIdTrain());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute = rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement("" +
                    "INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) " +
                    "VALUES(?,?,?, ?, ?, ?);");

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
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_SAVE_STATION_IN_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(RouteStation routeStation, String[] params) {

    }

    @Override
    public void delete(RouteStation routeStation) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM routes WHERE id = ?");

            pstmt.setInt(1, routeStation.getIdRoute());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_DELETE_ROUTE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
