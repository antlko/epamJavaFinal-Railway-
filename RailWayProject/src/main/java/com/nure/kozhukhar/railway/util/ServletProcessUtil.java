package com.nure.kozhukhar.railway.util;

import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;

public class ServletProcessUtil {

    private static final Logger LOG = Logger.getLogger(ServletProcessUtil.class);

    private static final List<String> controllers = new ArrayList<>();

    static {
        Properties appProps = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        controllers.addAll(asList(appProps.getProperty("pages").split(",")));
    }

    public static void process(HttpServletRequest request,
                               HttpServletResponse response,
                               String errorPath,
                               Action action
    ) throws ServletException, IOException {

        LOG.debug("Controller starts");
        LOG.debug("Action : " + action);

        String forward = errorPath;
        try {
            forward = action.execute(request, response);
        } catch (AppException ex) {
            request.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        LOG.debug("Controller finished, now go to forward address --> " + forward);

        if (controllers.contains(forward)) {
            response.sendRedirect(request.getContextPath() + forward);
        } else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}
