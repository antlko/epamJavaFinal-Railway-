package com.nure.kozhukhar.railway.db.bean;

import com.nure.kozhukhar.railway.db.entity.Station;
import com.nure.kozhukhar.railway.db.entity.route.RouteStation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provide information that is responsible for user-purchased route
 *
 * @author Anatol Kozhukhar
 */
public class UserCheckBean {

    private String userInitial;

    private Integer idUser;

    private Integer idTrain;

    private Integer numTrain;

    private Integer numSeat;

    private Integer numCarriage;

    private String cityStart;

    private String cityEnd;

    private List<Station> stationList;

    private LocalDateTime dateEnd;

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    public Integer getNumCarriage() {
        return numCarriage;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getUserInitial() {
        return userInitial;
    }

    public void setUserInitial(String userInitial) {
        this.userInitial = userInitial;
    }

    public String getCityStart() {
        return cityStart;
    }

    public Integer getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(Integer numTrain) {
        this.numTrain = numTrain;
    }

    public void setCityStart(String cityStart) {
        this.cityStart = cityStart;
    }

    public String getCityEnd() {
        return cityEnd;
    }

    public void setCityEnd(String cityEnd) {
        this.cityEnd = cityEnd;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "UserCheckBean{" +
                "userInitial='" + userInitial + '\'' +
                ", idUser=" + idUser +
                ", idTrain=" + idTrain +
                ", numTrain=" + numTrain +
                ", numSeat=" + numSeat +
                ", numCarriage=" + numCarriage +
                ", cityStart='" + cityStart + '\'' +
                ", cityEnd='" + cityEnd + '\'' +
                ", stationList=" + stationList +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
