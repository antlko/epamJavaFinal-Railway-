package com.nure.kozhukhar.railway.web.controller;

import com.nure.kozhukhar.railway.util.ServletProcessUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.account.AccountActionFactory;
import com.nure.kozhukhar.railway.web.action.booking.BookingActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Booking servlet controller
 *
 * @author Anatol Kozhukhar
 */
@WebServlet(name = "BookingController", urlPatterns = "/booking")
public class BookingController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private static final long serialVersionUID = -8730681283449626002L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Account Servlet POST was start");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Account Servlet GET was start");
        Action action = BookingActionFactory.getAction(request);
        LOG.trace("Action name in servlet : " + action);
        ServletProcessUtil.process(request, response,
                "/booking", action);
    }
}
