package com.nure.kozhukhar.railway.web.action.ordering;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.nure.kozhukhar.railway.db.bean.RouteSearchBean;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.entity.UserRole;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderingMainActionTest extends Mockito {

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    HttpSession mockSession;
    @Mock
    InitialContext ic;

    private RouteSearchBean routeSearchBean = new RouteSearchBean();

    public OrderingMainActionTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException, DBException {
        mockConn = DBUtil.getInstance().getDataSource().getConnection();
        mockConn.setAutoCommit(false);
        List<RouteStation> routeStations = new ArrayList<>();
        RouteStation routeStation = new RouteStation();
        routeStation.setName("test");
        routeStation.setIdStation(1);
        routeStations.add(routeStation);
        routeSearchBean.setStationList(routeStations);
        Train train = new Train();
        train.setId(1);
        routeSearchBean.setTrain(train);
    }

    @Test
    public void newTest() throws AppException, SQLException, ServletException, IOException, NamingException, ClassNotFoundException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        UserDao userDao = mock(UserDao.class);
        OrderingMainAction action = new OrderingMainAction();

        User user = new User();
        user.setId(1);

        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockSession.getAttribute("userRoute")).thenReturn(routeSearchBean);
        when(request.getParameter("checkedSeats")).thenReturn("1");

        when(userDao.getFullNameByUserId(anyInt())).thenReturn("test test");
        when(request.getParameter("checkedCarriage")).thenReturn("1");

        assertEquals("WEB-INF/jsp/booking/ordering.jsp",
                action.execute(request, response));
    }

}