package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    public static User getByLogin(String login) {
        User user = new User();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        ) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<String> getUserRolesByLogin(String login) {
        List<String> roles = new ArrayList<>();

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT role FROM users INNER JOIN user_roles WHERE login = ?");
        ) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
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
    public void save(User user) {

    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
