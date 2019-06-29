package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookingStartPageAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        return "WEB-INF/jsp/booking/booking.jsp";
    }
}
