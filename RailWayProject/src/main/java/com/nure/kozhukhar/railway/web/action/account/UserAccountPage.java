package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserAccountPage extends Action {

    private static final Logger LOG = Logger.getLogger(AccountActionFactory.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        return "WEB-INF/jsp/user/account.jsp";
    }
}
