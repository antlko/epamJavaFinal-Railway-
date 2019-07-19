package com.nure.kozhukhar.railway.web.controller;

import com.nure.kozhukhar.railway.web.action.ordering.OrderingMainAction;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.nure.kozhukhar.railway.web.controller.AccountControllerTest.PATH;
import static org.junit.Assert.*;

public class BookingControllerTest extends Mockito{

    @Test
    public void AdminNewTest() throws ServletException, IOException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        BookingController servlet = new BookingController();
        servlet = Mockito.spy(servlet);

        when(request.getParameter("action")).thenReturn("noFound");
        when(request.getRequestDispatcher(PATH)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(servlet,times(1)).doGet(request,response);

    }

}