package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StationDao implements Dao<Station> {

    private static final Logger LOG = Logger.getLogger(StationDao.class);

    public static List<RouteStation> getAllStationByRoute(String cityStart, String cityEnd, Date date, Integer id) {
        List<RouteStation> stations = new ArrayList<>();
        RouteStation stationTemp = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_BY_ROUTE_ID);
        ) {
            int atr = 1;
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stationTemp = new RouteStation();
                stationTemp.setIdStation(rs.getInt("id"));
                stationTemp.setName(rs.getString("name"));
                stationTemp.setIdCity(rs.getInt("id_city"));
                stationTemp.setDateEnd(rs.getDate("date_end"));
                stationTemp.setTimeStart(TimeUtil.getDateTimeWithTimeZone(
                        rs.getTimestamp("time_date_start").toLocalDateTime()));
                stationTemp.setTimeEnd(TimeUtil.getDateTimeWithTimeZone(
                        rs.getTimestamp("time_date_end").toLocalDateTime()));
                stationTemp.setIdTrain(rs.getInt("id_train"));
                stations.add(stationTemp);

                LOG.trace("Time Start : " + LocalDateTime.ofInstant(
                        stationTemp.getTimeStart().toInstant(ZoneOffset.MIN), ZoneId.of(TimeUtil.getTimeZone())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    @Override
    public Station get(long id) {
        return null;
    }

    @Override
    public List<Station> getAll() {
        List<Station> stations = new ArrayList<>();
        Station stationTemp = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM stations;");
            while (rs.next()) {
                stationTemp = new Station();
                stationTemp.setId(rs.getInt("id"));
                stationTemp.setName(rs.getString("name"));
                stationTemp.setIdCity(rs.getInt("id_city"));
                stations.add(stationTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    @Override
    public void save(Station station) {

    }

    @Override
    public void update(Station station, String[] params) {

    }

    @Override
    public void delete(Station station) {

    }
}
