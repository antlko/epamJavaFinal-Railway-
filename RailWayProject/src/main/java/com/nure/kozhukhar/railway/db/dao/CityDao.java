package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements Dao<City> {

    public static Integer getIdCityByName(String name) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idCity = null;
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("SELECT id FROM cities WHERE name = ?");

            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idCity = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_CITY_BY_ID, e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return idCity;
    }

    @Override
    public City get(long id) {
        return null;
    }

    @Override
    public List<City> getAll() throws DBException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<City> cityNames = new ArrayList<>();
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Cities ORDER BY name");
            while (rs.next()) {
                City cityTemp = new City();
                cityTemp.setName(rs.getString("name"));
                cityNames.add(cityTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_GET_CITY_LIST, e);
        } finally {
            DBUtil.close(conn, stmt, rs);
        }

        return cityNames;
    }

    @Override
    public void save(City city) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("INSERT INTO cities(name,id_country) VALUES(?,?)");
            int atr = 1;
            pstmt.setString(atr++, city.getName());
            pstmt.setInt(atr, city.getIdCountry());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_CITY_SAVE, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    @Override
    public void update(City city, String[] params) {

    }

    @Override
    public void delete(City city) throws DBException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            pstmt = conn.prepareStatement("DELETE FROM cities WHERE name = ?");
            pstmt.setString(1, city.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_DELETE_CITY, e);
        } finally {
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }
}
