package com.nure.kozhukhar.railway.web.listener;

import com.nure.kozhukhar.railway.web.filter.EncodingFilter;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Listener for requests.
 * <p>
 *     Save into logs request information.
 * </p>
 */
@WebListener
public class RequestListener implements ServletRequestListener {

    private static final Logger LOG = Logger.getLogger(RequestListener.class);

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        LOG.debug("ServletRequest destroyed. Remote IP="
                + servletRequest.getRemoteAddr());
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        LOG.debug("ServletRequest initialized. Remote IP="
                + servletRequest.getRemoteAddr());
    }

}
