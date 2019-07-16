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

public class CheckDao implements Dao {

    private static final Logger LOG = Logger.getLogger(CheckDao.class);

    public static void saveUserCheckInfo(UserCheck userCheck) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route,initials) \n" +
                     "VALUES(?,?,?,?,?,?,?,?);");

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
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_SAVE_USER_CHECK, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public static List<UserCheck> getAllCheckByUserId(Integer idUser) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<UserCheck> userChecks = new ArrayList<>();
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
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

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_CHECK_BY_USER, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return userChecks;
    }

    public static UserCheckBean getAllCarriageInfoByTags(Integer idUser, Integer idTrain, Integer numCarriage, Integer numSeat) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        UserCheckBean userCheckBean = new UserCheckBean();
        List<Station> stationList = new ArrayList<>();
        String cityEnd = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
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
            e.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GET_CARRIAGE_INFO, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        userCheckBean.setCityStart(stationList.get(0).getName());
        userCheckBean.setCityEnd(cityEnd);
        userCheckBean.setStationList(stationList);

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

    @Override
    public void delete(Object o) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if(o == null) return;

        if (o instanceof UserCheckBean) {
            UserCheckBean userCheckBean = (UserCheckBean) o;
            try {
                conn = DBUtil.getInstance().getDataSource().getConnection();
                pstmt = conn.prepareStatement(Queries.SQL_DELETE_USER_CHECK);
                int atr = 1;
                pstmt.setInt(atr++, userCheckBean.getIdUser());
                pstmt.setInt(atr++, userCheckBean.getIdTrain());
                pstmt.setInt(atr++, userCheckBean.getNumCarriage());
                pstmt.setInt(atr, userCheckBean.getNumSeat());
                pstmt.executeUpdate();
                conn.commit();

            } catch (SQLException e) {
                DBUtil.rollback(conn);
                e.printStackTrace();
                throw new DBException(Messages.ERR_CANNOT_DELETE_USER_CHECK, e);
            } finally {
                DBUtil.close(rs);
                DBUtil.close(pstmt);
                DBUtil.close(conn);
            }
        }
    }
}
