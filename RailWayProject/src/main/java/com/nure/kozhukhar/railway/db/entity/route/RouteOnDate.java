package com.nure.kozhukhar.railway.db.entity.route;

import com.nure.kozhukhar.railway.db.entity.Entity;
import com.nure.kozhukhar.railway.db.entity.Station;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RouteOnDate extends Entity {

    private Date DateEnd;

    private Station station;

    private Integer idRoute;

    private Integer idTrain;

    private Date timeDateStart;

    private Date timeDateEnd;

    private List<Station> stations;

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        DateEnd = dateEnd;
    }

    public Integer getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    public Date getTimeDateStart() {
        return timeDateStart;
    }

    public void setTimeDateStart(Date timeDateStart) {
        this.timeDateStart = timeDateStart;
    }

    public Date getTimeDateEnd() {
        return timeDateEnd;
    }

    public void setTimeDateEnd(Date timeDateEnd) {
        this.timeDateEnd = timeDateEnd;
    }



    @Override
    public String toString() {
        return "RouteOnDate{" +
                "DateEnd=" + DateEnd +
                ", station=" + station +
                ", idRoute=" + idRoute +
                ", idTrain=" + idTrain +
                ", timeDateStart=" + timeDateStart +
                ", timeDateEnd=" + timeDateEnd +
                '}';
    }
}
