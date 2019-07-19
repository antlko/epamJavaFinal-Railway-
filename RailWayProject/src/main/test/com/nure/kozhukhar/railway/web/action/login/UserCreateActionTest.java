package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.web.action.ordering.OrderingMainAction;
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
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserCreateActionTest extends Mockito {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();


    @Mock
    HttpSession mockSession;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    private static final String LOGIN = "testLog";
    private static final String PASSWORD = "testPas";
    private static final String EMAIL = "testEmail@gmail.com";

    private static final String LOGIN_EXCEPTION = "ex";
    private static final String PASSWORD_EXCEPTION = "ex";
    private static final String EMAIL_EXCEPTION = "ex";

    public UserCreateActionTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = AppException.class)
    public void testUserCreatingLogin() throws ServletException, IOException, AppException {
        UserCreateAction createAction = new UserCreateAction();
        when(request.getParameter("login")).thenReturn(LOGIN_EXCEPTION);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("email")).thenReturn(EMAIL);
        assertEquals("WEB-INF/jsp/booking/ordering.jsp",
                createAction.execute(request, response));
    }

    @Test(expected = AppException.class)
    public void testUserCreatingPassword() throws ServletException, IOException, AppException {
        UserCreateAction createAction = new UserCreateAction();
        when(request.getParameter("login")).thenReturn(LOGIN);
        when(request.getParameter("password")).thenReturn(PASSWORD_EXCEPTION);
        when(request.getParameter("email")).thenReturn(EMAIL);
        assertEquals("WEB-INF/jsp/booking/ordering.jsp",
                createAction.execute(request, response));
    }

    @Test(expected = AppException.class)
    public void testUserCreatingEmail() throws ServletException, IOException, AppException {
        UserCreateAction createAction = new UserCreateAction();
        when(request.getParameter("login")).thenReturn(LOGIN);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("email")).thenReturn(EMAIL_EXCEPTION);
        assertEquals("WEB-INF/jsp/booking/ordering.jsp",
                createAction.execute(request, response));
    }

    @Test
    public void testUserCreatingNormalAction() throws ServletException, IOException, AppException {
        UserCreateAction createAction = new UserCreateAction();
        when(request.getParameter("login")).thenReturn(LOGIN);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getParameter("email")).thenReturn(EMAIL);
        assertEquals("/login",
                createAction.execute(request, response));

        UserDao userDao = new UserDao(connection);
        User user = new User();
        user.setLogin(LOGIN);
        userDao.delete(user);
    }

}