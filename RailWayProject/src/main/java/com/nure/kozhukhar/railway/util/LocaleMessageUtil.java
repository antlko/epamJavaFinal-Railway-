package com.nure.kozhukhar.railway.util;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocaleMessageUtil {

    private static final Logger LOG = Logger.getLogger(LocaleMessageUtil.class);

    public static String getMessageWithLocale(HttpServletRequest request, String localeMessage) {

        String output = "";
        ResourceBundle rb = null;

        Cookie[] cookies = request.getCookies();
        String lang = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("localize".equals(cookie.getName())) {
                    lang = cookie.getValue();
                }
            }
        }

        LOG.trace("Lang inside cookie - > " + lang);

        Locale locale = Locale.forLanguageTag(lang);
        if (!"".equals(lang)) {
            rb = ResourceBundle.getBundle("messages", locale);
        }
        try {
            try {
                if (rb != null) {
                    output = rb.getString(localeMessage);
                } else {
                    throw new MissingResourceException("Missing rb",
                            LocaleMessageUtil.class.toString(), null);
                }
            } catch (MissingResourceException e) {
                LOG.debug("RS First Bundle error - > " + e);
                rb = ResourceBundle.getBundle("messages", Locale.forLanguageTag("en"));
                output = rb.getString(localeMessage);
            }
        } catch (Exception e) {
            LOG.debug("RS Bundle error - > " + e);
            return localeMessage;
        }

        LOG.debug("Locale test -> " + output);
        return output;
    }

}
