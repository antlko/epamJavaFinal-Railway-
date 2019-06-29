package com.nure.kozhukhar.railway.util;

import com.nure.kozhukhar.railway.web.action.Action;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ServletProcessUtil {

    private static final Logger LOG = Logger.getLogger(ServletProcessUtil.class);

    private static final List<String> controllers = new ArrayList<>();

    static {
        controllers.addAll(asList("/account", "/login"));
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
        } catch (Exception ex) {

        }
        LOG.trace("Forward address --> " + forward);
        LOG.debug("Controller finished, now go to forward address --> " + forward);

        if (controllers.contains(forward)) {
            response.sendRedirect(request.getContextPath() + forward);
        } else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}