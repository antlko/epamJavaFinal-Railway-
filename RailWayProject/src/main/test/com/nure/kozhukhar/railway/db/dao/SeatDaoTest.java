package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class SeatDaoTest {


    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private SeatDao seatDao;

    public SeatDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        seatDao = new SeatDao(connection);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getSeatCountInfo() throws DBException {
        seatDao.getSeatCountInfo("", "", Date.valueOf("1000-01-01"), 1);
    }

    @Test
    public void getAllSeatsByCarriageType() throws DBException {
        seatDao.getAllSeatsByCarriageType("", "", "",
                Date.valueOf("1000-01-01"), 1);
    }

    @Test
    public void getAllSeatsByCarriageTypeAndNum() throws DBException, ClassNotFoundException {
        seatDao.getAllSeatsByCarriageTypeAndNum("", "", "",
                Date.valueOf("1000-01-01"), 1, 1);
    }

    @Test
    public void get() {
        assertThat(seatDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() {
        assertThat(seatDao.getAll()).isEqualTo(null);
    }

    @Test
    public void save() {
        seatDao.save(null);
    }

    @Test
    public void update() {
        seatDao.update(null, null);
    }

    @Test
    public void delete() {
        seatDao.delete(null);
    }
}