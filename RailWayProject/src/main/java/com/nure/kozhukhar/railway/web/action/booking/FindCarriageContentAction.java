package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindCarriageContentAction extends Action {

    private static final Logger LOG = Logger.getLogger(FindCarriageContentAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        Integer carrNum = Integer.valueOf(request.getParameter("carrNum"));
        Integer indCarrNum = 0;
        LOG.trace("Carr name :  " + carrNum);

        List<SeatSearchBean> seatBeanList =
                (ArrayList<SeatSearchBean>) session.getAttribute("serviceCarriage");
        LOG.debug("Request serviceCarriage " + seatBeanList);
        for(SeatSearchBean searchBean : seatBeanList) {
            if (carrNum.equals(searchBean.getNumCarriage())) {
                break;
            }
            indCarrNum++;
        }
        LOG.trace("Seat Bean list :  " + seatBeanList);
        session.setAttribute("serviceSeats", seatBeanList.get(indCarrNum).getListSeat());
        session.setAttribute("checkedCarriage", carrNum);
        return "WEB-INF/jsp/booking/seat_info.jsp";
    }
}
