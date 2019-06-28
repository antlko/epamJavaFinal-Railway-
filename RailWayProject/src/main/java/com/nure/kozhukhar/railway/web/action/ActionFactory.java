package com.nure.kozhukhar.railway.web.action;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("login", new UserLoginAction());
        actions.put("noFound", new NoFoundAction());
    }

    public static Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("action");
        action = actions.get(actionName);
        if(action == null || !actions.containsKey(actionName)) {
            //action = actions.get("error");
            return actions.get("noFound");
        }
        return action;
    }
}
