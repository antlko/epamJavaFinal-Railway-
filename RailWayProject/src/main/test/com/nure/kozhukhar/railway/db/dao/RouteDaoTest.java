package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RouteDaoTest extends Mockito {


    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private RouteDao routeDao;

    private RouteStation routeStation;

    public RouteDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        routeDao = new RouteDao(connection);
        routeStation = new RouteStation();
        routeStation.setIdTrain(999999);
        routeStation.setIdRoute(null);
        routeStation.setName(null);
        routeStation.setTimeStart(LocalDateTime.parse("2000-01-01T00:00"));
        routeStation.setTimeEnd(LocalDateTime.parse("2000-01-01T00:00"));
        routeStation.setPrice(100);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getIdStationByName() throws SQLException, DBException {
        assertThat(routeDao.getIdStationByName("")).isEqualTo(null);
    }

    @Test(expected = DBException.class)
    public void saveStationByRouteId() throws SQLException, DBException {
        Savepoint savepoint = connection.setSavepoint();
        routeDao.saveStationByRouteId(0,
                LocalDateTime.parse("2000-01-01T00:00"),
                LocalDateTime.parse("2000-01-01T00:00"));
        connection.rollback(savepoint);
    }

    @Test
    public void getAllRoute() throws DBException, SQLException {
        assertThat(routeDao.getAllRoute().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void getIdRouteOnDate() throws SQLException, DBException {
        Savepoint savepoint = connection.setSavepoint();
        assertThat(routeDao.getIdRouteOnDate("", "", Date.valueOf("2000-01-01")))
                .isEqualTo(new ArrayList());
        connection.rollback(savepoint);
    }

    @Test
    public void getAllStationsByRouteId() throws SQLException, DBException {
        assertThat(routeDao.getAllStationsByRouteId(0)).isEqualTo(new ArrayList<>());
    }

    @Test
    public void get() throws SQLException {
        Savepoint savepoint = connection.setSavepoint();
        assertThat(routeDao.get(0)).isEqualTo(null);
        connection.rollback(savepoint);
    }

    @Test
    public void getAll() throws SQLException {
        Savepoint savepoint = connection.setSavepoint();
        routeDao.getAll();
        connection.rollback(savepoint);
    }

    @Test(expected = NullPointerException.class)
    public void saveOneRoute() throws SQLException, DBException {
        Savepoint savepoint = connection.setSavepoint();
        routeDao.saveOneRoute(0);
        connection.rollback(savepoint);
    }

    @Test(expected = NullPointerException.class)
    public void save() throws DBException, SQLException {
        Savepoint savepoint = connection.setSavepoint();
        routeDao.save(routeStation);
        connection.rollback(savepoint);
    }

    @Test
    public void update() {
        routeDao.update(null, null);
    }

    @Test
    public void delete() throws DBException {
        RouteStation routeStation = new RouteStation();
        routeStation.setIdRoute(0);
        routeDao.delete(routeStation);
    }
}