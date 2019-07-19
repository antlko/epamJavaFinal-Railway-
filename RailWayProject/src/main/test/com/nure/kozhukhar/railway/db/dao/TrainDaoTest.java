package com.nure.kozhukhar.railway.db.dao;


import static org.assertj.core.api.Assertions.*;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;

public class TrainDaoTest {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private TrainDao trainDao;

    private Train train;

    public TrainDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        trainDao = new TrainDao(connection);

        train = new Train();
        train.setNumber(9999);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getMaxSizeFromCarriageByTrain() throws SQLException, DBException {
        trainDao.getMaxSizeFromCarriageByTrain(0,0);
    }

    @Test
    public void saveAndDeleteTrainContent() throws SQLException, DBException {
        trainDao.save(train);
        train.setId(trainDao.getIdTrainByNumber(train.getNumber()));
        trainDao.saveTrainContent(train.getId(),1,1,3);
        trainDao.delete(train);
    }

    @Test
    public void getTrainsStatistic() throws DBException {
        trainDao.getTrainsStatistic();
    }

    @Test
    public void deleteAllTrainContent() throws DBException {
        trainDao.save(train);
        trainDao.getIdTrainByNumber(train.getNumber());
        trainDao.deleteAllTrainContent(train.getNumber());
        trainDao.delete(train);
    }

    @Test
    public void get() {
        assertThat(trainDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() throws DBException {
        assertThat(trainDao.getAll().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void save() throws DBException {
        trainDao.save(train);
        trainDao.delete(train);
    }

    @Test
    public void update() {
        trainDao.update(null,null);
    }
}