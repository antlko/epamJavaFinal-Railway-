package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Queries;
import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.exception.Messages;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Object which operate Countries information from DB
 * Have a {@link CountryDao} constructor with connection parameter.
 *
 * @author Anatol Kozhukhar
 */
public class CountryDao implements Dao<Country> {

    private static final Logger LOG = Logger.getLogger(CountryDao.class);

    private Connection conn;

    /**
     * Connection is important for execution queries and
     * manipulation data from the DB.
     *
     * @param conn is used for set connection parameter
     */
    public CountryDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method return country ID from DB by country name
     *
     * @param name Country name
     * @return country ID
     * @throws DBException
     */
    public Integer findIdCountyByName(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Integer idCountry = null;

        // Code block which execute query/ies to DB.
        // If operation failed -> we must throw new DBException to the layer upper.
        try {
            pstmt = conn.prepareStatement(Queries.SQL_FIND_ID_COUNTRY_BY_NAME);

            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idCountry = rs.getInt("id");
            }
            conn.commit();

        } catch (SQLException e) {
            // Rollback changes in DB, inform server using Logger,
            // and throw new DBException.
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_COUNTRY_WAS_NOT_FOUND, e);
            throw new DBException(Messages.ERR_COUNTRY_WAS_NOT_FOUND, e);
        } finally {
            // Close all opened resources.
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }
        return idCountry;
    }

    @Override
    public Country get(long id) {
        return null;
    }

    /**
     * This method return all information about countries.
     *
     * @return list of countries
     * @throws DBException
     */
    @Override
    public List<Country> getAll() throws DBException {
        Statement stmt = null;
        ResultSet rs = null;

        List<Country> countryNames = new ArrayList<>();

        // Code block which execute query/ies to DB.
        // If operation failed -> we must throw new DBException to the layer upper.
        try {
            conn = DBUtil.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();
            rs = stmt.executeQuery(Queries.SQL_FIND_ALL_COUNTRIES);

            while (rs.next()) {
                Country countryTemp = new Country();
                countryTemp.setName(rs.getString("name"));
                countryNames.add(countryTemp);
            }
            conn.commit();

        } catch (SQLException | ClassNotFoundException e) {
            // Rollback changes in DB, inform server using Logger,
            // and throw new DBException.
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_COUNTRIES_WAS_NOT_FOUND, e);
            throw new DBException(Messages.ERR_COUNTRIES_WAS_NOT_FOUND, e);
        } finally {
            // Close all opened resources.
            DBUtil.close(rs);
            DBUtil.close(stmt);
        }
        return countryNames;
    }

    /**
     * This method is used for save new country to DB
     *
     * @param country Country object
     * @throws DBException
     */
    @Override
    public void save(Country country) throws DBException {
        PreparedStatement pstmt = null;

        // Code block which execute query/ies to DB.
        // If operation failed -> we must throw new DBException to the layer upper.
        try {
            pstmt = conn.prepareStatement(Queries.SQL_SAVE_NEW_COUNTRY);
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            // Rollback changes in DB, inform server using Logger,
            // and throw new DBException.
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_COUNTRY_SAVE, e);
            throw new DBException(Messages.ERR_COUNTRY_SAVE, e);
        } finally {
            // Close all opened resources.
            DBUtil.close(pstmt);
        }
    }

    @Override
    public void update(Country country, String[] params) {

    }

    /**
     * Delete country from DB
     *
     * @param country Country object
     * @throws DBException
     */
    @Override
    public void delete(Country country) throws DBException {
        PreparedStatement pstmt = null;

        // Code block which execute query/ies to DB.
        // If operation failed -> we must throw new DBException to the layer upper.
        try {
            pstmt = conn.prepareStatement(Queries.SQL_DELETE_FROM_COUNTRIES);
            pstmt.setString(1, country.getName());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            // Rollback changes in DB, inform server using Logger,
            // and throw new DBException.
            DBUtil.rollback(conn);
            LOG.error(Messages.ERR_COUNTRY_DELETE, e);
            throw new DBException(Messages.ERR_COUNTRY_DELETE, e);
        } finally {
            // Close all opened resources.
            DBUtil.close(pstmt);
        }
    }
}
