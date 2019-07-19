package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;

public class CityDaoTest extends Mockito {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    public CityDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }

    private City city;

    @Before
    public void startUp() throws SQLException {
        connection.setAutoCommit(false);
        city =  new City();
        city.setId(-1);
        city.setIdCountry(-1);
        city.setName("");
    }
    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void getIdCityByName() throws DBException {
        CityDao cityDao = new CityDao(connection);
        cityDao.getIdCityByName("Test");
    }

    @Test
    public void getAll() throws DBException {
        CityDao cityDao = new CityDao(connection);
        cityDao.getAll();
    }

    @Test(expected = DBException.class)
    public void save() throws SQLException, DBException {
        CityDao cityDao = new CityDao(connection);
        Savepoint savepoint = connection.setSavepoint();
        cityDao.save(city);
        connection.rollback(savepoint);
    }

    @Test
    public void delete() throws SQLException, DBException {
        CityDao cityDao = new CityDao(connection);
        cityDao.delete(city);
    }

    @Test
    public void update() {
        CityDao cityDao = new CityDao(connection);
        cityDao.update(null,null);
    }

    @Test
    public void get() {
        CityDao cityDao = new CityDao(connection);
        assertThat(cityDao.get(0)).isEqualTo(null);
    }
}