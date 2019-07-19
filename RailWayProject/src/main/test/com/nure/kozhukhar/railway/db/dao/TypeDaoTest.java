package com.nure.kozhukhar.railway.db.dao;

import static org.assertj.core.api.Assertions.*;
import com.nure.kozhukhar.railway.db.entity.Type;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TypeDaoTest {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private TypeDao typeDao;

    private Type type;

    public TypeDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        typeDao = new TypeDao(connection);

        type = new Type();
        type.setName("test_type");
        type.setPrice(9999);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void getIDTypeByName() throws DBException {
        typeDao.save(type);
        typeDao.getIDTypeByName(type.getName());
        typeDao.delete(type);
    }

    @Test
    public void get() {
        assertThat(typeDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() throws DBException {
        assertThat(typeDao.getAll().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void update() throws DBException {
        typeDao.save(type);
        Type newType = new Type();
        newType.setName(type.getName());
        newType.setPrice(100);
        typeDao.update(newType,null);
        typeDao.delete(newType);
    }
}