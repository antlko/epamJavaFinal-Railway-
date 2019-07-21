package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.RouteDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Type;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Route change action.
 *
 * @author Anatol Kozhukhar
 */
public class RouteChangeData extends Action {

    private static final Logger LOG = Logger.getLogger(RouteChangeData.class);
    private static final long serialVersionUID = 2969312008058985748L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            RouteDao routeDao = new RouteDao(connection);
            connection.setAutoCommit(false);

            if ("Save".equals(request.getParameter("changeRouteInfo"))) {
                List<RouteStation> routeStation = new ArrayList<>();
                routeDao.saveOneRoute(Integer.valueOf(request.getParameter("tagTrains")));

                List<String> infoStation = Arrays.asList(
                        request.getParameter("listInfoStation").split(";")
                );
                Pattern pattern = Pattern.compile("((.*?), (((.*?) (.*?))-((.*?) (.*?)))\\.( Price: (.*?)$))");

                LOG.trace(infoStation);

                for (String stationPrice : infoStation) {
                    Matcher matcher = pattern.matcher(stationPrice);
                    if (matcher.find()) {
                        LOG.trace("Group(2) = " + matcher.group(2)); //station Name
                        LOG.trace("Group(4) = " + matcher.group(4));//Date start
                        LOG.trace("Group(7) = " + matcher.group(7));// Date End
                        LOG.trace("Group(11) = " + matcher.group(11)); //Price

                        Integer price = Integer.valueOf(matcher.group(11));
                        String stationName = matcher.group(2);
                        String timeStart = matcher.group(4);
                        String timeEnd = matcher.group(7);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime dateTimeStart = LocalDateTime.parse(timeStart, formatter);
                        LocalDateTime dateTimeEnd = LocalDateTime.parse(timeEnd, formatter);

                        RouteStation stationTemp = new RouteStation();
                        stationTemp.setTimeStart(dateTimeStart);
                        stationTemp.setTimeEnd(dateTimeEnd);
                        stationTemp.setName(stationName);
                        stationTemp.setIdTrain(Integer.valueOf(request.getParameter("tagTrains")));
                        stationTemp.setPrice(price);

                        LOG.trace("Station Temp admin panel : " + stationTemp);
                        routeDao.save(stationTemp);
                    }
                }

            }
            if ("SaveRoutesDate".equals(request.getParameter("changeRouteInfo"))) {
                LOG.debug("Save route on date part.");
                String dateStart = request.getParameter("date-station") + " 00:00";
                String dateEnd = request.getParameter("date-station-end") + " 00:00";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTimeStart = LocalDateTime.parse(dateStart, formatter);
                LocalDateTime dateTimeEnd = LocalDateTime.parse(dateEnd, formatter);

                LOG.trace("Dates from form --> " + dateTimeStart + " - " + dateTimeEnd);

                routeDao.saveStationByRouteId(Integer.valueOf(
                        request.getParameter("routeId")),
                        dateTimeStart, dateTimeEnd
                );
            }
            if ("Delete".equals(request.getParameter("changeRouteInfo"))) {
                LOG.debug("Delete route was pressed!");
                RouteStation routeStation = new RouteStation();
                routeStation.setIdRoute(Integer.valueOf(request.getParameter("routeId")));
                routeDao.delete(routeStation);
            }
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

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
