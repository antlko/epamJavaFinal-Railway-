package com.nure.kozhukhar.railway.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Register servlet controller
 *
 * @author Anatol Kozhukhar
 */
@WebServlet(name = "RegController", urlPatterns = "/reg")
public class RegController extends HttpServlet {
    private static final long serialVersionUID = -8628034203168210593L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/jsp/reg.jsp").forward(request, response);
    }
}
