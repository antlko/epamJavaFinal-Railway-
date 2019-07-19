package com.nure.kozhukhar.railway.db.dao;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.entity.User;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class UserDaoTest {

    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();

    private UserDao userDao;

    private User user;

    private Role role;

    public UserDaoTest() throws SQLException, DBException, ClassNotFoundException {
    }


    @Before
    public void setUp() throws Exception {
        connection.setAutoCommit(false);
        userDao = new UserDao(connection);

        user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setSurname("test");
        user.setName("test");
        user.setEmail("test@test.test");
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void testMainUserBehavior() throws DBException {
        userDao.save(user);
        User newUser = userDao.getByLogin(user.getLogin());
        System.err.println(newUser);
        userDao.saveUserRoleByLogin(newUser,Role.USER.getName());


        //Test Update user
        newUser.setName("update");
        userDao.update(newUser, null);

        //Test getFullNameByUserId
        String[] nameSurname = userDao.getFullNameByUserId(newUser.getId()).split(" ");

        userDao.delete(newUser);

        assertThat(nameSurname[0]).isEqualTo("update");
        assertThat(nameSurname[1]).isEqualTo(newUser.getSurname());
        assertThat(newUser.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    public void get() {
        assertThat(userDao.get(0)).isEqualTo(null);
    }

    @Test
    public void getAll() {
        assertThat(userDao.getAll()).isEqualTo(null);
    }
}