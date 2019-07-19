package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.db.entity.User;
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

import static org.junit.Assert.*;

public class UserLoginPageActionTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void userInSystemTest() throws IOException, ServletException {
        UserLoginPageAction loginPageAction = new UserLoginPageAction();
        when(session.getAttribute("user")).thenReturn(new User());
        assertEquals("/account",
                loginPageAction.execute(request, response));
    }

    @Test
    public void userNotInSystemTest() throws IOException, ServletException {
        UserLoginPageAction loginPageAction = new UserLoginPageAction();
        when(session.getAttribute("user")).thenReturn(null);
        assertEquals("WEB-INF/jsp/login.jsp",
                loginPageAction.execute(request, response));
    }
}