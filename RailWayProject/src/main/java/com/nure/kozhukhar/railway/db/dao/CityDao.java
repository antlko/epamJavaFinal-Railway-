package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements Dao<City> {

    private static final Logger LOG = Logger.getLogger(CheckDao.class);

    private Connection conn;

    public CityDao(Connection conn) {
        this.conn = conn;
    }

    public Integer getIdCityByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idCity = null;
        try {
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
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return idCity;
    }

    @Override
    public City get(long id) {
        return null;
    }

    @Override
    public List<City> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<City> cityNames = new ArrayList<>();
        try {
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
            DBUtil.close(rs);
            DBUtil.close(stmt);
        }

        return cityNames;
    }

    @Override
    public void save(City city) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO cities(name,id_country) VALUES(?,?)");
            int atr = 1;
            pstmt.setString(atr++, city.getName());
            pstmt.setInt(atr, city.getIdCountry());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.trace(Messages.ERR_CITY_SAVE, e);
            throw new DBException(Messages.ERR_CITY_SAVE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(City city, String[] params) {

    }

    @Override
    public void delete(City city) throws DBException {
        PreparedStatement pstmt = null;

        try {
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
        }
    }
}
