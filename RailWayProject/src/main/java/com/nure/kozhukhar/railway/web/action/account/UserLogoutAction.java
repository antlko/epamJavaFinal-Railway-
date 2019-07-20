package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.web.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout action.
 *
 * @author Anatol Kozhukhar
 */
public class UserLogoutAction extends Action {
    private static final long serialVersionUID = -1998504187785610633L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        session.removeAttribute("userRoles");
        session.invalidate();
        return "/login";
    }
}
