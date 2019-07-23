package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.dao.TrainDao;
import com.nure.kozhukhar.railway.db.dao.TypeDao;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.Action;
import com.nure.kozhukhar.railway.web.action.login.UserSignInAction;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Change type action.
 *
 * @author Anatol Kozhukhar
 */
public class TypeChangeData extends Action {
    private static final Logger LOG = Logger.getLogger(TypeChangeData.class);
    private static final long serialVersionUID = -3867566095412076655L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {

        Type type = new Type();
        type.setName(request.getParameter("typeName"));
        String price = request.getParameter("typePrice");

        try (Connection connection = DBUtil.getInstance().getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            TypeDao typeDao = new TypeDao(connection);

            if ("Save".equals(request.getParameter("changeTypeInfo"))) {
                if("".equals(price) || "".equals(type.getName())) {
                    throw new AppException(LocaleMessageUtil
                            .getMessageWithLocale(request, Messages.ERR_TYPE_FIELD));
                }
                type.setPrice(Integer.valueOf(price));
                typeDao.save(type);
            }
            if ("Delete".equals(request.getParameter("changeTypeInfo"))) {
                typeDao.delete(type);
            }
        } catch (AppException e) {
            LOG.error(e.getMessage(), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, e.getMessage()));
        } catch (Exception e) {
            LOG.error(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_TYPE), e);
            throw new AppException(LocaleMessageUtil
                    .getMessageWithLocale(request, Messages.ERR_CANNOT_SAVE_TYPE));
        }

        String checkedVal = request.getParameter("checkVal");
        request.getSession().setAttribute("tab", checkedVal);

        return "/admin";
    }
}
