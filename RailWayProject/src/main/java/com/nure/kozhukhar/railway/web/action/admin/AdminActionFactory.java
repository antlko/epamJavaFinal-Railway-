package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin Action Factory
 * <p>
 * Holder for all actions admin controller
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public class AdminActionFactory {

    private static final Logger LOG = Logger.getLogger(AdminActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("mainAdmin", new AdminAccountPage());
        actions.put("changeUser", new UserChangeData());
        actions.put("changeCity", new CityChangeData());
        actions.put("changeCountry", new CountryChangeData());
        actions.put("changeStation", new StationChangeData());
        actions.put("changeTrain", new TrainChangeData());
        actions.put("changeType", new TypeChangeData());
        actions.put("changeCarrSeat", new CarrSeatChangeData());
        actions.put("changeRoute", new RouteChangeData());
        actions.put("noFound", new NoFoundAction());
    }

    /**
     * Returns action object with the given name.
     *
     * @param request
     *            request from ServletUtil
     * @return Command object.
     */
    public static Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("action");

        LOG.debug("Action name auth : " + actionName);
        action = actions.get(actionName);

        // This action will be use when a user tries to go to '/admin' domain page
        if (action == null) {
            return actions.get("mainAdmin");
        }
        if (!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}
