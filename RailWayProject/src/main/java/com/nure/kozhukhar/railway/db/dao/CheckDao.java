package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckDao implements Dao {

    private static final Logger LOG = Logger.getLogger(CheckDao.class);

    public static void saveUserCheckInfo(UserCheck userCheck) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_check (id_user, date_end, id_train,num_carriage,num_seat,id_station,id_route,initials) \n" +
                     "VALUES(?,?,?,?,?,?,?,?);");
        ) {
            String date = Timestamp.valueOf(userCheck.getDateEnd()).toString().split("\\.")[0];
//            LOG.trace("Transformed date : " + date);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<UserCheck> getAllCheckByUserId(Integer idUser) {
        List<UserCheck> userChecks = new ArrayList<>();
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_SELECT_ALL_CHECK_FOR_USER);
        ) {
            pstmt.setInt(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserCheck userTemp = new UserCheck();
                userTemp.setIdTrain(rs.getInt("id_train"));
                userTemp.setNumTrain(rs.getInt("number"));
                userTemp.setNumCarriage(rs.getInt("num_carriage"));
                userTemp.setNumSeat(rs.getInt("num_seat"));
                userTemp.setInitials(rs.getString("initials"));
                userChecks.add(userTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userChecks;
    }

    public static UserCheckBean getAllCarriageInfoByTags(Integer idUser, Integer idTrain, Integer numCarriage, Integer numSeat) {
        UserCheckBean userCheckBean = new UserCheckBean();
        List<Station> stationList = new ArrayList<>();
        String cityEnd = null;

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(Queries.SQL_SELECT_ALL_STATION_INFO_FOR_CHECK);
        ) {
            int atr = 1;
            pstmt.setInt(atr++, idUser);
            pstmt.setInt(atr++, idTrain);
            pstmt.setInt(atr++, numCarriage);
            pstmt.setInt(atr, numSeat);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Station stationTemp = new Station();
                stationTemp.setName(rs.getString("name"));
                stationTemp.setDateEnd(TimeUtil.getDateTimeWithTimeZone(
                        rs.getTimestamp("date_end").toLocalDateTime()));
                cityEnd = rs.getString("dest");
                stationList.add(stationTemp);

                LOG.trace("Station date End " + stationTemp.getDateEnd());
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void delete(Object o) {

    }
}
