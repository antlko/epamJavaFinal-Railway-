package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDao implements Dao<Country> {

    private Connection conn;

    public CountryDao(Connection conn) {
        this.conn = conn;
    }

    public Integer findIdCountyByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idCounrty = null;

        try {
            pstmt = conn.prepareStatement("SELECT * FROM Countries WHERE name = ?");

            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idCounrty = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_COUNTRY_WAS_NOT_FOUND, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return idCounrty;
    }

    @Override
    public Country get(long id) {
        return null;
    }

    @Override
    public List<Country> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<Country> countryNames = new ArrayList<>();
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Countries ORDER BY name");

            while (rs.next()) {
                Country countryTemp = new Country();
                countryTemp.setName(rs.getString("name"));
                countryNames.add(countryTemp);
            }
            conn.commit();

        } catch (SQLException | ClassNotFoundException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_COUNTRIES_WAS_NOT_FOUND, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(conn);
        }
        return countryNames;
    }

    @Override
    public void save(Country country) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO countries(name) VALUES(?)");
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            throw new DBException(Messages.ERR_COUNTRY_SAVE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(Country country, String[] params) {

    }

    @Override
    public void delete(Country country) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM countries WHERE name = ?");
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            e.printStackTrace();
            throw new DBException(Messages.ERR_COUNTRY_DELETE, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
