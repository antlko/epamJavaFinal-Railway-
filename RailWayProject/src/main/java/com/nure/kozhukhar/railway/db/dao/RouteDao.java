package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteOnDate;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RouteDao implements Dao<RouteStation> {

    private static final Logger LOG = Logger.getLogger(RouteDao.class);


    public static Integer getIdStationByName(String name) {
        Integer idStation = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM stations WHERE name = ?");
        ) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idStation = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idStation;
    }

    public static void saveStationByRouteId(Integer idRoute, LocalDateTime dateStart, LocalDateTime dateEnd) {
        PreparedStatement pstmt = null;

        List<RouteStation> stationList = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
        ) {
            pstmt = conn.prepareStatement("SELECT * FROM routes_station WHERE id_route = ?");
            pstmt.setInt(1, idRoute);
            ResultSet rs = pstmt.executeQuery();
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
                routeStationTemp.setTimeEnd(TimeUtil.getDateTimeWithTimeZone(
                        rs.getTimestamp("time_end").toLocalDateTime())
                );
                routeStationTemp.setTimeEnd(dateTime.minusHours(2));
                stationList.add(routeStationTemp);

                LOG.trace("Time with time zone for adding new route -> " + routeStationTemp.getTimeStart());
                LOG.trace("Time without time zone for adding new route -> "
                        + rs.getTimestamp("time_start").toLocalDateTime());
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
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<RouteSearchBean> getAllRoute() {
        List<RouteSearchBean> routes = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT id FROM routes;");
            while (rs.next()) {
                int idRoute = rs.getInt("id");
                PreparedStatement pstmt = conn.prepareStatement("SELECT name from routes_station RS INNER JOIN Stations S ON RS.id_station = S.id WHERE id_route = ? ORDER BY time_end ");
                pstmt.setInt(1, idRoute);
                ResultSet rsStat = pstmt.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static List<RouteOnDate> getRouteOnDate(String cityStart, String cityEnd, Date date) {
        List<RouteOnDate> routes = new ArrayList<>();
        Station stationTemp = null;
        RouteOnDate routeTemp = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_AND_CITIES);
        ) {

            int atr = 1;
            LOG.trace("Booking [date, cityStart, cityEnd] in DAO : " + date + ", " + cityStart + " <-> " + cityEnd);
            LOG.trace("Date in string : " + date);

            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr, cityEnd);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stationTemp = new Station();
                routeTemp = new RouteOnDate();

                routeTemp.setDateEnd(rs.getDate("date_end"));
                routeTemp.setTimeDateStart(rs.getDate("time_date_start"));
                routeTemp.setTimeDateStart(rs.getDate("time_date_end"));

                stationTemp.setId(rs.getInt("id_station"));
                stationTemp.setName(rs.getString("name"));
                routeTemp.setStation(stationTemp);

                LOG.trace("Booking rote temp: " + routeTemp);
                routes.add(routeTemp);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static List<Route> getIdRouteOnDate(String cityStart, String cityEnd, Date date) {
        List<Route> routes = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
        ) {
            PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_ID);
            int atr = 1;
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr++, cityEnd);
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr, cityEnd);
            ResultSet rs = pstmt.executeQuery();

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
//                commonPrice+=rs.getInt("price");
                routes.add(routeTemp);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }


    @Override
    public RouteStation get(long id) {
        return null;
    }

    @Override
    public List<RouteStation> getAll() {
        return null;
    }

    public static void saveOneRoute(Integer trainNumber) {
        PreparedStatement pstmt = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection()
        ) {
            pstmt = conn.prepareStatement("SELECT Count(DISTINCT id) as idRoute FROM routes");

            int atr = 1;
            Integer idRoute = 1;
            Integer idTrain = TrainDao.getIdTrainByNumber(trainNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute += rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement("INSERT INTO routes(id, id_train) VALUES (?,?)");
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr, idTrain);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(RouteStation routeStation) {
        PreparedStatement pstmt = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection()
        ) {
            pstmt = conn.prepareStatement("SELECT Count(DISTINCT id) as idRoute FROM routes");

            int atr = 1;
            Integer idRoute = 0;
            Integer idTrain = TrainDao.getIdTrainByNumber(routeStation.getIdTrain());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idRoute = rs.getInt("idRoute");
            }
            pstmt = conn.prepareStatement("" +
                    "INSERT INTO routes_station(id_train, id_route, id_station, time_start, time_end, price) " +
                    "VALUES(?,?,?, ?, ?, ?);");

            LOG.trace("Before timestamp date : " + routeStation.getTimeStart());
            String dateStart = Timestamp.valueOf(routeStation.getTimeStart()).toString().split("\\.")[0];
            dateStart = dateStart.substring(0, dateStart.length() - 3);
            String dateEnd = Timestamp.valueOf(routeStation.getTimeEnd()).toString().split("\\.")[0];
            dateEnd = dateEnd.substring(0, dateStart.length() - 3);
            LOG.trace("After timestamp date : " + dateStart);

            pstmt.setInt(atr++, idTrain);
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr++, getIdStationByName(routeStation.getName()));
            pstmt.setString(atr++, dateStart);
            pstmt.setString(atr++, dateEnd);
            pstmt.setInt(atr, routeStation.getPrice());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RouteStation routeStation, String[] params) {

    }

    @Override
    public void delete(RouteStation routeStation) {

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM routes WHERE id = ?");
        ) {
            pstmt.setInt(1, routeStation.getIdRoute());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
