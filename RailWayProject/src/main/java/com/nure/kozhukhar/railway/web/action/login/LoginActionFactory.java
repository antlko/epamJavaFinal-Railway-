package com.nure.kozhukhar.railway.web.action.login;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Login Action Factory
 * <p>
 * Holder for all actions Login controller
 * </p>
 *
 * @author Anatol Kozhukhar
 */
public class LoginActionFactory {

    private static final Logger LOG = Logger.getLogger(LoginActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("login", new UserLoginPageAction());
        actions.put("account", new UserSignInAction());
        actions.put("register", new UserRegisterAction());
        actions.put("createUser", new UserCreateAction());
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
        action = actions.get(actionName);
        if(action == null) {
            return actions.get("login");
        }
        if(!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}
