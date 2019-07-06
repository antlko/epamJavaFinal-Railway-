package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.NoFoundAction;
import com.nure.kozhukhar.railway.web.action.account.UserAccountPage;
import com.nure.kozhukhar.railway.web.action.account.UserDeleteCheckAction;
import com.nure.kozhukhar.railway.web.action.account.UserLogoutAction;
import com.nure.kozhukhar.railway.web.action.account.UserUpdatePersonal;
import com.nure.kozhukhar.railway.web.action.login.UserRegisterAction;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AdminActionFactory {

    private static final Logger LOG = Logger.getLogger(AdminActionFactory.class);

    private static Map<String, Action> actions = new HashMap<>();

    private static Action action;

    static {
        actions.put("mainAdmin", new AdminAccountPage());
        actions.put("changeUser", new UserChangeData());
        actions.put("changeCity", new CityChangeData());
        actions.put("changeCountry" , new CountryChangeData());
        actions.put("noFound", new NoFoundAction());
    }

    public static Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("action");
        LOG.debug("Action name auth : " + actionName);
        action = actions.get(actionName);

        // This action will be use when a user tries to go to '/admin' domain page
        if(action == null) {
            return actions.get("mainAdmin");
        }
        if(!actions.containsKey(actionName)) {
            return actions.get("noFound");
        }
        return action;
    }
}
