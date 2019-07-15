package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginPageAction extends Action {

    private static final Logger LOG = Logger.getLogger(UserLoginPageAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (request.getSession().getAttribute("user") != null) {
            LOG.trace("User are already exist in the system");
            return "/account";
        }

        return "WEB-INF/jsp/login.jsp";
    }
}
