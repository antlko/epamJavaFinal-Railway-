package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.entity.Seat;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDao implements Dao<Seat> {

    private static final Logger LOG = Logger.getLogger(SeatDao.class);

    public static List<SeatSearchBean> getSeatCountInfo(String cityStart, String cityEnd, Date date, Integer id) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<SeatSearchBean> seatsInfoBusy = new ArrayList<>();

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement(Queries.SQL_FIND_SEAT_FREE_INFO);
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
            pstmt.setInt(atr++, id);
            pstmt.setInt(atr, id);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                SeatSearchBean seatTemp = new SeatSearchBean();
                seatTemp.setSeatType(rs.getString("nameType"));
                seatTemp.setFree(rs.getInt("newFree"));
                LOG.trace("SeatDao getting info [" + seatTemp.getSeatType() + ", " + seatTemp.getFree() + "]");
                seatsInfoBusy.add(seatTemp);
            }

            LOG.trace("Seat info test : " + seatsInfoBusy);
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_COUNT_ALL_FREE_SEAT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return seatsInfoBusy;
    }

    public static List<SeatSearchBean> getAllSeatsByCarriageType(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        List<SeatSearchBean> seatsInfo = new ArrayList<>();

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement(Queries.SQL_FIND_FREE_SEATS_BY_TRAIN);
            int atr = 1;
            LOG.trace("Info to query : " + cityStart
                    + ", " + cityEnd + ", " + date + ", " + idTrain + ", " + type + "; ");
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr++, cityEnd);
            pstmt.setInt(atr++, idTrain);
            pstmt.setString(atr++, type);
            pstmt.setString(atr++, cityEnd);
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr, cityStart);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SeatSearchBean seatTemp = new SeatSearchBean();
                seatTemp.setNumCarriage(rs.getInt("numCarr"));
                seatTemp.setNumTrain(rs.getInt("idTrain"));
                seatTemp.setSeatType(rs.getString("nameType"));
                seatTemp.setPriceSeat(rs.getInt("tpPrice"));
                seatsInfo.add(seatTemp);
                LOG.trace("seat Temp :" + seatTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SEAT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return seatsInfo;
    }


    public static List<Integer> getAllSeatsByCarriageTypeAndNum(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain, Integer idCarriage) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Integer> seats = new ArrayList<>();
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement(Queries.SQL_FIND_FALSE_SEATS_BY_TRAIN_AND_CARRIAGE);

            Integer maxSize = TrainDao.getMaxSizeFromCarriageByTrain(idTrain, idCarriage);

            LOG.trace("Max size - > " + maxSize);
            if (maxSize != null) {
                List<Integer> busySeats = new ArrayList<>();
                int atr = 1;
                pstmt.setString(atr++, cityStart);
                pstmt.setString(atr++, String.valueOf(date));
                pstmt.setString(atr++, cityStart);
                pstmt.setString(atr++, cityStart);
                pstmt.setString(atr++, String.valueOf(date));
                pstmt.setString(atr++, cityEnd);
                pstmt.setInt(atr++, idTrain);
                pstmt.setInt(atr++, idCarriage);
                pstmt.setString(atr, type);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    busySeats.add(rs.getInt("numSeat"));
                }

                for (int i = 1; i <= maxSize; ++i) {
                    if (!busySeats.contains(i)) {
                        seats.add(i);
                    }
                }
                LOG.trace("Count seats -> " + seats.size());
                conn.commit();
            }
            LOG.trace("INFO all seats : " + seats + ", maxSize -> " + maxSize);
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_ALL_SEAT, e);
        } catch (DBException e) {
            throw new DBException(Messages.ERR_GET_MAX_SIZE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

        return seats;
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
