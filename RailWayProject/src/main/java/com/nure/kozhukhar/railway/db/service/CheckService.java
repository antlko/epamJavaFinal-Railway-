package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.dao.CheckDao;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CheckService {

    private static final Logger LOG = Logger.getLogger(CheckService.class);

    public static List<UserCheckBean> getUserTicketsById(Integer idUser) {
        List<UserCheck> userCheckList = CheckDao.getAllCheckByUserId(idUser);
        LOG.trace("User check (not bean) --> " + userCheckList);
        List<UserCheckBean> checksInfo = new ArrayList<>();
        for(UserCheck userCheck: userCheckList) {
            UserCheckBean check = CheckDao.getAllCarriageInfoByTags(
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

        LOG.debug("Check beans info --> " + checksInfo);
        return checksInfo;
    }

}
