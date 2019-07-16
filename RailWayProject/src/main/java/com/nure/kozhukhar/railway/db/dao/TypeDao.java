package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.bean.TrainStatisticBean;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDao implements Dao<Type> {

    public static Integer getIDTypeByName(String name) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer typeId = null;
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("SELECT id FROM Types WHERE name = ?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                typeId = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_ID_TYPE, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
        return typeId;
    }

    @Override
    public Type get(long id) {
        return null;
    }

    @Override
    public List<Type> getAll() throws DBException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Type> types = new ArrayList<>();
        Type typeTemp = null;
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM types ORDER BY price;");
            while (rs.next()) {
                typeTemp = new Type();
                typeTemp.setId(rs.getInt("id"));
                typeTemp.setName(rs.getString("name"));
                typeTemp.setPrice(rs.getInt("price"));
                types.add(typeTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_TYPES, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(conn);
        }
        return types;
    }

    @Override
    public void save(Type type) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM types WHERE name = ?");
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
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_SAVE_CARR_TYPE, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    @Override
    public void update(Type type, String[] params) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("UPDATE types SET price = ? WHERE name = ?");
            int atr = 1;
            pstmt.setInt(atr++, type.getPrice());
            pstmt.setString(atr, type.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_UPDATE_CARR_TYPE, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    @Override
    public void delete(Type type) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("DELETE FROM types WHERE name = ?");
            pstmt.setString(1, type.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_DELETE_TYPE_OF_CARRIAGE, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }
}
