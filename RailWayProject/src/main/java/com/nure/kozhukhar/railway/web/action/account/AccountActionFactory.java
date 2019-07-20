package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import com.nure.kozhukhar.railway.web.action.login.UserRegisterAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Account Action Factory
 * <p>
 * Holder for all actions account controller
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public class AccountActionFactory {

    private static final Logger LOG = Logger.getLogger(AccountActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("account", new UserAccountPage());
        actions.put("updatePersonal", new UserUpdatePersonal());
        actions.put("logout", new UserLogoutAction());
        actions.put("register", new UserRegisterAction());
        actions.put("noFound", new NoFoundAction());
        actions.put("deleteCheck", new UserDeleteCheckAction());
        actions.put("showCheck", new UserShowCheckAction());
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
        if (action == null) {
            return actions.get("account");
        }
        if (!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}
