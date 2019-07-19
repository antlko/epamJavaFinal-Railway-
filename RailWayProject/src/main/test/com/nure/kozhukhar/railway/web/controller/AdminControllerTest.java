package com.nure.kozhukhar.railway.web.controller;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nure.kozhukhar.railway.web.controller.AccountControllerTest.PATH;
import static org.junit.Assert.*;

public class AdminControllerTest extends  Mockito{

    @Test
    public void AdminNewTest() throws ServletException, IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        AdminController servlet = new AdminController();
        servlet = Mockito.spy(servlet);

        when(request.getParameter("action")).thenReturn("noFound");
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(servlet,times(1)).doGet(request,response);

    }

}