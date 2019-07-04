package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.entity.Seat;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
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

    public static List<SeatSearchBean> getAllSeatsByCarriageType(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain) {

        List<SeatSearchBean> seatsInfo = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_FREE_SEATS_BY_TRAIN);
        ) {
            int atr = 1;
            LOG.trace("Info to query : " + cityStart
                    + ", " + cityEnd + ", " + date + ", " + idTrain + ", " + type + "; ");
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, idTrain);
            stmt.setString(atr++, type);
            stmt.setString(atr, cityStart);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SeatSearchBean seatTemp = new SeatSearchBean();
                seatTemp.setNumCarriage(rs.getInt("numCarr"));
                seatTemp.setNumTrain(rs.getInt("idTrain"));
                seatTemp.setSeatType(rs.getString("nameType"));
                seatTemp.setPriceSeat(rs.getInt("tpPrice"));
                seatsInfo.add(seatTemp);
                LOG.trace("seat Temp :" + seatTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatsInfo;
    }

    public static List<Integer> getAllSeatsByCarriageTypeAndNum(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain, Integer idCarriage) {

        List<Integer> seats = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_FREE_SEATS_BY_TRAIN_AND_CARRIAGE);
        ) {
            int atr = 1;
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, idTrain);
            stmt.setInt(atr++, idCarriage);
            stmt.setString(atr++, type);
            stmt.setString(atr, cityStart);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                seats.add(rs.getInt("numSeat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public static Integer getTypePriceByTypeName(String name) {
        int price = 0;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_SELECT_TYPE_PRICE);
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
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
