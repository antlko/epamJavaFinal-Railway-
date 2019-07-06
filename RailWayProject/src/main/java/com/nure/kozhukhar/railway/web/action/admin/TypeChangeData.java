package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.TrainDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TypeChangeData extends Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        TypeDao typeDao = new TypeDao();
        Type type = new Type();
        type.setName(request.getParameter("typeName"));

        if ("Save".equals(request.getParameter("changeTypeInfo"))) {
            type.setPrice(Integer.valueOf(request.getParameter("typePrice")));
            typeDao.save(type);
        }
        if ("Delete".equals(request.getParameter("changeTypeInfo"))) {
            typeDao.delete(type);
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
