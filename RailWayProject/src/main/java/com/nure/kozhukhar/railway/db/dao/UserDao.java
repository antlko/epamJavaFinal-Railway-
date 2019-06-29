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
        PreparedStatement pstmt = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            pstmt = conn.prepareStatement("INSERT INTO users(login,password,email,name,surname) VALUES(?,?,?,null,null)");
            int atr = 1;
            pstmt.setString(atr++, user.getLogin());
            pstmt.setString(atr++, user.getPassword());
            pstmt.setString(atr, user.getEmail());
            pstmt.executeUpdate();
            atr = 1;
            pstmt = conn.prepareStatement("INSERT INTO user_roles(id,role) VALUES(?,?)");
            pstmt.setInt(atr++, getByLogin(user.getLogin()).getId());
            pstmt.setString(atr, Role.USER.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
