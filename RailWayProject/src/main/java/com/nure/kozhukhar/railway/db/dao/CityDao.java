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

/**
 * DAO Object which operate Cities information from DB.
 * Have a {@link CityDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class CityDao implements Dao<City> {

    private static final Logger LOG = Logger.getLogger(CityDao.class);

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public CityDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method search City by name parameter and
     * return City ID.
     *
     * @param name City name
     * @return ID City
     * @throws DBException
     */
    public Integer getIdCityByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer idCity = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_GET_ID_CITY_BY_NAME);

            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idCity = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_CITY_BY_ID, e);
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

    /**
     * This method return all cities from DB
     *
     * @return list of Cities
     * @throws DBException
     */
    @Override
    public List<City> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<City> cityNames = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(Queries.SQL_GET_ALL_CITIES);
            while (rs.next()) {
                City cityTemp = new City();
                cityTemp.setName(rs.getString("name"));
                cityNames.add(cityTemp);
            }
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_GET_CITY_LIST, e);
            throw new DBException(Messages.ERR_GET_CITY_LIST, e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
        }

        return cityNames;
    }

    /**
     * This method save new city to DB
     *
     * @param city City object
     * @throws DBException
     */
    @Override
    public void save(City city) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_CITY);
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

    /**
     * This method delete city from db
     *
     * @param city City object
     * @throws DBException
     */
    @Override
    public void delete(City city) throws DBException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_CITY_FROM_DB);
            pstmt.setString(1, city.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_DELETE_CITY, e);
            throw new DBException(Messages.ERR_DELETE_CITY, e);
        } finally {
            DBUtil.close(pstmt);
        }
    }
}
