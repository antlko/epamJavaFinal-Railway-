package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.exception.DBException;
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
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class UserRegisterActionTest extends Mockito {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() throws IOException, ServletException {
        UserRegisterAction registerAction = new UserRegisterAction();
        assertThat(registerAction.execute(request, response))
                .isEqualTo("/reg");
    }
}