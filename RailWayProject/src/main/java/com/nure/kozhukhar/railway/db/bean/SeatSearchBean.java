package com.nure.kozhukhar.railway.db.bean;

import com.nure.kozhukhar.railway.db.entity.Seat;

import java.util.List;

public class SeatSearchBean {

    private String seatType;

    private Integer free;

    private Integer numSeat;

    private List<Integer> listSeat;

    private Integer numTrain;

    private Integer priceSeat;

    private Integer numCarriage;

    public Integer getPriceSeat() {
        return priceSeat;
    }

    public void setPriceSeat(Integer priceSeat) {
        this.priceSeat = priceSeat;
    }

    public Integer getNumCarriage() {
        return numCarriage;
    }

    public List<Integer> getListSeat() {
        return listSeat;
    }

    public void setListSeat(List<Integer> listSeat) {
        this.listSeat = listSeat;
    }

    public void setNumCarriage(Integer numCarriage) {
        this.numCarriage = numCarriage;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getNumSeat() {
        return numSeat;
    }

    public void setNumSeat(Integer numSeat) {
        this.numSeat = numSeat;
    }

    public Integer getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(Integer numTrain) {
        this.numTrain = numTrain;
    }

    @Override
    public String toString() {
        return "SeatSearchBean{" +
                "seatType='" + seatType + '\'' +
                ", free=" + free +
                ", numSeat=" + numSeat +
                ", numTrain=" + numTrain +
                ", numCarriage=" + numCarriage +
                '}';
    }
}
