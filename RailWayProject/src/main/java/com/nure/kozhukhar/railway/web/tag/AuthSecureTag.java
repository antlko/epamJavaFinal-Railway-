package com.nure.kozhukhar.railway.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Authentication tag
 * <p>
 *     Custom tag which allows after logout
 *     always checking in system user or not.
 *     Security page be revalidate after redirect to them.
 * </p>
 */
public class AuthSecureTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        // HTTP 1.1.
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
