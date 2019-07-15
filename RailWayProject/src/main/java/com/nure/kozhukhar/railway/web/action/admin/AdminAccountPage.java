package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.dao.*;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminAccountPage extends Action {

    private static final Logger LOG = Logger.getLogger(AdminAccountPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        if (request.getSession() == null) {
            return "/login";
        }


        CityDao cityDao = new CityDao();
        CountryDao countryDao = new CountryDao();
        StationDao stationDao = new StationDao();
        TrainDao trainDao = new TrainDao();
        TypeDao typeDao = new TypeDao();

        List<TrainStatisticBean> trainsStat = TrainDao.getTrainsStatistic();
        LOG.trace("Train statistic : " + trainsStat);

        try {
            request.setAttribute("allCityInfo", cityDao.getAll());
            request.setAttribute("allCountryInfo", countryDao.getAll());
            request.setAttribute("allStationInfo", stationDao.getAll());
            request.setAttribute("allTrainInfo", trainDao.getAll());
            request.setAttribute("allTypeInfo", typeDao.getAll());
            request.setAttribute("allTrainStatInfo", trainsStat);
            request.setAttribute("allRouteInfo", RouteDao.getAllRoute());
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        LOG.trace("'Station' attributes for admin panel --> " + cityDao.getAll() + ", " + countryDao.getAll());


        return "WEB-INF/jsp/admin/admin.jsp";
    }
}
