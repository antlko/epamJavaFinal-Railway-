package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.CityDao;
import com.nure.kozhukhar.railway.db.dao.CountryDao;
import com.nure.kozhukhar.railway.db.dao.StationDao;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Station;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Change station action.
 *
 * @author Anatol Kozhukhar
 */
public class StationChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(StationChangeData.class);
    private static final long serialVersionUID = -3551833302017356152L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {


        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);

            StationDao stationsDao = new StationDao(connection);
            CityDao cityDao = new CityDao(connection);

            Station station = new Station();

            if ("Save".equals(request.getParameter("changeStationInfo"))) {
                station.setName(request.getParameter("stationName"));
                station.setIdCity(cityDao.getIdCityByName(
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
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_STATION), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_STATION));
        }

        String checkedVal = request.getParameter("checkVal");
        LOG.trace("Check val on the tab is : " + checkedVal);
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
