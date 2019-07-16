package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.TrainDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CarrSeatChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(CarrSeatChangeData.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        TrainDao trainDao = new TrainDao();
        Train train = new Train();

        try {
            train.setId(TrainDao.getIdTrainByNumber(
                    Integer.valueOf(request.getParameter("tagTrains"))
            ));

            if ("Save".equals(request.getParameter("changeTrainInfo"))) {
                Integer carriageCount = Integer.valueOf(request.getParameter("carriageCount"));
                Integer seatCount = Integer.valueOf(request.getParameter("seatCount"));
                Integer idCarrType = TypeDao.getIDTypeByName(request.getParameter("tagTypes"));


                LOG.trace("Train " + train
                        + ", Carriage count : " + carriageCount
                        + ", seatCount : " + seatCount
                        + ", carrType : " + idCarrType);


                TrainDao.saveTrainContent(train.getId(), carriageCount, seatCount, idCarrType);
            }
            if ("Delete".equals(request.getParameter("changeTrainInfo"))) {
                TrainDao.deleteAllTrainContent(train.getId());
            }
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
