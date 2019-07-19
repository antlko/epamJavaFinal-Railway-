package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.util.LocaleMessageUtil;
import com.nure.kozhukhar.railway.web.action.ordering.OrderingMainAction;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDao implements Dao<User> {

    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    private static final Logger LOG = Logger.getLogger(UserDao.class);

    public User getByLogin(String login) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        User user = new User();

        try {
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE login = ?");

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
            throw new DBException(Messages.ERR_GET_USER_BY_LOGIN, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }

        return user;
    }

    public List<String> getUserRolesByLogin(String login) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<String> roles = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("SELECT role FROM users U INNER JOIN user_roles UR ON U.id = UR.id WHERE login = ?;");
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_GET_USER_ROLE_BY_LOGIN, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return roles;
    }

    public String getFullNameByUserId(Integer idUser) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String initials = "";

        try {
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setInt(1, idUser);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                initials += rs.getString("name");
                initials += " " + rs.getString("surname");
            }

            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_GET_DB_USER_FULL_NAME, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(rs);
        }
        return initials;
    }

    public void saveUserRoleByLogin(User user, String role) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Integer userId = getByLogin(user.getLogin()).getId();
            pstmt = conn.prepareStatement("SELECT * FROM user_roles WHERE id = ? AND role = ?");
            int atr = 1;
            pstmt.setInt(atr++, userId);
            pstmt.setString(atr, role);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                atr = 1;
                pstmt = conn.prepareStatement("INSERT INTO user_roles(id,role) VALUES(?,?)");
                pstmt.setInt(atr++, userId);
                pstmt.setString(atr, role);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
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

    @Override
    public void save(User user) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO users(login,password,email,name,surname) VALUES(?,?,?,?,?)");
            int atr = 1;
            pstmt.setString(atr++, user.getLogin());
            pstmt.setString(atr++, user.getPassword());
            pstmt.setString(atr++, user.getEmail());
            pstmt.setString(atr++, user.getName());
            pstmt.setString(atr, user.getSurname());
            pstmt.executeUpdate();
            atr = 1;
            pstmt = conn.prepareStatement("INSERT INTO user_roles(id,role) VALUES(?,?)");
            pstmt.setInt(atr++, getByLogin(user.getLogin()).getId());
            pstmt.setString(atr, Role.USER.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_ADD_NEW_USER, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(User user, String[] params) throws DBException {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPDATE users SET name = ?,surname = ?, email = ? WHERE login = ?");
            int atr = 1;
            pstmt.setString(atr++, user.getName());
            pstmt.setString(atr++, user.getSurname());
            pstmt.setString(atr++, user.getEmail());
            pstmt.setString(atr, user.getLogin());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_UPDATE_USER_DATA, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void delete(User user) throws DBException {
        PreparedStatement pstmt = null;

        Integer userId = getByLogin(user.getLogin()).getId();

        try {
            pstmt = conn.prepareStatement("DELETE FROM user_roles WHERE id = ?");
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("DELETE FROM users WHERE login= ?");
            pstmt.setString(1, user.getLogin());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_DELETE_USER, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
