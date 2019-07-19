package com.nure.kozhukhar.railway.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.nure.kozhukhar.railway.web.controller.AccountControllerTest.PATH;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderingControllerTest extends Mockito {

    @Mock
    HttpSession mockSession;

    /***
     * When the action is noFound
     */
    @Test
    public void AdminNewTest() throws ServletException, IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        OrderingController servlet = new OrderingController();
        servlet = Mockito.spy(servlet);

        when(request.getParameter("action")).thenReturn("noFound");
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(servlet,times(1)).doGet(request,response);

    }

    /***
     * When the action is null
     */
    @Test
    public void OrderingMainNewTest() throws ServletException, IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        OrderingController servlet = new OrderingController();
        servlet = Mockito.spy(servlet);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(null);
        when(request.getRequestDispatcher("/login")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(servlet,times(1)).doGet(request,response);

    }


}