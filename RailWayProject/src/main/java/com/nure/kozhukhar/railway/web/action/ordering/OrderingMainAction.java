package com.nure.kozhukhar.railway.web.action.ordering;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.dao.CheckDao;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderingMainAction extends Action {

    private static final Logger LOG = Logger.getLogger(OrderingMainAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "/login";
        }

        LOG.trace("Info ordering ticket --> "
                + " Route " + session.getAttribute("userRoute") + ", "
                + " User: " + session.getAttribute("user") + ", "
                + " Carriage Number: " + request.getParameter("checkedCarriage") + ", "
                + " Seats List: " + Arrays.toString(request.getParameter("checkedSeats").split(" ")) + ", ");

        LOG.trace("rsb:" + session.getAttribute("userRoute"));
        RouteSearchBean route = (RouteSearchBean) session.getAttribute("userRoute");

        UserCheck userCheck = new UserCheck();
        String[] seatList = request.getParameter("checkedSeats").split(" ");
        userCheck.setIdUser(((User) session.getAttribute("user")).getId());
        userCheck.setInitials(UserDao.getFullNameByUserId(
                ((User) session.getAttribute("user")).getId())
        );
        userCheck.setNumCarriage(Integer.valueOf(request.getParameter("checkedCarriage")));
        userCheck.setIdRoute(route.getIdRoute());

        for (String numSeat : seatList) {
            for (int i = 0; i < route.getStationList().size() - 1; ++i) {
                userCheck.setDateEnd(route.getStationList().get(i).getTimeEnd());
                userCheck.setIdTrain(route.getTrain().getId());
                userCheck.setIdStation(route.getStationList().get(i).getIdStation());
                userCheck.setNumSeat(Integer.parseInt(numSeat));
                LOG.trace("INSERT for ordering --> " + userCheck);
                CheckDao.saveUserCheckInfo(userCheck);

            }
        }

        return "WEB-INF/jsp/booking/ordering.jsp";
    }
}
