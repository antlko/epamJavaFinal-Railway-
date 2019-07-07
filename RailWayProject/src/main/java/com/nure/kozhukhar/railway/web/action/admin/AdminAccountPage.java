package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.dao.*;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Train;
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
            throws IOException, ServletException {
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

        request.setAttribute("allCityInfo", cityDao.getAll());
        request.setAttribute("allCountryInfo", countryDao.getAll());
        request.setAttribute("allStationInfo", stationDao.getAll());
        request.setAttribute("allTrainInfo", trainDao.getAll());
        request.setAttribute("allTypeInfo", typeDao.getAll());
        request.setAttribute("allTrainStatInfo", trainsStat);

        LOG.trace("'Station' attributes for admin panel --> " + cityDao.getAll() + ", " + countryDao.getAll());


        return "WEB-INF/jsp/admin/admin.jsp";
    }
}
