package com.nure.kozhukhar.railway.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Properties;

/**
 * Time util.
 * <p>
 * It set time Zone and transform
 * some time to time with timezone.
 *
 * Time zone saved in application.properties
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public final class TimeUtil {

    private static String timeZone;

    static {
        Properties appProps = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        timeZone = appProps.getProperty("timezone");
    }

    public static LocalDateTime getDateTimeWithTimeZone(LocalDateTime dateTime) {
        return LocalDateTime.ofInstant(
                dateTime.toInstant(ZoneOffset.MIN),
                ZoneId.of(timeZone)
        ).minusDays(1);
    }

    public static String formatterToHours(long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;

        return hours + " : " + minutes;
    }

}
