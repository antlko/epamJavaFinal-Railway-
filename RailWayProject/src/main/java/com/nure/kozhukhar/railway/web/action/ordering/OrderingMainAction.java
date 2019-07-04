package com.nure.kozhukhar.railway.web.action.ordering;

import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderingMainAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "/login";
        }


        return "WEB-INF/jsp/booking/ordering.jsp";
    }
}
