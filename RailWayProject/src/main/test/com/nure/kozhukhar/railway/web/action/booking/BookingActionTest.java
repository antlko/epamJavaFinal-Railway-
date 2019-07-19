package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.bean.SeatSearchBean;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class BookingActionTest extends Mockito {


    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private static final String DATE = "2019-07-30";
    private static final String TEST_CITY = "test_City";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    public BookingActionTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        connection.setAutoCommit(false);
        when(request.getSession()).thenReturn(session);
    }


    @Test(expected = AppException.class)
    public void executeFindTickets() throws ServletException, IOException, AppException {
        FindTicketsAction ticketsAction = new FindTicketsAction();

        when(request.getParameter("date")).thenReturn(DATE);
        when(request.getParameter("cityStart")).thenReturn(TEST_CITY);
        when(request.getParameter("cityEnd")).thenReturn(TEST_CITY + "other");

        assertThat(ticketsAction.execute(request,response))
                .isEqualTo("/booking");
    }

    @Test
    public void executeFindCarriage() throws ServletException, IOException, AppException {
        FindCarriageContentAction ticketsAction = new FindCarriageContentAction();

        List<SeatSearchBean> searchBeanList = new ArrayList<>();
        SeatSearchBean searchBean = new SeatSearchBean();
        searchBean.setNumCarriage(1);
        searchBeanList.add(searchBean);

        when(request.getParameter("carrNum")).thenReturn("1");
        when(session.getAttribute("serviceCarriage")).thenReturn(searchBeanList);

        assertThat(ticketsAction.execute(request,response))
                .isEqualTo("WEB-INF/jsp/booking/seat_info.jsp");
    }

    @Test
    public void executeToStartPageBooking() throws ServletException, IOException, AppException {
        BookingStartPageAction ticketsAction = new BookingStartPageAction();
        assertThat(ticketsAction.execute(request,response))
                .isEqualTo("WEB-INF/jsp/booking/booking.jsp");
    }

    @Test
    public void executeSeatAction() throws ServletException, IOException, AppException {
        BookingSeatsAction ticketsAction = new BookingSeatsAction();

        // Set of parameters
        List<RouteSearchBean> searchBeanList = new ArrayList<>();
        RouteSearchBean searchBean = new RouteSearchBean();
        searchBeanList.add(searchBean);
        searchBean.setDateFrom("2000-01-01");
        Train train = new Train();
        train.setId(2);
        train.setNumber(222);
        searchBean.setTrain(train);
        List<RouteStation> routeStationList = new ArrayList<>();
        RouteStation routeStation = new RouteStation();
        routeStation.setPrice(0);
        routeStationList.add(routeStation);
        searchBean.setStationList(routeStationList);

        // Conditions
        when(session.getAttribute("infoRoutes")).thenReturn(searchBeanList);
        when(request.getParameter("checkedRouteForUser")).thenReturn("0");
        when(request.getParameter("date")).thenReturn(DATE);
        when(request.getParameter("cityStart")).thenReturn(TEST_CITY);
        when(request.getParameter("cityEnd")).thenReturn(TEST_CITY + "other");
        when(request.getParameter("seatCheckType")).thenReturn("type");

        assertThat(ticketsAction.execute(request,response))
                .isEqualTo("WEB-INF/jsp/booking/seat_info.jsp");
    }
}