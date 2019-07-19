package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.Country;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import com.nure.kozhukhar.railway.web.action.ordering.OrderingMainAction;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CountryDaoTest extends Mockito {

    private static final Logger LOG = Logger.getLogger(CountryDaoTest.class);

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    public CountryDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException {
        connection.setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void testSave() throws DBException, SQLException {
        CountryDao countryDao = new CountryDao(connection);
        Country country = new Country();
        country.setName("UNIT TEST");
        countryDao.save(country);
    }

    @Test(expected = DBException.class)
    public void testSaveException() throws DBException, SQLException {
        CountryDao countryDao = new CountryDao(connection);
        Country country = new Country();
        country.setName(null);
        country.setId(null);
        countryDao.save(country);
    }

    @Test
    public void testFindIdCountyByName() throws DBException, SQLException, ClassNotFoundException {
        CountryDao countryDao = new CountryDao(connection);
        Integer id = countryDao.findIdCountyByName("Ukraine");
        LOG.trace("Log test ->" + id);
        assertThat(id).isEqualTo(1);
    }

    @Test
    public void testGetAll() throws DBException {
        CountryDao countryDao = new CountryDao(connection);
        countryDao.getAll();
    }

    @Test
    public void testDelete() throws DBException {
        CountryDao countryDao = new CountryDao(connection);
        Country country = new Country();
        country.setName("UNIT TEST");
        countryDao.delete(country);
    }


}