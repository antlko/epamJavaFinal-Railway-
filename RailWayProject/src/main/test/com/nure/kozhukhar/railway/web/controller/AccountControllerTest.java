package com.nure.kozhukhar.railway.web.controller;

import com.mysql.cj.Session;
import com.nure.kozhukhar.railway.db.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Fields;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AccountControllerTest extends Mockito {

    public static final String PATH = "WEB-INF/jsp/error_400.jsp";


    @Test
    public void AccountNewTest() throws ServletException, IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        AccountController servlet = new AccountController();
        servlet = Mockito.spy(servlet);

        when(request.getParameter("action")).thenReturn("noFound");
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(servlet,times(1)).doGet(request,response);

    }

}