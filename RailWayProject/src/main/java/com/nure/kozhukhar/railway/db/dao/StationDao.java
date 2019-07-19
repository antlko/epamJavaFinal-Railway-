package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
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

    private Connection conn;

    public StationDao(Connection conn) {
        this.conn = conn;
    }

    private static final Logger LOG = Logger.getLogger(StationDao.class);

    public List<RouteStation> getAllStationByRoute(String cityStart, String cityEnd, Date date, Integer id) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<RouteStation> stations = new ArrayList<>();
        RouteStation stationTemp = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_FIND_ROUTE_ON_DATE_BY_ROUTE_ID);
            int atr = 1;

            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setInt(atr++, id);
            pstmt.setString(atr++, cityStart);
            pstmt.setInt(atr++, id);
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setInt(atr++, id);
            pstmt.setString(atr++, cityEnd);
            pstmt.setInt(atr++, id);
            pstmt.setInt(atr, id);
            rs = pstmt.executeQuery();
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
                stationTemp.setPrice(rs.getInt("price"));
                stations.add(stationTemp);

                LOG.trace("Checking station : " + stationTemp.getIdStation() + ", " + stationTemp.getTimeEnd());
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_STATIONS_IN_ROUTE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        LOG.trace("Received stations -> " + stations);
        return stations;
    }

    @Override
    public Station get(long id) {
        return null;
    }

    @Override
    public List<Station> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<Station> stations = new ArrayList<>();
        Station stationTemp = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stations;");
            while (rs.next()) {
                stationTemp = new Station();
                stationTemp.setId(rs.getInt("id"));
                stationTemp.setName(rs.getString("name"));
                stationTemp.setIdCity(rs.getInt("id_city"));
                stations.add(stationTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_STATIONS, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
        }
        return stations;
    }

    @Override
    public void save(Station station) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO stations(name,id_city) VALUES(?,?)");

            int atr = 1;
            pstmt.setString(atr++, station.getName());
            pstmt.setInt(atr, station.getIdCity());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_CANNOT_SAVE_STATION, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(Station station, String[] params) {

    }

    @Override
    public void delete(Station station) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM stations WHERE name = ?");

            pstmt.setString(1, station.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_DELETE_STATION, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
