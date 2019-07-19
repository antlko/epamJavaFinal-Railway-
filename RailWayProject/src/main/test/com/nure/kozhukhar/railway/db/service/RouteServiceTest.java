package com.nure.kozhukhar.railway.db.service;

import com.nure.kozhukhar.railway.exception.DBException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


public class RouteServiceTest {

    private RouteService routeService;

    @Before
    public void setUp() throws Exception {
        routeService = new RouteService();
    }

    @Test
    public void getRouteInfoByCityDate() throws DBException {
        assertThat(RouteService.getRouteInfoByCityDate(
                "Одесса-Главная",
                "Харьков-Левада",
                Date.valueOf("2019-07-30")
        )).isNotEqualTo(null);
    }

    @Test
    public void getSeatInfoByCarriageType() throws DBException {
        assertThat(RouteService.getSeatInfoByCarriageType(
                "Одесса-Главная",
                "Харьков-Левада",
                Date.valueOf("2019-07-30"),
                "купе",
                265
        )).isNotEqualTo(null);
    }
}