package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.service.CheckService;
import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserShowCheckAction extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        Integer idUser = ((User) session.getAttribute("user")).getId();
        request.setAttribute("checkInfo", CheckService.getUserTicketsById(idUser).get(
                Integer.valueOf(request.getParameter("checkInd"))
        ));

        return "WEB-INF/jsp/user/print_doc.jsp";
    }
}
