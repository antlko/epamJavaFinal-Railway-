package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
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
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserSignInActionTest extends Mockito {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    // Test data
    private static final String LOGIN = "testLog";
    private static final String PASSWORD = "testPas";
    private static final String EMAIL = "testEmail@gmail.com";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    public UserSignInActionTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        connection.setAutoCommit(false);
        when(request.getSession()).thenReturn(session);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void executeSignIn() throws ServletException, IOException, AppException {
        UserSignInAction signInAction = new UserSignInAction();

        //Create test-User for future use
        UserDao userDao = new UserDao(connection);

        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        userDao.save(user);

        System.out.println("User for test -> " +user);

        //Testing sign-in
        when(request.getParameter("login")).thenReturn(LOGIN);
        when(request.getParameter("password")).thenReturn(PASSWORD);

        assertEquals("/account",
                signInAction.execute(request, response));

        //Delete test data
        userDao.delete(user);
    }

    @Test(expected = AppException.class)
    public void executeSignInWrongLogin() throws ServletException, IOException, AppException {
        UserSignInAction signInAction = new UserSignInAction();

        //Testing sign-in
        when(request.getParameter("login")).thenReturn("wrongLoginTest");
        when(request.getParameter("password")).thenReturn("123");

        assertEquals("/account",
                signInAction.execute(request, response));
    }
}