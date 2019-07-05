package com.nure.kozhukhar.railway.db.entity;

import java.time.LocalDateTime;

public class UserCheck extends Entity {

    private Integer idUser;

    private Integer totalPrice;

    private Integer idTrain;

    private String initials;

    private Integer numCarriage;

    private Integer numSeat;

    private Integer numTrain;

    private LocalDateTime dateEnd;

    private Integer idStation;

    private Integer idRoute;

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(Integer numTrain) {
        this.numTrain = numTrain;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    public Integer getNumCarriage() {
        return numCarriage;
    }

    public void setNumCarriage(Integer numCarriage) {
        this.numCarriage = numCarriage;
    }

    public Integer getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(Integer numSeat) {
        this.numSeat = numSeat;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
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

    @Override
    public String toString() {
        return "UserCheck{" +
                "idUser=" + idUser +
                ", totalPrice=" + totalPrice +
                ", idTrain=" + idTrain +
                ", numCarriage=" + numCarriage +
                ", numSeat=" + numSeat +
                ", numTrain=" + numTrain +
                ", dateEnd=" + dateEnd +
                ", idStation=" + idStation +
                ", idRoute=" + idRoute +
                '}';
    }
}
