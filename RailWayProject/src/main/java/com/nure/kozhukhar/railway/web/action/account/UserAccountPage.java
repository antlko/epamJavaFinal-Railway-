package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.service.CheckService;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class UserAccountPage extends Action {

    private static final Logger LOG = Logger.getLogger(UserAccountPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();

        Integer idUser = ((User)session.getAttribute("user")).getId();
        session.setAttribute("userChecks", CheckService.getUserTicketsById(idUser));

        return "WEB-INF/jsp/user/account.jsp";
    }
}
