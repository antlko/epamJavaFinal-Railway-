package com.nure.kozhukhar.railway.web.action.admin;

import com.nure.kozhukhar.railway.db.Role;
import com.nure.kozhukhar.railway.db.dao.*;
import com.nure.kozhukhar.railway.db.entity.*;
import com.nure.kozhukhar.railway.exception.AppException;
import com.nure.kozhukhar.railway.exception.DBException;
import com.nure.kozhukhar.railway.util.DBUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

import static org.junit.Assert.*;

public class AdminDataTest extends Mockito {



    private Connection connection = DBUtil.getInstance().getDataSource().getConnection();


    // Constants
    private static final String LOGIN = "testLog";
    private static final String PASSWORD = "testPas";
    private static final String EMAIL = "testEmail@gmail.com";

    // DAO
    private UserDao userDao;

    private TypeDao typeDao;

    private TrainDao trainDao;

    private StationDao stationDao;

    private CityDao cityDao;

    private CountryDao countryDao;

    // Entity
    private User user;

    private Type type;

    private Station station;

    private City city;

    private Country country;

    private Train train;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    public AdminDataTest() throws SQLException, DBException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        connection.setAutoCommit(false);
        when(request.getSession()).thenReturn(session);

        user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        userDao = new UserDao(connection);

        type = new Type();
        type.setName("testType");
        type.setPrice(100);
        typeDao = new TypeDao(connection);
        typeDao.save(type);

        train = new Train();
        train.setNumber(99999);
        trainDao = new TrainDao(connection);

        cityDao = new CityDao(connection);
        countryDao = new CountryDao(connection);
        city = new City();
        country = new Country();
        country.setName("testCountry");
        country.setId(countryDao.findIdCountyByName(country.getName()));
        city.setName("testCity");
        city.setIdCountry(country.getId());
        city.setId(cityDao.getIdCityByName(city.getName()));

        System.out.println("city" + city);

