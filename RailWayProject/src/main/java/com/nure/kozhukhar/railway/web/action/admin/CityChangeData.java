package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.CityDao;
import com.nure.kozhukhar.railway.db.dao.CountryDao;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(CityChangeData.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        CityDao cityDao = new CityDao();
        City city = new City();
        try {
            if ("Save".equals(request.getParameter("changeCityInfo"))) {
                city.setName(request.getParameter("cityName"));
                CountryDao countryDao = new CountryDao();
                city.setIdCountry(CountryDao.findIdCountyByName(
                        request.getParameter("tagCountries")
                ));
                LOG.trace("Selected ID country is --> " + city.getIdCountry());
                cityDao.save(city);
            }
            if ("Delete".equals(request.getParameter("changeCityInfo"))) {
                city.setName(request.getParameter("cityName"));
                cityDao.delete(city);
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
