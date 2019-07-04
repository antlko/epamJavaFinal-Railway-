package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteOnDate;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RouteDao implements Dao<RouteStation> {

    private static final Logger LOG = Logger.getLogger(RouteDao.class);

    public static List<RouteOnDate> getRouteOnDate(String cityStart, String cityEnd, Date date) {
        List<RouteOnDate> routes = new ArrayList<>();
        Station stationTemp = null;
        RouteOnDate routeTemp = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
        ) {
            PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_AND_CITIES);
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

    @Override
    public void save(RouteStation routeStation) {

    }

    @Override
    public void update(RouteStation routeStation, String[] params) {

    }

    @Override
    public void delete(RouteStation routeStation) {

    }
}
