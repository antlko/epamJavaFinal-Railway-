package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.entity.Seat;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class SeatDao implements Dao<Seat> {

    private static final Logger LOG = Logger.getLogger(SeatDao.class);

    public static List<SeatSearchBean> getSeatCountInfo(String cityStart, String cityEnd, Date date, Integer id) {
        List<SeatSearchBean> seatsInfo = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_SEAT_FREE_INFO);
        ) {
            int atr = 1;
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, id);
            stmt.setString(atr, cityStart);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SeatSearchBean seatTemp = new SeatSearchBean();
                seatTemp.setSeatType(rs.getString("nameType"));
                seatTemp.setFree(rs.getInt("free"));
                LOG.trace("SeatDao getting info [" + seatTemp.getSeatType() + ", " + seatTemp.getFree() + "]");
                seatsInfo.add(seatTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatsInfo;
    }

    @Override
    public Seat get(long id) {
        return null;
    }

    @Override
    public List<Seat> getAll() {
        return null;
    }

    @Override
    public void save(Seat seat) {

    }

    @Override
    public void update(Seat seat, String[] params) {

    }

    @Override
    public void delete(Seat seat) {

    }
}
