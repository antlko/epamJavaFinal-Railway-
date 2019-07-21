package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.dao.*;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Admin page action.
 *
 * @author Anatol Kozhukhar
 */
public class AdminAccountPage extends Action {

    private static final Logger LOG = Logger.getLogger(AdminAccountPage.class);
    private static final long serialVersionUID = 406837234627325838L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        if (request.getSession() == null) {
            return "/login";
        }
        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            CityDao cityDao = new CityDao(connection);
            CountryDao countryDao = new CountryDao(connection);
            StationDao stationDao = new StationDao(connection);
            TrainDao trainDao = new TrainDao(connection);
            TypeDao typeDao = new TypeDao(connection);
            RouteDao routeDao = new RouteDao(connection);

            List<TrainStatisticBean> trainsStat = trainDao.getTrainsStatistic();
            LOG.trace("Train statistic : " + trainsStat);

            request.setAttribute("allCityInfo", cityDao.getAll());
            request.setAttribute("allCountryInfo", countryDao.getAll());
            request.setAttribute("allStationInfo", stationDao.getAll());
            request.setAttribute("allTrainInfo", trainDao.getAll());
            request.setAttribute("allTypeInfo", typeDao.getAll());
            request.setAttribute("allTrainStatInfo", trainsStat);
            request.setAttribute("allRouteInfo", routeDao.getAllRoute());

            LOG.trace("'Station' attributes for admin panel --> " + cityDao.getAll() + ", " + countryDao.getAll());

        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_NEW_ROUTE), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_NEW_ROUTE));
        }

        return "WEB-INF/jsp/admin/admin.jsp";
    }
}
