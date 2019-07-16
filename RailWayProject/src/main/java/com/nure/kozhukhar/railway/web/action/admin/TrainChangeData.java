package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.CityDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.dao.TrainDao;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TrainChangeData extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        TrainDao trainDao = new TrainDao();
        Train train = new Train();
        train.setNumber(Integer.valueOf(request.getParameter("trainNumber")));

        try {
            if ("Save".equals(request.getParameter("changeTrainInfo"))) {
                trainDao.save(train);
            }
            if ("Delete".equals(request.getParameter("changeTrainInfo"))) {
                trainDao.delete(train);
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
