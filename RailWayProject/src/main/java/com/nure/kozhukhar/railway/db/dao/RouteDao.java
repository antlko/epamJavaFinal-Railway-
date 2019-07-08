package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteOnDate;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
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

    public static List<RouteSearchBean> getAllRoute() {
        List<RouteSearchBean> routes = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT id FROM routes;");
            while (rs.next()) {
                int idRoute = rs.getInt("id");
                PreparedStatement pstmt = conn.prepareStatement("SELECT name from routes_station RS INNER JOIN Stations S ON RS.id_station = S.id WHERE id_route = ?");
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

            String dateStart = Timestamp.valueOf(routeStation.getTimeStart()).toString().split("\\.")[0];
            String dateEnd = Timestamp.valueOf(routeStation.getTimeEnd()).toString().split("\\.")[0];

            pstmt.setInt(atr++, idTrain);
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr++, getIdStationByName(routeStation.getName()));
            pstmt.setString(atr++, dateStart);
            pstmt.setString(atr++, dateEnd);
            pstmt.setInt(atr, routeStation.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RouteStation routeStation, String[] params) {

    }

    @Override
    public void delete(RouteStation routeStation) {

    }
}
