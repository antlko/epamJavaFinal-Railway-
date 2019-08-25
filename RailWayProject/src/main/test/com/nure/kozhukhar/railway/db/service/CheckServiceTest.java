package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.db.bean.UserCheckBean;
import com.nure.kozhukhar.railway.db.dao.UserDao;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.assertj.core.api.Assertions.*;


public class CheckServiceTest extends Mockito {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    @Mock
    private UserDao userDao;

    public CheckServiceTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getUserTicketsById() throws DBException {
        List<UserCheckBean> checkBeans = CheckService.getUserTicketsById(1);
        assertThat(checkBeans.size()).isGreaterThan(0);
        assertThat(checkBeans.get(0).getStationList().size()).isGreaterThan(0);
    }
}