package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDao implements Dao<Train> {


    public static Integer getMaxSizeFromCarriageByTrain(Integer trainNum, Integer carrNum) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer maxSize = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();

            pstmt = conn.prepareStatement("SELECT * FROM carriages C INNER JOIN Trains T ON C.id_train = T.id\n" +
                    " WHERE C.num_carriage = ?" +
                    " AND T.number = ?");
            int atr = 1;
            pstmt.setInt(atr++, carrNum);
            pstmt.setInt(atr, trainNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxSize = rs.getInt("max_size");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_MAX_SIZE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

        return maxSize;
    }

    public static void saveTrainContent(Integer idTrain, Integer countCarr, Integer countSeat, Integer idType) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            int atr = 1;
            Integer countCarriage = 0;
            pstmt = conn.prepareStatement("SELECT Count(num_carriage) as cnt FROM carriages WHERE id_train = ?");
            pstmt.setInt(atr, idTrain);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                countCarriage = rs.getInt("cnt");
                countCarriage++;
            }
            countCarr += countCarriage;
            for (int numCarr = countCarriage; numCarr < countCarr; ++numCarr) {
                atr = 1;
                pstmt = conn.prepareStatement("INSERT INTO carriages(id_train, num_carriage,id_type, max_size) VALUES(?,?,?,?)");
                pstmt.setInt(atr++, idTrain);
                pstmt.setInt(atr++, numCarr);
                pstmt.setInt(atr++, idType);
                pstmt.setInt(atr, countSeat);
                pstmt.executeUpdate();
                for (int numSeat = 1; numSeat <= countSeat; ++numSeat) {
                    atr = 1;
                    pstmt = conn.prepareStatement("INSERT INTO seats(id_train, num_carriage,num_seat) VALUES(?,?,?)");
                    pstmt.setInt(atr++, idTrain);
                    pstmt.setInt(atr++, numCarr);
                    pstmt.setInt(atr, numSeat);
                    pstmt.executeUpdate();
                }
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_SAVE_NEW_CARRIAGE_AND_SEAT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public static List<TrainStatisticBean> getTrainsStatistic() throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<TrainStatisticBean> trainStatistic = new ArrayList<>();

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_COUNT_CARRIAGES_AND_SEATS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TrainStatisticBean trainStatTemp = new TrainStatisticBean();
                trainStatTemp.setTrainNumber(rs.getInt("number"));
                trainStatTemp.setCountCarriages(rs.getInt("carriages"));
                trainStatTemp.setCountSeats(rs.getInt("seats"));
                trainStatistic.add(trainStatTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_INFO_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return trainStatistic;
    }

    public static void deleteAllTrainContent(Integer idTrain) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("DELETE FROM seats WHERE id_train = ? ");
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("DELETE FROM carriages WHERE id_train = ?;");
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_DELETE_ALL_TRAINS_CONTENT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public static Integer getIdTrainByNumber(Integer number) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idTrain = null;
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("SELECT id FROM trains WHERE number = ?");
            pstmt.setInt(1, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idTrain = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }

        return idTrain;
    }

    @Override
    public Train get(long id) {
        return null;
    }

    @Override
    public List<Train> getAll() throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Train> trains = new ArrayList<>();
        Train trainTemp = null;
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM trains;");
            while (rs.next()) {
                trainTemp = new Train();
                trainTemp.setId(rs.getInt("id"));
                trainTemp.setNumber(rs.getInt("number"));
                trains.add(trainTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_ALL_TRAINS, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return trains;
    }

    @Override
    public void save(Train train) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("INSERT INTO trains(number) VALUES(?)");
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_SAVE_NEW_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    @Override
    public void update(Train train, String[] params) {

    }

    @Override
    public void delete(Train train) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("DELETE FROM trains WHERE number = ?");
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_DELETE_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }
}
