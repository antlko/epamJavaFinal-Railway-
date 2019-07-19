package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.dao.CheckDao;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckService {

    private static final Logger LOG = Logger.getLogger(CheckService.class);

    public static List<UserCheckBean> getUserTicketsById(Integer idUser) throws DBException {

        List<UserCheckBean> checksInfo = null;

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            CheckDao checkDao = new CheckDao(connection);

            List<UserCheck> userCheckList = checkDao.getAllCheckByUserId(idUser);
            LOG.trace("User check (not bean) --> " + userCheckList);
            checksInfo = new ArrayList<>();
            for (UserCheck userCheck : userCheckList) {
                UserCheckBean check = checkDao.getAllCarriageInfoByTags(
                        idUser,
                        userCheck.getIdTrain(),
                        userCheck.getNumCarriage(),
                        userCheck.getNumSeat()
                );
                check.setNumTrain(userCheck.getNumTrain());
                check.setUserInitial(userCheck.getInitials());
                check.setDateEnd(check.getStationList().get(0).getDateEnd());
                check.setNumSeat(userCheck.getNumSeat());
                check.setNumCarriage(userCheck.getNumCarriage());
                check.setIdTrain(userCheck.getIdTrain());
                check.setIdUser(idUser);
                checksInfo.add(check);
            }
        } catch (ClassNotFoundException | NullPointerException |SQLException e) {
            e.printStackTrace();
        }

        LOG.debug("Check beans info --> " + checksInfo);
        return checksInfo;
    }

}
