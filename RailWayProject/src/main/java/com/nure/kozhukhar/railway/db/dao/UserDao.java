package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Object which operate User information from DB
 * Have a {@link UserDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class UserDao implements Dao<User> {

    private static final Logger LOG = Logger.getLogger(UserDao.class);

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method is used for for getting user by login
     * @param login user Login
     * @return object of User
     * @throws DBException
     */
    public User getByLogin(String login) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User user = new User();

        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_USER_BY_LOGIN);

            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
            }
            conn.commit();
            if (user.getId() == null) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_USER_BY_LOGIN, e);
            throw new DBException(Messages.ERR_GET_USER_BY_LOGIN, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }

        return user;
    }

    /**
     * This method is used for getting users role by login
     * @param login user Login
     * @return list of roles
     * @throws DBException
     */
    public List<String> getUserRolesByLogin(String login) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<String> roles = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(Queries.GET_USER_ROLES_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_USER_ROLE_BY_LOGIN, e);
            throw new DBException(Messages.ERR_GET_USER_ROLE_BY_LOGIN, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return roles;
    }

    /**
     * This method is used for getting full user name by ID user
     * @param idUser ID user
     * @return Full name [name surname]
     * @throws DBException
     */
    public String getFullNameByUserId(Integer idUser) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String initials = "";

        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_FULL_NAME_BY_USER_ID);
            pstmt.setInt(1, idUser);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                initials += rs.getString("name");
                initials += " " + rs.getString("surname");
            }

            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_DB_USER_FULL_NAME, e);
            throw new DBException(Messages.ERR_GET_DB_USER_FULL_NAME, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return initials;
    }

    /**
     * This method is used for saving new role to user
     * @param user object of User
     * @param role object of Role
     * @throws DBException
     */
    public void saveUserRoleByLogin(User user, String role) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Integer userId = getByLogin(user.getLogin()).getId();
            pstmt = conn.prepareStatement(Queries.SQL_SELECT_USER_AND_ROLE);
            int atr = 1;
            pstmt.setInt(atr++, userId);
            pstmt.setString(atr, role);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                atr = 1;
                pstmt = conn.prepareStatement(Queries.SQL_SAVE_USER_ROUTE_BY_LOGIN);
                pstmt.setInt(atr++, userId);
                pstmt.setString(atr, role);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_SAVE_USER_ROLE, e);
            throw new DBException(Messages.ERR_SAVE_USER_ROLE, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
    }


    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    /**
     * This method is used for save new User to DB
     * @param user object of User
     * @throws DBException
     */
    @Override
    public void save(User user) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_USER_TO_DB);
            int atr = 1;
            pstmt.setString(atr++, user.getLogin());
            pstmt.setString(atr++, user.getPassword());
            pstmt.setString(atr++, user.getEmail());
            pstmt.setString(atr++, user.getName());
            pstmt.setString(atr, user.getSurname());
            pstmt.executeUpdate();
            atr = 1;
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_USER_ROLE);
            pstmt.setInt(atr++, getByLogin(user.getLogin()).getId());
            pstmt.setString(atr, Role.USER.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_ADD_NEW_USER, e);
            throw new DBException(Messages.ERR_ADD_NEW_USER, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for updating user
     * @param user object of User
     * @param params other parameters
     * @throws DBException
     */
    @Override
    public void update(User user, String[] params) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(Queries.SQL_UPDATE_USER);
            int atr = 1;
            pstmt.setString(atr++, user.getName());
            pstmt.setString(atr++, user.getSurname());
            pstmt.setString(atr++, user.getEmail());
            pstmt.setString(atr, user.getLogin());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_UPDATE_USER_DATA, e);
            throw new DBException(Messages.ERR_UPDATE_USER_DATA, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    /**
     * This method is used for deleting user
     * @param user object of user
     * @throws DBException
     */
    @Override
    public void delete(User user) throws DBException {
        PreparedStatement pstmt = null;

        Integer userId = getByLogin(user.getLogin()).getId();

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_FROM_USER_ROLES);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_USER);
            pstmt.setString(1, user.getLogin());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_DELETE_USER, e);
            throw new DBException(Messages.ERR_DELETE_USER, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
