package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.db.entity.UserCheck;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;


public class CheckDaoTest extends Mockito {

    private static final Logger LOG = Logger.getLogger(CountryDaoTest.class);

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    @Mock
    PreparedStatement preparedStatement;

    private UserCheck userCheck;

    public CheckDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException {
        connection.setAutoCommit(false);
        userCheck = new UserCheck();
        preparedStatement = Mockito.mock(PreparedStatement.class);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test(expected = DBException.class)
    public void testSaveUserCheckInfo() throws DBException {
        CheckDao checkDao = new CheckDao(connection);
        UserCheck userCheck = mock(UserCheck.class);

        when(userCheck.getDateEnd()).thenReturn(LocalDateTime.parse("2019-01-01T00:00"));
        checkDao.saveUserCheckInfo(userCheck);

        verify(preparedStatement, times(9));
    }

    @Test
    public void testGetAllCheckByUserId() throws DBException {
        CheckDao checkDao = new CheckDao(connection);
        assertThat(checkDao.getAllCheckByUserId(-1).size()).isEqualTo(0);
    }

    @Test
    public void testGetAllCarrByTag() throws DBException {
        CheckDao checkDao = new CheckDao(connection);
        UserCheckBean userCheckBean = new UserCheckBean();
        assertThat(checkDao.getAllCarriageInfoByTags(0, 0, 0, 0)
                .getClass()).isEqualTo(UserCheckBean.class);
    }

    @Test
    public void testDelete() throws DBException {
        CheckDao checkDao = new CheckDao(connection);
        UserCheckBean userCheckBean = new UserCheckBean();
        userCheckBean.setIdUser(0);
        userCheckBean.setIdTrain(0);
        userCheckBean.setNumCarriage(0);
        userCheckBean.setNumSeat(0);
        checkDao.delete(userCheckBean);
    }

    @Test
    public void get() {
        CheckDao checkDao = new CheckDao(connection);
        assertThat(checkDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() {
        CheckDao checkDao = new CheckDao(connection);
        assertThat(checkDao.getAll()).isEqualTo(null);
    }

    @Test
    public void save() {
        CheckDao checkDao = new CheckDao(connection);
        checkDao.save(null);
    }

    @Test
    public void update() {
        CheckDao checkDao = new CheckDao(connection);
        checkDao.update(null, null);
    }


}