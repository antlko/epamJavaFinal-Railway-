package com.nure.kozhukhar.railway.db.entity;

import com.nure.kozhukhar.railway.db.entity.route.Route;
import com.nure.kozhukhar.railway.db.entity.route.RouteOnDate;
import org.junit.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.*;

import static org.junit.Assert.*;

public class EntitiesTest {

    @Test
    public void testCarriage() {
        Carriage carriage = new Carriage();
        carriage.setIdTrain(1);
        carriage.setIdType("carr");
        carriage.setNumCarriage(1);

        assertThat(carriage.getIdTrain()).isEqualTo(1);
        assertThat(carriage.getIdType()).isEqualTo("carr");
        assertThat(carriage.getNumCarriage()).isEqualTo(1);
    }

    @Test
    public void testSeat() {
        Seat seat = new Seat();
        seat.setIdTrain(1);
        seat.setNumCarriage(1);
        seat.setNumSeat(1);

        assertThat(seat.getIdTrain()).isEqualTo(1);
        assertThat(seat.getNumCarriage()).isEqualTo(1);
        assertThat(seat.getNumSeat()).isEqualTo(1);
    }

    @Test
    public void testRoute() {
        Route route = new Route();
        route.setId(1);
        route.setTrain(new Train());

        assertThat(route.getId()).isEqualTo(1);
        assertThat(route.getTrain().getClass()).isEqualTo(Train.class);
    }

    @Test
    public void testRouteOnDate() {
        RouteOnDate routeOnDate = new RouteOnDate();
        routeOnDate.setDateEnd(new Date(0));
        routeOnDate.setTimeDateEnd(new Date(0));
        routeOnDate.setTimeDateStart(new Date(0));
        routeOnDate.setIdRoute(1);
        routeOnDate.setIdTrain(1);
        routeOnDate.setPrice(1);
        routeOnDate.setStation(new Station());

        assertThat(routeOnDate.getDateEnd().getClass()).isEqualTo(Date.class);
        assertThat(routeOnDate.getTimeDateStart().getClass()).isEqualTo(Date.class);
        assertThat(routeOnDate.getTimeDateEnd().getClass()).isEqualTo(Date.class);
        assertThat(routeOnDate.getStation().getClass()).isEqualTo(Station.class);
        assertThat(routeOnDate.getIdTrain()).isEqualTo(1);
        assertThat(routeOnDate.getPrice()).isEqualTo(1);
        assertThat(routeOnDate.getIdRoute()).isEqualTo(1);
    }

}