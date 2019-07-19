package com.nure.kozhukhar.railway.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class AuthSecureTag extends SimpleTagSupport {


    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        // HTTP 1.1.
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        // HTTP 1.0
        response.setHeader("Pragma","no-cache");

        //Proxies
        response.setHeader("Expires", "0");

        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
