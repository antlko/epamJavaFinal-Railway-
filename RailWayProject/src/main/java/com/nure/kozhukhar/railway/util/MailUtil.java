package com.nure.kozhukhar.railway.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Mail Util.
 * This is mail sender util.
 *
 * @author Anatol Kozhukhar
 */
public class MailUtil {

    private static String MAIL_LOGIN;

    private static String MAIL_PASSWORD;

    private static Properties prop;

    static {
        Properties appProps = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");
        try {
            appProps.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MAIL_LOGIN = appProps.getProperty("mail.sender.login");
        MAIL_PASSWORD = appProps.getProperty("mail.sender.password");
        prop = new Properties();
        prop.put("mail.transport.protocol", "smtps");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "bolshoymedvedtoliano@gmail.com");
    }

    public static void sendWelcomeMail(Integer pin, String recipientMail) throws MessagingException {
        Session session = Session.getDefaultInstance(prop);
        Transport transport = session.getTransport();
        transport.connect("smtp.gmail.com", 465, MAIL_LOGIN, MAIL_PASSWORD);

        Message message = new MimeMessage(session);
        message.setSubject("Welcome!");
        message.setContent("<h1>Welcome to the RailWay project!</h1>" +
                "<p>This is your secret code (don't lose it) : </p>" + pin, "text/html");

        InternetAddress address = new InternetAddress(recipientMail);
        message.addRecipient(Message.RecipientType.TO, address);
        message.setSentDate(new Date());

        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
    }
}
