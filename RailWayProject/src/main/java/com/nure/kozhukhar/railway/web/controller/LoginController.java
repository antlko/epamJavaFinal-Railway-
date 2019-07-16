package com.nure.kozhukhar.railway.web.controller;

import com.nure.kozhukhar.railway.util.ServletProcessUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.login.LoginActionFactory;
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
        LOG.debug("Controller post starts.");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Controller get starts.");

        String errorPage = request.getParameter("errorPage");
        if(errorPage == null) {
            errorPage = "WEB-INF/jsp/error_400.jsp";
        }

        Action action = LoginActionFactory.getAction(request);
        ServletProcessUtil.process(request, response,
                errorPage, action);
    }

}