        station = new Station();
        stationDao = new StationDao(connection);
        station.setPrice(100);
        station.setName("testStation");
        station.setIdCity(city.getId());
    }

    @After
    public void tearDown() throws Exception {
        trainDao.delete(train);
        typeDao.delete(type);
    }


    /***
     * AdminAccountPage action
     */
    @Test
    public void adminAccountPageReturn() throws ServletException, IOException, AppException {
        AdminAccountPage adminChangeData = new AdminAccountPage();

        when(request.getSession()).thenReturn(null);
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/login");
    }

    @Test
    public void adminAccountPage() throws ServletException, IOException, AppException {
        AdminAccountPage adminChangeData = new AdminAccountPage();

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("WEB-INF/jsp/admin/admin.jsp");
    }

    /***
     * UserChangeData actions
     */
    @Test
    public void userChangeDataSave() throws ServletException, IOException, AppException {
        UserChangeData adminChangeData = new UserChangeData();
        userDao.save(user);
        userDao.saveUserRoleByLogin(user, Role.USER.getName());

        when(request.getParameter("loginUser")).thenReturn(user.getLogin());
        when(request.getParameter("tagRole")).thenReturn(Role.ADMIN.getName());
        when(request.getParameter("updateUserInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
        userDao.delete(user);
    }

    @Test
    public void userChangeDataDelete() throws ServletException, IOException, AppException {
        UserChangeData adminChangeData = new UserChangeData();
        userDao.save(user);
        userDao.saveUserRoleByLogin(user, Role.USER.getName());

        when(request.getParameter("loginUser")).thenReturn(user.getLogin());
        when(request.getParameter("tagRole")).thenReturn(Role.ADMIN.getName());
        when(request.getParameter("updateUserInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    /***
     * TypeChangeData action
     */
    @Test
    public void typeChangeDataSave() throws ServletException, IOException, AppException {
        TypeChangeData adminChangeData = new TypeChangeData();

        when(request.getParameter("typeName")).thenReturn("testType");
        when(request.getParameter("typePrice")).thenReturn("500");
        when(request.getParameter("changeTypeInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        typeDao.delete(type);
    }

    @Test
    public void typeChangeDataDelete() throws ServletException, IOException, AppException {
        TypeChangeData adminChangeData = new TypeChangeData();

        when(request.getParameter("typeName")).thenReturn("testType");
        when(request.getParameter("changeTypeInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    /***
     * TrainChangeData action
     */
    @Test
    public void trainChangeDataSave() throws ServletException, IOException, AppException {
        TrainChangeData adminChangeData = new TrainChangeData();

        when(request.getParameter("trainNumber")).thenReturn("99999");
        when(request.getParameter("changeTrainInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        Train train = new Train();
        train.setNumber(99999);
        trainDao.delete(train);
    }

    @Test
    public void trainChangeDataDelete() throws ServletException, IOException, AppException {
        TrainChangeData adminChangeData = new TrainChangeData();

        when(request.getParameter("trainNumber")).thenReturn("99999");
        when(request.getParameter("changeTrainInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    /***
     * StationChangeData action
     */
    @Test(expected = AppException.class)
    public void stationChangeDataSave() throws ServletException, IOException, AppException {
        StationChangeData adminChangeData = new StationChangeData();

        when(request.getParameter("stationName")).thenReturn(station.getName());
        when(request.getParameter("tagCities")).thenReturn("setCit");
        when(request.getParameter("changeStationInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    @Test
    public void stationChangeDataDelete() throws ServletException, IOException, AppException {
        StationChangeData adminChangeData = new StationChangeData();

        when(request.getParameter("stationName")).thenReturn("testType");
        when(request.getParameter("changeStationInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    /***
     * CountryChangeData action
     */
    @Test
    public void countryChangeDataSave() throws ServletException, IOException, AppException {
        CountryChangeData adminChangeData = new CountryChangeData();

        when(request.getParameter("countryName")).thenReturn(country.getName());
        when(request.getParameter("changeCountryInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        countryDao.delete(country);
    }

    @Test
    public void countryChangeDataDelete() throws ServletException, IOException, AppException {
        CountryChangeData adminChangeData = new CountryChangeData();

        countryDao.save(country);

        when(request.getParameter("countryName")).thenReturn(country.getName());
        when(request.getParameter("changeCountryInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    /***
     * CountryChangeData action
     */
    @Test
    public void cityChangeDataSave() throws ServletException, IOException, AppException {
        CityChangeData adminChangeData = new CityChangeData();

        countryDao.save(country);

        when(request.getParameter("cityName")).thenReturn(city.getName());
        when(request.getParameter("tagCountries")).thenReturn(country.getName());
        when(request.getParameter("changeCityInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        cityDao.delete(city);
        countryDao.delete(country);
    }

    @Test
    public void cityChangeDataDelete() throws ServletException, IOException, AppException {
        CityChangeData adminChangeData = new CityChangeData();

        countryDao.save(country);
        city.setIdCountry(countryDao.findIdCountyByName(
                country.getName())
        );
        cityDao.save(city);

        when(request.getParameter("cityName")).thenReturn(city.getName());
        when(request.getParameter("changeCityInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        countryDao.delete(country);
    }

    /***
     * CarriageChangeData action
     */
    @Test
    public void carriageChangeDataSave() throws ServletException, IOException, AppException {
        CarrSeatChangeData adminChangeData = new CarrSeatChangeData();

        trainDao.save(train);

        when(request.getParameter("tagTrains")).thenReturn(train.getNumber().toString());
        when(request.getParameter("changeTrainInfo")).thenReturn("Save");
        when(request.getParameter("carriageCount")).thenReturn("0");
        when(request.getParameter("seatCount")).thenReturn("0");
        when(request.getParameter("tagTypes")).thenReturn("none");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        trainDao.delete(train);
    }

    @Test
    public void carriageChangeDataDelete() throws ServletException, IOException, AppException {
        CountryChangeData adminChangeData = new CountryChangeData();

        trainDao.save(train);

        when(request.getParameter("tagTrains")).thenReturn(train.getNumber().toString());
        when(request.getParameter("changeTrainInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        trainDao.delete(train);
    }

    /***
     * RouteChangeData action
     */
    @Test
    public void routeChangeDataSave() throws ServletException, IOException, AppException {
        RouteChangeData adminChangeData = new RouteChangeData();

        trainDao.save(train);

        when(request.getParameter("tagTrains")).thenReturn(train.getNumber().toString());
        when(request.getParameter("listInfoStation"))
                .thenReturn("TestCity, 0001-01-01 01:01-0001-01-01 01:01. Price 50");

        when(request.getParameter("changeRouteInfo")).thenReturn("Save");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");

        trainDao.delete(train);
    }

    @Test(expected = AppException.class)
    public void routeChangeDataSaveOnDate() throws ServletException, IOException, AppException {
        RouteChangeData adminChangeData = new RouteChangeData();

        trainDao.save(train);

        when(request.getParameter("routeId")).thenReturn("99999");
        when(request.getParameter("tagTrains")).thenReturn(train.getNumber().toString());
        when(request.getParameter("listInfoStation"))
                .thenReturn("TestCity, 0001-01-01 01:01-0001-01-01 01:01. Price 50");
        when(request.getParameter("date-station")).thenReturn("0001-01-01");
        when(request.getParameter("date-station-end")).thenReturn("0001-01-01");

        when(request.getParameter("changeRouteInfo")).thenReturn("SaveRoutesDate");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

    @Test
    public void routeChangeDataDelete() throws ServletException, IOException, AppException {
        CountryChangeData adminChangeData = new CountryChangeData();
        when(request.getParameter("routeId")).thenReturn("99999");
        when(request.getParameter("changeTrainInfo")).thenReturn("Delete");
        when(request.getParameter("checkVal")).thenReturn("1");

        assertThat(adminChangeData.execute(request, response))
                .isEqualTo("/admin");
    }

}