package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDao implements Dao<Type> {
    @Override
    public Type get(long id) {
        return null;
    }

    @Override
    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        Type typeTemp = null;
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM types;");
            while (rs.next()) {
                typeTemp = new Type();
                typeTemp.setId(rs.getInt("id"));
                typeTemp.setName(rs.getString("name"));
                typeTemp.setPrice(rs.getInt("price"));
                types.add(typeTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    @Override
    public void save(Type type) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM types WHERE name = ?");
            int atr = 1;
            pstmt.setString(atr, type.getName());
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                pstmt = conn.prepareStatement("INSERT INTO types(name, price) VALUES(?,?)");
                atr = 1;
                pstmt.setString(atr++, type.getName());
                pstmt.setInt(atr, type.getPrice());
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                update(type, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Type type, String[] params) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE types SET price = ? WHERE name = ?");
        ) {
            int atr = 1;
            pstmt.setInt(atr++, type.getPrice());
            pstmt.setString(atr, type.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Type type) {
        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM types WHERE name = ?");
        ) {
            pstmt.setString(1, type.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
