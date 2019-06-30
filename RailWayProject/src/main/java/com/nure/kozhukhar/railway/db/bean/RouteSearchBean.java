package com.nure.kozhukhar.railway.db.bean;

import com.nure.kozhukhar.railway.db.entity.Entity;
import com.nure.kozhukhar.railway.db.entity.Train;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class RouteSearchBean extends Entity {

    private Integer idRoute;

    private Train train;

    private List<RouteStation> stationList;

    private String travelTime;

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public Integer getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<RouteStation> getStationList() {
        return stationList;
    }

    public void setStationList(List<RouteStation> stationList) {
        this.stationList = stationList;
    }

    @Override
public String toString() {
        return "RouteSearchBean{" +
                "idRoute=" + idRoute +
                ", train=" + train +
                ", stationList=" + stationList +
                '}';
    }
}
