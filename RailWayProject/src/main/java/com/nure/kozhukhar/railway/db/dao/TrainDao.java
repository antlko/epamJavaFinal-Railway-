package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Object which operate Train information from DB
 * Have a {@link TrainDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class TrainDao implements Dao<Train> {

    private static final Logger LOG = Logger.getLogger(StationDao.class);

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public TrainDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method is used for getting max size of carriage
     *
     * @param trainNum train Number
     * @param carrNum carriage Number
     * @return Size
     * @throws DBException
     */
    public Integer getMaxSizeFromCarriageByTrain(Integer trainNum, Integer carrNum) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer maxSize = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_MAX_SIZE_FROM_CARRIAGE_BY_TRAIN);
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
            LOG.error(Messages.ERR_GET_MAX_SIZE, e);
            throw new DBException(Messages.ERR_GET_MAX_SIZE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return maxSize;
    }

    /**
     * This method is used for saving train content
     * (carriages, seats)
     * @param idTrain ID Train
     * @param countCarr Count of carriages
     * @param countSeat count of seats in this carriage
     * @param idType ID Type of carriage
     * @throws DBException
     */
    public void saveTrainContent(Integer idTrain, Integer countCarr, Integer countSeat, Integer idType) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            int atr = 1;
            Integer countCarriage = 0;
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_COUNT_CARRIAGES);
            pstmt.setInt(atr, idTrain);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                countCarriage = rs.getInt("cnt");
                countCarriage++;
            }
            countCarr += countCarriage;
            for (int numCarr = countCarriage; numCarr < countCarr; ++numCarr) {
                atr = 1;
                pstmt = conn.prepareStatement(Queries.SQL_SAVE_CARRIAGES);
                pstmt.setInt(atr++, idTrain);
                pstmt.setInt(atr++, numCarr);
                pstmt.setInt(atr++, idType);
                pstmt.setInt(atr, countSeat);
                pstmt.executeUpdate();
                for (int numSeat = 1; numSeat <= countSeat; ++numSeat) {
                    atr = 1;
                    pstmt = conn.prepareStatement(Queries.SQL_SAVE_SEATS);
                    pstmt.setInt(atr++, idTrain);
                    pstmt.setInt(atr++, numCarr);
                    pstmt.setInt(atr, numSeat);
                    pstmt.executeUpdate();
                }
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_SAVE_NEW_CARRIAGE_AND_SEAT, e);
            throw new DBException(Messages.ERR_CANNOT_SAVE_NEW_CARRIAGE_AND_SEAT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for getting train statistic
     * (type of carriage, count of seats) in the train
     *
     * @return TrainStatisticBean
     * @throws DBException
     */
    public List<TrainStatisticBean> getTrainsStatistic() throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<TrainStatisticBean> trainStatistic = new ArrayList<>();

        try {
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
            LOG.error(Messages.ERR_CANNOT_GET_INFO_TRAIN, e);
            throw new DBException(Messages.ERR_CANNOT_GET_INFO_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return trainStatistic;
    }

    /**
     * This method is used for deleting train
     *
     * @param idTrain ID train
     * @throws DBException
     */
    public void deleteAllTrainContent(Integer idTrain) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_SEATS);
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_CARRIAGES);
            pstmt.setInt(1, idTrain);
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_DELETE_ALL_TRAINS_CONTENT, e);
            throw new DBException(Messages.ERR_DELETE_ALL_TRAINS_CONTENT, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for getting train by number
     *
     * @param number number of train
     * @return ID train
     * @throws DBException
     */
    public Integer getIdTrainByNumber(Integer number) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idTrain = null;
        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_ID_TRAIN_BY_NUM);
            pstmt.setInt(1, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idTrain = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_TRAIN, e);
            throw new DBException(Messages.ERR_GET_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return idTrain;
    }

    @Override
    public Train get(long id) {
        return null;
    }

    /**
     * @return list of Train objects
     * @throws DBException
     */
    @Override
    public List<Train> getAll() throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Train> trains = new ArrayList<>();
        Train trainTemp = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(Queries.SQL_GET_ALL_TRAINS);
            while (rs.next()) {
                trainTemp = new Train();
                trainTemp.setId(rs.getInt("id"));
                trainTemp.setNumber(rs.getInt("number"));
                trains.add(trainTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_ALL_TRAINS, e);
            throw new DBException(Messages.ERR_GET_ALL_TRAINS, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return trains;
    }

    /**
     * Save new train
     * @param train object of Train
     * @throws DBException
     */
    @Override
    public void save(Train train) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_TRAINS);
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_SAVE_NEW_TRAIN, e);
            throw new DBException(Messages.ERR_SAVE_NEW_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(Train train, String[] params) {

    }

    /**
     * This method is used for deleting train
     * @param train object of Train
     * @throws DBException
     */
    @Override
    public void delete(Train train) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_TRAIN);
            pstmt.setInt(1, train.getNumber());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_DELETE_TRAIN, e);
            throw new DBException(Messages.ERR_DELETE_TRAIN, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
    }
}
