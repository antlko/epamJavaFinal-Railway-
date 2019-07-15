package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.CityDao;
import com.nure.kozhukhar.railway.db.dao.CountryDao;
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

public class CountryChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(CountryChangeData.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        CountryDao countryDao = new CountryDao();
        Country country = new Country();

        try {
            if ("Save".equals(request.getParameter("changeCountryInfo"))) {
                country.setName(request.getParameter("countryName"));
                countryDao.save(country);
            }
            if ("Delete".equals(request.getParameter("changeCountryInfo"))) {
                country.setName(request.getParameter("countryName"));
                countryDao.delete(country);
            }
        } catch (DBException ex) {
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, ex.getMessage()));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);
        return "/admin";
    }
}
