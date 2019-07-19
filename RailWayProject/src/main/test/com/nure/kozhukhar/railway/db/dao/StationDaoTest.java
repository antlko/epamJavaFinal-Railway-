package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.City;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.*;

public class StationDaoTest {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private StationDao stationDao;

    private Station station;

    public StationDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        stationDao = new StationDao(connection);

        station = new Station();
        station.setName("testStation");
        station.setIdCity(0);
        station.setPrice(0);
        station.setDateEnd(LocalDateTime.parse("1000-01-01T00:00"));
        station.setDateEnd(LocalDateTime.parse("1000-01-01T00:00"));
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getAllStationByRoute() throws DBException {
        stationDao.getAllStationByRoute("","", Date.valueOf("1000-01-01"),1);
    }

    @Test
    public void get() {
        assertThat(stationDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() throws DBException {
        stationDao.getAll();
    }

    @Test(expected = DBException.class)
    public void save() throws DBException {
        stationDao.save(station);
    }

    @Test
    public void update() {
        stationDao.update(null,null);
    }

    @Test
    public void delete() throws DBException {
        stationDao.delete(station);
    }
}