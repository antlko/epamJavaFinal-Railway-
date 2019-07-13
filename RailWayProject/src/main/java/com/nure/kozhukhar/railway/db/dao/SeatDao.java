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
        List<SeatSearchBean> seatsInfoBusy = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_SEAT_FREE_INFO);
        ) {
            int atr = 1;
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setInt(atr++, id);
            stmt.setString(atr++, cityStart);
            stmt.setInt(atr++, id);
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setInt(atr++, id);
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, id);
            stmt.setInt(atr++, id);
            stmt.setInt(atr, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SeatSearchBean seatTemp = new SeatSearchBean();
                seatTemp.setSeatType(rs.getString("nameType"));
                seatTemp.setFree(rs.getInt("newFree"));
                LOG.trace("SeatDao getting info [" + seatTemp.getSeatType() + ", " + seatTemp.getFree() + "]");
                seatsInfoBusy.add(seatTemp);
            }

            LOG.trace("Seat info test : " + seatsInfoBusy);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatsInfoBusy;
    }

    public static List<SeatSearchBean> getMaxAllCarrSizeByIdRoute(Integer idRoute) {
        List<SeatSearchBean> maxSeatList = new ArrayList<>();
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT TP.name as nameType, SUM(max_size) as maxCarr" +
                     " FROM carriages C, Trains T, Types TP, Routes R\n" +
                     "WHERE C.id_train = T.id\n" +
                     "\tAND C.id_type = TP.id\n" +
                     "    AND R.id_train = T.id\n" +
                     "    AND R.id = ? " +
                     "GROUP BY TP.name");
        ) {
            pstmt.setInt(1, idRoute);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SeatSearchBean searchBeanTemp = new SeatSearchBean();
                searchBeanTemp.setSeatType(rs.getString("nameType"));
                searchBeanTemp.setMaxSize(rs.getInt("maxCarr"));
                maxSeatList.add(searchBeanTemp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxSeatList;
    }

    public static String getStationAllSeatsByIDRoute(
            String cityStart, String cityEnd, Date date, Integer idRoute) {

        String station = "";

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_SELECT_MIN_COUNT_STATION_ON_ROUTE);
        ) {
            int atr = 1;
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setString(atr++, cityStart);
            pstmt.setString(atr++, String.valueOf(date));
            pstmt.setInt(atr++, idRoute);
            pstmt.setString(atr++, cityEnd);
            pstmt.setInt(atr++, idRoute);
            pstmt.setInt(atr++, idRoute);
            pstmt.setString(atr++, cityEnd);
            pstmt.setString(atr, cityStart);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                station = rs.getString("nmSt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return station;
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
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, idTrain);
            stmt.setString(atr++, type);
            stmt.setString(atr++, cityEnd);
            stmt.setString(atr++, cityStart);
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

    public static String getStationWithMinSeats(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain, Integer idCarriage) {

        String stat = "";
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_CITY_WITH_MIN_VALUE_ON_ROUTE);
        ) {
            int atr = 1;
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityStart);
            stmt.setString(atr++, String.valueOf(date));
            stmt.setString(atr++, cityEnd);
            stmt.setInt(atr++, idTrain);
            stmt.setInt(atr++, idCarriage);
            stmt.setString(atr++, type);
            stmt.setString(atr++, cityEnd);
            stmt.setString(atr, cityStart);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stat = rs.getString("nameStation");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }


    public static List<Integer> getAllSeatsByCarriageTypeAndNum(
            String cityStart, String cityEnd, String type, Date date, Integer idTrain, Integer idCarriage) {

        List<Integer> seats = new ArrayList<>();

        Integer maxSize = TrainDao.getMaxSizeFromCarriageByTrain(idTrain, idCarriage);
        LOG.trace("Max size - > " + maxSize);
        if (maxSize != null) {
            try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(Queries.SQL_FIND_FALSE_SEATS_BY_TRAIN_AND_CARRIAGE);
            ) {
                List<Integer> busySeats = new ArrayList<>();

                int atr = 1;
                stmt.setString(atr++, cityStart);
                stmt.setString(atr++, String.valueOf(date));
                stmt.setString(atr++, cityStart);
                stmt.setString(atr++, cityStart);
                stmt.setString(atr++, String.valueOf(date));
                stmt.setString(atr++, cityEnd);
                stmt.setInt(atr++, idTrain);
                stmt.setInt(atr++, idCarriage);
                stmt.setString(atr, type);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    busySeats.add(rs.getInt("numSeat"));
                }

                for (int i = 1; i <= maxSize; ++i) {
                    if (!busySeats.contains(i)) {
                        seats.add(i);
                    }
                }
                LOG.trace("Count seats -> " + seats.size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        LOG.trace("INFO all seats : " + seats + ", maxSize -> " + maxSize);
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
