package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Object getting User Check information from route.
 * Have a {@link CheckDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class CheckDao implements Dao {

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public CheckDao(Connection conn) {
        this.conn = conn;
    }

    private static final Logger LOG = Logger.getLogger(CheckDao.class);

    /**
     * This method save information about user route.
     *
     * @param userCheck object, which have a set of values prepared for inserting.
     * @throws DBException
     */
    public void saveUserCheckInfo(UserCheck userCheck) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_USER_CHECK);

            String date = Timestamp.valueOf(userCheck.getDateEnd()).toString().split("\\.")[0];

            int atr = 1;
            pstmt.setInt(atr++, userCheck.getIdUser());
            pstmt.setString(atr++, date);
            pstmt.setInt(atr++, userCheck.getIdTrain());
            pstmt.setInt(atr++, userCheck.getNumCarriage());
            pstmt.setInt(atr++, userCheck.getNumSeat());
            pstmt.setInt(atr++, userCheck.getIdStation());
            pstmt.setInt(atr++, userCheck.getIdRoute());
            pstmt.setString(atr, userCheck.getInitials());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_SAVE_USER_CHECK, e);
            throw new DBException(Messages.ERR_CANNOT_SAVE_USER_CHECK, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    /**
     *
     * @param idUser ID User in DB
     * @return all info about bought routes
     * @throws DBException
     */
    public List<UserCheck> getAllCheckByUserId(Integer idUser) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<UserCheck> userChecks = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_ALL_CHECK_FOR_USER);

            pstmt.setInt(1, idUser);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                UserCheck userTemp = new UserCheck();
                userTemp.setIdTrain(rs.getInt("id_train"));
                userTemp.setNumTrain(rs.getInt("number"));
                userTemp.setNumCarriage(rs.getInt("num_carriage"));
                userTemp.setNumSeat(rs.getInt("num_seat"));
                userTemp.setInitials(rs.getString("initials"));
                userChecks.add(userTemp);
            }
            conn.commit();

        } catch (SQLException | NullPointerException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_GET_CHECK_BY_USER, e);
            throw new DBException(Messages.ERR_CANNOT_GET_CHECK_BY_USER, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return userChecks;
    }

    /**
     * This method is used for getting info about stations and time
     *
     * @param idUser ID User
     * @param idTrain ID Train
     * @param numCarriage Carriage number
     * @param numSeat Seat number
     * @return Return bean UserCheckBean
     * @throws DBException
     */
    public UserCheckBean getAllCarriageInfoByTags(Integer idUser, Integer idTrain, Integer numCarriage, Integer numSeat) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserCheckBean userCheckBean = new UserCheckBean();
        List<Station> stationList = new ArrayList<>();
        String cityEnd = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_ALL_STATION_INFO_FOR_CHECK);

            int atr = 1;
            pstmt.setInt(atr++, idUser);
            pstmt.setInt(atr++, idTrain);
            pstmt.setInt(atr++, numCarriage);
            pstmt.setInt(atr, numSeat);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Station stationTemp = new Station();
                stationTemp.setName(rs.getString("name"));
                stationTemp.setDateEnd(TimeUtil.getDateTimeWithTimeZone(
                        rs.getTimestamp("date_end").toLocalDateTime()));
                cityEnd = rs.getString("dest");
                stationList.add(stationTemp);

                LOG.trace("Station date End " + stationTemp.getDateEnd());
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_CANNOT_GET_CARRIAGE_INFO, e);
            throw new DBException(Messages.ERR_CANNOT_GET_CARRIAGE_INFO, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        if (stationList.size() > 0) {
            userCheckBean.setCityStart(stationList.get(0).getName());
            userCheckBean.setCityEnd(cityEnd);
            userCheckBean.setStationList(stationList);
        }

        return userCheckBean;
    }

    @Override
    public Object get(long id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o, String[] params) {

    }

    /**
     * This method delete info about user Check
     *
     * @param o object of UserCheckBean
     * @throws DBException
     */
    @Override
    public void delete(Object o) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (o == null) return;

        if (o instanceof UserCheckBean) {
            UserCheckBean userCheckBean = (UserCheckBean) o;
            try {
                pstmt = conn.prepareStatement(Queries.SQL_DELETE_USER_CHECK);
                int atr = 1;
                pstmt.setInt(atr++, userCheckBean.getIdUser());
                pstmt.setInt(atr++, userCheckBean.getIdTrain());
                pstmt.setInt(atr++, userCheckBean.getNumCarriage());
                pstmt.setInt(atr, userCheckBean.getNumSeat());
                pstmt.executeUpdate();
                conn.commit();

            } catch (SQLException | NullPointerException e) {
                DBUtil.rollback(conn);
                LOG.error(Messages.ERR_CANNOT_DELETE_USER_CHECK, e);
                throw new DBException(Messages.ERR_CANNOT_DELETE_USER_CHECK, e);
            } finally {
                DBUtil.close(rs);
                DBUtil.close(pstmt);
            }
        }
    }
}
