package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.TrainDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Change seats and carriages action.
 *
 * @author Anatol Kozhukhar
 */
public class CarrSeatChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(CarrSeatChangeData.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {


        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            TrainDao trainDao = new TrainDao(connection);
            TypeDao typeDao = new TypeDao(connection);

            Train train = new Train();
            train.setId(trainDao.getIdTrainByNumber(
                    Integer.valueOf(request.getParameter("tagTrains"))
            ));

            if ("Save".equals(request.getParameter("changeTrainInfo"))) {
                Integer carriageCount = Integer.valueOf(request.getParameter("carriageCount"));
                Integer seatCount = Integer.valueOf(request.getParameter("seatCount"));
                Integer idCarrType = typeDao.getIDTypeByName(request.getParameter("tagTypes"));


                LOG.trace("Train " + train
                        + ", Carriage count : " + carriageCount
                        + ", seatCount : " + seatCount
                        + ", carrType : " + idCarrType);


                trainDao.saveTrainContent(train.getId(), carriageCount, seatCount, idCarrType);
            }
            if ("Delete".equals(request.getParameter("changeTrainInfo"))) {
                trainDao.deleteAllTrainContent(train.getId());
            }
        } catch (DBException | ClassNotFoundException | SQLException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
