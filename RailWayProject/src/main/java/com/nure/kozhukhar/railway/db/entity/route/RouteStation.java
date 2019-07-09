package com.nure.kozhukhar.railway.db.entity.route;

import com.nure.kozhukhar.railway.db.entity.Entity;

import java.sql.Date;
import java.time.LocalDateTime;

public class RouteStation extends Entity {

    private Integer idStation;

    private Integer idRoute;

    private Integer idTrain;

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    private Date dateEnd;

    private String name;

    private Integer idCity;

    private Integer price;


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getIdStation() {
        return idStation;
    }

    public void setIdStation(Integer idStation) {
        this.idStation = idStation;
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

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String   toString() {
        return "RouteStation{" +
                "idStation=" + idStation +
                ", idRoute=" + idRoute +
                ", idTrain=" + idTrain +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", dateEnd=" + dateEnd +
                ", name='" + name + '\'' +
                ", idCity=" + idCity +
                ", price=" + price +
                '}';
    }
}
