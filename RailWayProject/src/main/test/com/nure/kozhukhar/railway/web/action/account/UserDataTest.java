package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.web.action.admin.AdminAccountPage;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataTest extends Mockito {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    // DAO objects
    private UserDao userDao;

    // Objects for testing
    private User user;

    // Constants
    private static final String LOGIN = "testLog";
    private static final String PASSWORD = "testPas";
    private static final String EMAIL = "testEmail@gmail.com";

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    public UserDataTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        connection.setAutoCommit(false);
        when(request.getSession()).thenReturn(session);

        userDao = new UserDao(connection);
        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);

        userDao.save(user);
    }

    @After
    public void tearDown() throws Exception {
        userDao.delete(user);
    }

    @Test
    public void userUpdatePersonal() throws ServletException, IOException, AppException {
        UserUpdatePersonal userChangeData = new UserUpdatePersonal();

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("Name")).thenReturn(user.getName());
        when(request.getParameter("Surname")).thenReturn(user.getSurname());
        when(request.getParameter("Email")).thenReturn(user.getEmail());

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("/account");
    }

    @Test(expected = AppException.class)
    public void userShowCheckAction() throws ServletException, IOException, AppException {
        UserShowCheckAction userChangeData = new UserShowCheckAction();

        when(session.getAttribute("user")).thenReturn(user);
        when(session.getAttribute("checkInd")).thenReturn("0");

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("WEB-INF/jsp/user/print_doc.jsp");
    }

    @Test(expected = NullPointerException.class)
    public void userLogoutAction() throws ServletException, IOException, AppException {
        UserLogoutAction userChangeData = new UserLogoutAction();

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("/login");
    }

    @Test(expected = AppException.class)
    public void userDeleteCheckAction() throws ServletException, IOException, AppException {
        UserDeleteCheckAction userChangeData = new UserDeleteCheckAction();

        List<UserCheckBean> userCheckBeans = new ArrayList<>();
        UserCheckBean userCheckBean = new UserCheckBean();
        userCheckBeans.add(userCheckBean);

        when(request.getParameter("checkInd")).thenReturn("0");
        when(session.getAttribute("userChecks")).thenReturn(userCheckBeans);

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("/account");
    }

    @Test(expected = AppException.class)
    public void userAccountPage() throws ServletException, IOException, AppException {
        UserAccountPage userChangeData = new UserAccountPage();

        when(session.getAttribute("user")).thenReturn(user);

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("WEB-INF/jsp/user/account.jsp");
    }

    @Test
    public void userAccountPageNotLogged() throws ServletException, IOException, AppException {
        UserAccountPage userChangeData = new UserAccountPage();

        when(session.getAttribute("user")).thenReturn(null);

        assertThat(userChangeData.execute(request, response))
                .isEqualTo("/login");
    }



}
