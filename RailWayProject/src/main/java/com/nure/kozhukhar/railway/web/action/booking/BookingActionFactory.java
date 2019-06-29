package com.nure.kozhukhar.railway.web.action.booking;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BookingActionFactory {

    private static final Logger LOG = Logger.getLogger(BookingActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("bookingMain", new BookingStartPageAction());
        actions.put("order", new NoFoundAction());
        actions.put("findTickets", new FindTicketsAction());
        actions.put("noFound", new NoFoundAction());
    }

    public static Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("action");
        LOG.debug("Action name auth : " + actionName);
        action = actions.get(actionName);
        if(action == null) {
            return actions.get("bookingMain");
        }
        if(!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}
