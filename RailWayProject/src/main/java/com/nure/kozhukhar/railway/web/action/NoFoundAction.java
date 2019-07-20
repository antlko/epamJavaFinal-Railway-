package com.nure.kozhukhar.railway.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * No one can be found action.
 *
 * @author Anatol Kozhukhar
 */
public class NoFoundAction extends Action {
    private static final long serialVersionUID = 8915661779197351387L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        return "WEB-INF/jsp/error_400.jsp";
    }
}
