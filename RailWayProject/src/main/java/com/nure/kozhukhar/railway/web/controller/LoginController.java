package com.nure.kozhukhar.railway.web.controller;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.ActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginController.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Controller starts");

        Action action = ActionFactory.getAction(request);

        String forward = "error_page";
        forward = action.execute(request, response);

        LOG.trace("Forward address --> " + forward);
        LOG.debug("Controller finished, now go to forward address --> " + forward);

        request.getRequestDispatcher(forward).forward(request, response);
        LOG.debug("Controller finished");
    }
}
