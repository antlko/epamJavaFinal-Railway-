package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.CityDao;
import com.nure.kozhukhar.railway.db.dao.CountryDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StationChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(CityChangeData.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {


        StationDao stationsDao = new StationDao();
        Station station = new Station();
        try {
            if ("Save".equals(request.getParameter("changeStationInfo"))) {
                station.setName(request.getParameter("stationName"));
                station.setIdCity(CityDao.getIdCityByName(
                        request.getParameter("tagCities")
                ));
                LOG.trace("Selected ID city is --> " + station.getIdCity());
                stationsDao.save(station);
            }
            if ("Delete".equals(request.getParameter("changeStationInfo"))) {
                station.setName(request.getParameter("stationName"));
                stationsDao.delete(station);
            }
        } catch (DBException e) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        }

        String checkedVal = request.getParameter("checkVal");
        LOG.trace("Check val on the tab is : " + checkedVal);
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
