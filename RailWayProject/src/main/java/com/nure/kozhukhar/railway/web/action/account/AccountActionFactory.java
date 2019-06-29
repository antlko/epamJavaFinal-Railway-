package com.nure.kozhukhar.railway.web.action.account;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import com.nure.kozhukhar.railway.web.action.login.UserLoginAction;
import com.nure.kozhukhar.railway.web.action.login.UserRegisterAction;
import com.nure.kozhukhar.railway.web.action.login.UserSignInAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AccountActionFactory {

    private static final Logger LOG = Logger.getLogger(AccountActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("account", new UserAccountPage());
        actions.put("logout", new UserLoginAction());
        actions.put("register", new UserRegisterAction());
        actions.put("noFound", new NoFoundAction());
    }

    public static Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("action");
        LOG.debug("Action name auth : " + actionName);
        action = actions.get(actionName);
        if(action == null) {
            return actions.get("account");
        }
        if(!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}