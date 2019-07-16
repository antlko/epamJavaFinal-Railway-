package com.nure.kozhukhar.railway.web.controller;

import com.nure.kozhukhar.railway.util.ServletProcessUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.account.AccountActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountController", urlPatterns = "/account")
public class AccountController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginController.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Account Servlet POST was start");
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Account Servlet GET was start");
        Action action = AccountActionFactory.getAction(request);
        LOG.trace("Action name in servlet : " + action);
        ServletProcessUtil.process(request, response,
                "/account", action);
    }
}
