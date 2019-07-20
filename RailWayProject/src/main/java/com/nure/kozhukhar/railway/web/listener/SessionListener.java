package com.nure.kozhukhar.railway.web.listener;

import com.nure.kozhukhar.railway.web.filter.EncodingFilter;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Listener for sessions.
 * <p>
 *     Save into logs request information.
 * </p>
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(SessionListener.class);

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        LOG.debug("Session Created: ID="
                + sessionEvent.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        LOG.debug("Session Destroyed: ID="
                + sessionEvent.getSession().getId());
    }

}
