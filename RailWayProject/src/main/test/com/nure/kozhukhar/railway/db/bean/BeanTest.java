package com.nure.kozhukhar.railway.db.bean;

import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.Route;
import org.junit.Test;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.*;

import static org.junit.Assert.*;

public class BeanTest {

    @Test
    public void testRouteSearchBean() {
        RouteSearchBean rsb = new RouteSearchBean();
        rsb.setTrain(new Train());
        rsb.setStationList(new ArrayList<>());
        rsb.setIdRoute(0);
        rsb.setSeatList(new ArrayList<>());
        rsb.setDateFrom("");
        rsb.setTravelTime("");
        rsb.setPriceRoute(0);
        rsb.setTimeFrom("");

        assertThat(rsb.getTrain().getClass()).isEqualTo(Train.class);
        assertThat(rsb.getStationList().getClass()).isEqualTo(ArrayList.class);
        assertThat(rsb.getIdRoute().getClass()).isEqualTo(Integer.class);
        assertThat(rsb.getSeatList().getClass()).isEqualTo(ArrayList.class);
        assertThat(rsb.getDateFrom().getClass()).isEqualTo(String.class);
        assertThat(rsb.getTravelTime().getClass()).isEqualTo(String.class);
        assertThat(rsb.getTimeFrom().getClass()).isEqualTo(String.class);
        assertThat(rsb.getPriceRoute().getClass()).isEqualTo(Integer.class);
    }

    @Test
    public void testSeatSearchBean() {
        SeatSearchBean ssb = new SeatSearchBean();

        ssb.setFree(0);
        ssb.setBusy(0);
        ssb.setMaxSize(0);
        ssb.setSeatType("");
        ssb.setPriceSeat(0);
        ssb.setNumSeat(0);
        ssb.setNumCarriage(0);
        ssb.setNumTrain(0);
        ssb.setListSeat(new ArrayList<>());

        assertThat(ssb.getFree().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getBusy().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getMaxSize().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getSeatType().getClass()).isEqualTo(String.class);
        assertThat(ssb.getPriceSeat().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getNumSeat().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getNumCarriage().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getNumTrain().getClass()).isEqualTo(Integer.class);
        assertThat(ssb.getListSeat().getClass()).isEqualTo(ArrayList.class);
    }
}